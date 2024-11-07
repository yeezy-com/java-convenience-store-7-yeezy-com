package store.domain;

public class Product {
    String name;
    int price;
    int count;
    String promotion;

    public Product(String name, int price, int count, String promotion) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public boolean isAvailable(int count) {
        return count <= this.count;
    }
}