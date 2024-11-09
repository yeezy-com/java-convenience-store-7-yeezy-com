package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.inventory.CartItem;
import store.domain.inventory.Inventory;
import store.domain.product.Product;
import store.view.InputView;

public class ConvenienceInputIterator {

    public static final String WRONG_INPUT_ERR = "잘못된 입력입니다. 다시 입력해 주세요.";
    public static final String NOT_ACCEPT_FORMAT = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    public static final String STOCK_OVER_FLOW_ERR = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String NOT_EXIST_PRODUCT_ERR = "존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public static final String PRODUCT_BUY_INPUT_PREFIX = "[";
    public static final String PRODUCT_BUY_INPUT_SUFFIX = "]";
    public static final String PRODUCT_BUY_DELIMITER = "-";
    private final InputIterator inputIterator;
    private final InputView inputView;

    public ConvenienceInputIterator(InputIterator inputIterator, InputView inputView) {
        this.inputIterator = inputIterator;
        this.inputView = inputView;
    }

    public List<CartItem> buyingProductInput(Inventory inventory) {
        List<CartItem> buyingProducts = inputIterator.retryUntilSuccess(() -> {
            String[] rawProducts = inputView.readItem().split(",", -1);
            List<CartItem> products = new ArrayList<>();
            for (String rawProduct : rawProducts) {
                validateFormat(rawProduct);

                String[] parts = rawProduct.substring(1, rawProduct.length() - 1).split("-", 2);
                String productName = parts[0];
                validateIsBlank(productName);
                int productCount = Integer.parseInt(parts[1]);

                validateProductContainInInventory(inventory, productName);
                validateStockCountInInventory(inventory, productName, productCount);

                Product sellProduct = inventory.findByName(productName);
                products.add(new CartItem(sellProduct, productCount));
            }
            return products;
        });
        return buyingProducts;
    }

    private void validateIsBlank(String productName) {
        if (productName.isBlank()) {
            throw new IllegalArgumentException(WRONG_INPUT_ERR);
        }
    }

    private void validateFormat(String rawProduct) {
        if (rawProduct.startsWith(PRODUCT_BUY_INPUT_PREFIX) && rawProduct.endsWith(PRODUCT_BUY_INPUT_SUFFIX)
        && rawProduct.contains(PRODUCT_BUY_DELIMITER)) {
            return;
        }
        throw new IllegalArgumentException(NOT_ACCEPT_FORMAT);
    }

    public String readMembershipApply() {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readMembershipApply();
            validateYorN(ans);
            return ans;
        });
    }

    public String readBuyAgain() {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readBuyAgain();
            validateYorN(ans);
            return ans;
        });
    }

    public String readPromotionNotApply(String productName, int quantity) {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readPromotionNotApply(productName, quantity);
            validateYorN(ans);
            return ans;
        });
    }

    public String readWantPromotion(String productName) {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readWantPromotion(productName);
            validateYorN(ans);
            return ans;
        });
    }

    private void validateProductContainInInventory(Inventory inventory, String productName) {
        if (inventory.contain(productName)) {
            return;
        }
        throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERR);
    }

    private void validateStockCountInInventory(Inventory inventory, String productName, int productCount) {
        if (inventory.isAvailable(productName, productCount)) {
            return;
        }
        throw new IllegalArgumentException(STOCK_OVER_FLOW_ERR);
    }

    private void validateYorN(String ans) {
        if (ans.equals("Y") || ans.equals("N")) {
            return;
        }
        throw new IllegalArgumentException(WRONG_INPUT_ERR);
    }
}
