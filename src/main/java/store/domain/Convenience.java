package store.domain;

import java.util.List;
import store.dto.BuyingProduct;

public class Convenience {

    private final Inventory inventory;

    public Convenience(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void sellProduct(List<BuyingProduct> buyingProducts) {
        List<Product> products = inventory.getProducts();
        for (BuyingProduct buyingProduct : buyingProducts) {
            Product stuff = products.stream()
                    .filter(product -> buyingProduct.name().equals(product.getName()))
                    .findFirst()
                    .get();

            if (stuff.count >= buyingProduct.count()) {
                stuff.count -= buyingProduct.count();
            }
    public void sellProduct(Receipt buyingProducts) {
        inventory.popStuffCount(buyingProducts);
    }

        }
    }
}
