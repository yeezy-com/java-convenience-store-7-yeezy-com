package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final int count;
    private final String promotion;

    public Product(String name, int price, int count, String promotion) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getPromotion() {
        return promotion;
    }
}
