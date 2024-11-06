package store.domain;

import java.util.List;
import java.util.Optional;

public class Inventory {

    List<Product> products;

    public Inventory(List<Product> products) {
        this.products = products;
    }

    public boolean isAvailable(String name, int count) {
        Optional<Product> first = products.stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst();

        return first.filter(product -> product.getCount() >= count).isPresent();

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
}
