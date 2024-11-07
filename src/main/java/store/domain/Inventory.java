package store.domain;

import java.util.List;
import java.util.Optional;
import store.dto.BuyingProduct;

public class Inventory {

    List<Product> products;

    public Inventory(List<Product> products) {
        this.products = products;
    }

    public boolean contain(String name) {
        Optional<Product> stuff = products.stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst();

        return stuff.isPresent();
    }

    public boolean isAvailable(String name, int count) {
        Optional<Product> stuff = products.stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst();

        return stuff.filter(product -> product.getCount() >= count).isPresent();
    }

    public void remove(String name, int count) {
        if (isAvailable(name, count)) {
            Product product = products.stream()
                    .filter(pd -> name.equals(pd.getName()))
                    .findFirst()
                    .get();
            product.count -= count;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getContain(String productName) {
        return products.stream()
                .filter(product -> productName.equals(product.getName()))
                .findFirst()
                .get();
    }

    public void popStuffCount(Receipt buyingProducts) {
        for (BuyingProduct buyingProduct : buyingProducts.getProducts()) {
            Product stuff = products.stream()
                    .filter(product -> product.name.equals(buyingProduct.name()))
                    .findFirst()
                    .get();

            if (stuff.isAvailable(buyingProduct.count())) {
                stuff.count -= buyingProduct.count();
                continue;
            }

            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
