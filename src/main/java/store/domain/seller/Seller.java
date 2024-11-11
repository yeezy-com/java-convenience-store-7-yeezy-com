package store.domain.seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.global.Answer;
import store.domain.Date;
import store.domain.Membership;
import store.domain.Receipt;
import store.controller.ConvenienceInputIterator;
import store.domain.inventory.CartItem;
import store.domain.inventory.Inventory;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

public class Seller {

    private final ConvenienceInputIterator convenienceInputIterator;
    private final Membership membership;
    private final Date date;

    public Seller(ConvenienceInputIterator convenienceInputIterator, Membership membership, Date date) {
        this.convenienceInputIterator = convenienceInputIterator;
        this.membership = membership;
        this.date = date;
    }

    public Receipt processPurchase(Inventory inventory) {
        List<CartItem> cartItems = convenienceInputIterator.buyingProductInput(inventory);

        Map<String, Integer> promotionCount = calculatePromotionCount(inventory, cartItems);
        int totalPrice = calculateTotalPrice(cartItems);
        int promotionPrice = calculatePromotionPrice(cartItems, promotionCount);

        int membershipDiscount = membership.membershipProcess(totalPrice, promotionPrice);
        return new Receipt(cartItems, promotionCount, membershipDiscount);
    }

    private int calculatePromotionPrice(List<CartItem> cartItems, Map<String, Integer> promotionCount) {
        return cartItems.stream()
                .mapToInt(cartItem -> calculatePromotionPrice(cartItem,
                        promotionCount.getOrDefault(cartItem.getName(), 0)))
                .sum();
    }

    private Map<String, Integer> calculatePromotionCount(Inventory inventory, List<CartItem> cartItems) {
        Map<String, Integer> promotionCount = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.hasPromotion() && cartItem.getPromotion().isWithinPromotionPeriod(date.today())) {
                promotionCount.put(cartItem.getName(), applyPromotion(cartItem, inventory));
                continue;
            }
            Product sellProduct = inventory.findByName(cartItem.getName());
            sellProduct.reduceRegularStock(cartItem.getQuantity());
        }
        return promotionCount;
    }

    private int calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    private int calculatePromotionPrice(CartItem cartItem, int promotionCount) {
        if (cartItem.hasPromotion()) {
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
            if (convenienceInputIterator.readWantPromotion(product.getName()) == Answer.IS_YES) {
                quantity++;
                cartItem.plusQuantity();
            }
        }

        if (product.getPromotionStock() >= quantity) {
            product.reducePromotionStock(quantity);
            return quantity / promotionCount;
        }

        return promotionMoreThenInventoryProcess(cartItem, quantity, product, promotionCount);
    }

    private int promotionMoreThenInventoryProcess(CartItem cartItem, int quantity, Product product, int promotionCount) {
        int gift = 0;
        while (quantity > 0) {
            if (product.getPromotionStock() >= promotionCount) {
                product.reducePromotionStock(promotionCount);
                quantity -= promotionCount;
                gift++;
                continue;
            }

            Answer response = convenienceInputIterator.readPromotionNotApply(product.getName(), quantity);
            if (response == Answer.IS_YES) {
                product.reduceRegularStock(quantity - product.getPromotionStock());
                product.reducePromotionStock(product.getPromotionStock());
                break;
            }

            cartItem.minusQuantity(quantity);
            break;
        }

        return gift;
    }
}
