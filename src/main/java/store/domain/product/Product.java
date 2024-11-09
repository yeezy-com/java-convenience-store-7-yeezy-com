package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {

    private final String name;
    private final int price;
    private int promotionStock;
    private int regularStock;
    private final Promotion promotion;

    public Product(String name, int price, int regularStock, int promotionStock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.regularStock = regularStock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public String promotionName() {
        return promotion.type();
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void updateRegularStock(int stock) {
        this.regularStock = stock;
    }

    public void reduceRegularStock(int quantity) {
        this.regularStock -= quantity;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public void reducePromotionStock(int quantity) {
        this.promotionStock -= quantity;
    }

    public int getRegularStock() {
        return regularStock;
    }


    public boolean allStockMoreThen(int productCount) {
        return regularStock + promotionStock >= productCount;
    }

    public int getTotalStock() {
        return regularStock + promotionStock;
    }

    public int promotionStandardCount() {
        return promotion.getStandardCount();
    }

    public int multiPrice(int count) {
        return count * price;
    }
}
