package store.domain;

import java.util.List;
import store.dto.BuyingProduct;

public class Receipt {

    private List<BuyingProduct> products;
    private int discountPrice;

    public Receipt(List<BuyingProduct> products) {
        this.products = products;
        this.discountPrice = 0;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public int totalPrice() {
        int sum = 0;
        for (BuyingProduct product : products) {
            sum += product.price() * product.count();
        }

        return sum;
    }

    public List<BuyingProduct> getProducts() {
        return products;
    }

    public void setDiscountPrice(int price) {
        this.discountPrice = price;
    }
}
