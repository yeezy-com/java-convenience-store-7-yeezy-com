package store.domain.inventory;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

// 구매할 개별 상품과 그 수량을 나타내는 클래스
public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public boolean hasPromotion() {
        return product.hasPromotion();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int calculatePrice() {
        return getPrice() * quantity;
    }

    public int calculatePrice(int promotionCount) {
        return product.multiPrice(promotionCount + promotionCount * product.promotionStandardCount());
    }

    public int getQuantity() {
        return quantity;
    }

    public void plusQuantity() {
        this.quantity++;
    }

    public Promotion getPromotion() {
        return product.getPromotion();
    }

    public void minusQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
