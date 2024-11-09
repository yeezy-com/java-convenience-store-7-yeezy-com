package store.view;

import store.domain.inventory.Inventory;
import store.domain.product.Product;

public class InventoryOutputView {

    public static final String PRODUCT_START = "- ";
    public static final String PRODUCT_NAME_PRICE = "%s %,d원";
    public static final String PRODUCT_STOCK_OR_PROMOTION_NAME = " %s%n";
    public static final String PRODUCT_STRING_STOCK = " %s";
    public static final String NONE_STOCK = "재고 없음";
    public static final String PRODUCT_STOCK = "%,d개";
    public static final String NOT_THING = "";

    public void printHasProducts(Inventory inventory) {
        inventory.forEach(this::printProduct);
    }

    private void printProduct(Product product) {
        printPromotionProduct(product);
        printRegularProduct(product);
    }

    private void printRegularProduct(Product product) {
        System.out.printf(PRODUCT_START);
        System.out.printf(PRODUCT_NAME_PRICE, product.getName(), product.getPrice());
        System.out.printf(PRODUCT_STOCK_OR_PROMOTION_NAME, checkRegularStock(product));
    }

    private void printPromotionProduct(Product product) {
        if (product.hasPromotion()) {
            System.out.printf(PRODUCT_START);
            System.out.printf(PRODUCT_NAME_PRICE, product.getName(), product.getPrice());
            System.out.printf(PRODUCT_STRING_STOCK, checkPromotionStock(product));
            System.out.printf(PRODUCT_STOCK_OR_PROMOTION_NAME, checkProductPromotion(product));
        }
    }

    private String checkPromotionStock(Product product) {
        if (product.getPromotionStock() > 0) {
            return String.format(PRODUCT_STOCK, product.getPromotionStock());
        }
        return NONE_STOCK;
    }

    private String checkRegularStock(Product product) {
        if (product.getRegularStock() > 0) {
            return String.format(PRODUCT_STOCK, product.getRegularStock());
        }
        return NONE_STOCK;
    }

    private String checkProductPromotion(Product product) {
        if (product.hasPromotion()) {
            return product.promotionName();
        }
        return NOT_THING;
    }
}
