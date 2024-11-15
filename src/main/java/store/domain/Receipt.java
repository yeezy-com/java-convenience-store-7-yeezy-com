package store.domain;

import java.util.List;
import java.util.Map;
import store.domain.inventory.CartItem;

public class Receipt {

    private final List<CartItem> product;
    private final Map<String, Integer> promotionCount;
    private final int membershipDiscount;

    public Receipt(List<CartItem> product, Map<String, Integer> promotionCount, int membershipDiscount) {
        this.product = product;
        this.membershipDiscount = membershipDiscount;
        this.promotionCount = promotionCount;
    }

    public int totalPromotionPrice() {
        return product.stream()
                .filter(CartItem::hasPromotion)
                .mapToInt(cartItem -> calculatePromotionDiscountPrice(cartItem,
                        promotionCount.getOrDefault(cartItem.getName(), 0)))
                .sum();
    }

    private int calculatePromotionDiscountPrice(CartItem cartItem, int promotionCount) {
        if (cartItem.hasPromotion()) {
            return promotionCount * cartItem.getPrice();
        }
        return 0;
    }

    public List<CartItem> getProduct() {
        return product;
    }

    public int calculateTotalPrice() {
        return product.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getPromotionCount(String name) {
        return promotionCount.getOrDefault(name, 0);
    }

    public boolean promotionContains(String name) {
        return promotionCount.containsKey(name);
    }

    public int getTotalCount() {
        return product.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
