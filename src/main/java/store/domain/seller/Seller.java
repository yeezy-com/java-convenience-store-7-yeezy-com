package store.domain.seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.Date;
import store.domain.Receipt;
import store.controller.ConvenienceInputIterator;
import store.domain.inventory.CartItem;
import store.domain.inventory.Inventory;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

public class Seller {

    private final ConvenienceInputIterator convenienceInputIterator;
    private final Pos pos;
    private final Date date;

    public Seller(ConvenienceInputIterator convenienceInputIterator, Pos pos, Date date) {
        this.convenienceInputIterator = convenienceInputIterator;
        this.pos = pos;
        this.date = date;
    }

    public Receipt processPurchase(Inventory inventory) {
        List<CartItem> cartItems = convenienceInputIterator.buyingProductInput(inventory);
        int membershipDiscount = 0;

        Map<String, Integer> promotionCount = calculatePromotionCount(inventory, cartItems);
        for (Entry<String, Integer> entry : promotionCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue() + "개 구매");
        }

        int totalPrice = calculateTotalPrice(cartItems);
        System.out.println(totalPrice);
        String membershipAnswer = convenienceInputIterator.readMembershipApply();
        membershipDiscount = calculateMembershipDiscount(membershipAnswer, cartItems, promotionCount, membershipDiscount, totalPrice);
        return new Receipt(cartItems, promotionCount, membershipDiscount);
    }

    private Map<String, Integer> calculatePromotionCount(Inventory inventory, List<CartItem> cartItems) {
        Map<String, Integer> promotionCount = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.hasPromotion() && cartItem.getPromotion().isWithinPromotionPeriod(date.today())) {
                promotionCount.put(cartItem.getName(), applyPromotion(cartItem, inventory));
                continue;
            }

            System.out.println(cartItem.getName());
            Product sellProduct = inventory.findByName(cartItem.getName());
            sellProduct.reduceRegularStock(cartItem.getQuantity());
        }
        return promotionCount;
    }

    private static int calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    private int calculateMembershipDiscount(String membership, List<CartItem> cartItems, Map<String, Integer> promotionCount,
                                      int membershipDiscount, int totalPrice) {
        if (membership.equals("Y")) {
            int promotionPrice = cartItems.stream()
                    .mapToInt(cartItem -> calculatePromotionPrice(cartItem, promotionCount.getOrDefault(cartItem.getName(), 0)))
                    .sum();
            membershipDiscount = applyMembershipDiscount(totalPrice - promotionPrice);
        }
        return membershipDiscount;
    }

    private int calculatePromotionPrice(CartItem cartItem, int promotionCount) {
        if (cartItem.hasPromotion()) {
            System.out.println(cartItem.getName() + " " + promotionCount);
            return cartItem.calculatePrice(promotionCount);
        }
        return 0;
    }


    // return 값은 증정 상품 갯수
    private int applyPromotion(CartItem cartItem, Inventory inventory) {
        Product product = inventory.findByName(cartItem.getName());
        Promotion promotion = product.getPromotion();

        int requiredCount = promotion.getStandardCount();
        int freeCount = promotion.getFreeCount();
        int promotionCount = requiredCount + freeCount;

        int quantity = cartItem.getQuantity();
        if (quantity < product.getTotalStock() && quantity % promotionCount == promotion.getStandardCount()) {
            if (pos.isY(convenienceInputIterator.readWantPromotion(product.getName()))) {
                quantity++;
                cartItem.plusQuantity();
            }
        }

        // 요청 수량보다 promotion 재고가 더 많은 경우
        if (product.getPromotionStock() >= quantity) {
            product.reducePromotionStock(quantity);
            return quantity / promotionCount;
        }

        int gift = 0;
        // 요청 수량보다 promotion 재고가 더 작은 경우
        while (quantity > 0) {
            if (product.getPromotionStock() >= promotionCount) {
                product.reducePromotionStock(promotionCount);
                quantity -= promotionCount;
                gift++;
                continue;
            }

            String response = convenienceInputIterator.readPromotionNotApply(product.getName(), quantity);
            if (response.equals("Y")) {
                product.reduceRegularStock(quantity - product.getPromotionStock());
                product.reducePromotionStock(product.getPromotionStock());
                break;
            }

            cartItem.minusQuantity(quantity);
            break;
        }

        return gift;
    }

    private int applyMembershipDiscount(int totalPrice) {
        int membershipDiscount = totalPrice * 30 / 100;
        return Math.min(membershipDiscount, 8000);
    }
}
