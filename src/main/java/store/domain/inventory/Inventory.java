package store.domain.inventory;

import java.util.List;
import java.util.function.Consumer;
import store.domain.product.Product;

public class Inventory {

    private final List<Product> products;

    public Inventory(List<Product> products) {
        this.products = products;
    }

    public void forEach(Consumer<Product> action) {
        products.forEach(action);
    }

    public boolean contain(String productName) {
        return products.stream()
                .anyMatch(product -> productName.equals(product.getName()));
    }

    public boolean isAvailable(String productName, int productCount) {
        Product sellProduct = findByName(productName);
        return sellProduct.allStockMoreThen(productCount);
    }

    public Product findByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .get();
    }
}
