package store.controller;

import java.util.Arrays;
import java.util.List;
import store.global.ErrorMessage;
import store.global.Answer;
import store.domain.inventory.CartItem;
import store.domain.inventory.Inventory;
import store.domain.product.Product;
import store.view.InputView;

public class ConvenienceInputIterator {

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
        return inputIterator.retryUntilSuccess(() -> {
            String[] rawProducts = inputView.readItem().split(",", -1);
            validateFormat(rawProducts);
            return Arrays.stream(rawProducts)
                    .map(rawProduct1 -> {
                        String[] parts = rawProduct1.substring(1, rawProduct1.length() - 1).split("-", 2);
                        String productName = parts[0];
                        validateNumber(parts[1]);
                        int productCount = Integer.parseInt(parts[1]);
                        validateProductContainInInventory(inventory, productName);
                        validateStockCountInInventory(inventory, productName, productCount);

                        Product sellProduct1 = inventory.findByName(productName);
                        return new CartItem(sellProduct1, productCount);
                    })
                    .toList();
        });
    }

    private void validateFormat(String[] rawProducts) {
        Arrays.stream(rawProducts)
                .forEach(this::validateFormat);
        Arrays.stream(rawProducts)
                .forEach(this::validateIsBlank);
    }

    private void validateNumber(String rawNumber) {
        validateIsConvertNumber(rawNumber);
        validateIsPositive(rawNumber);
    }

    public Answer readMembershipApply() {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readMembershipApply();
            validateYorN(ans);
            return Answer.of(ans);
        });
    }

    public Answer readBuyAgain() {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readBuyAgain();
            validateYorN(ans);
            return Answer.of(ans);
        });
    }

    public Answer readPromotionNotApply(String productName, int quantity) {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readPromotionNotApply(productName, quantity);
            validateYorN(ans);
            return Answer.of(ans);
        });
    }

    public Answer readWantPromotion(String productName) {
        return inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readWantPromotion(productName);
            validateYorN(ans);
            return Answer.of(ans);
        });
    }

    private void validateIsConvertNumber(String productCount) {
        try {
            Integer.parseInt(productCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT_ERR.getMessage());
        }
    }

    private void validateIsPositive(String productCount) {
        if (Integer.parseInt(productCount) <= 0) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT_ERR.getMessage());
        }
    }

    private void validateIsBlank(String productName) {
        if (productName.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT_ERR.getMessage());
        }
    }

    private void validateFormat(String rawProduct) {
        if (rawProduct.startsWith(PRODUCT_BUY_INPUT_PREFIX) && rawProduct.endsWith(PRODUCT_BUY_INPUT_SUFFIX)
        && rawProduct.contains(PRODUCT_BUY_DELIMITER)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.NOT_ACCEPT_FORMAT.getMessage());
    }

    private void validateProductContainInInventory(Inventory inventory, String productName) {
        if (inventory.contain(productName)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_PRODUCT_ERR.getMessage());
    }

    private void validateStockCountInInventory(Inventory inventory, String productName, int productCount) {
        if (inventory.isAvailable(productName, productCount)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.STOCK_OVER_FLOW_ERR.getMessage());
    }

    private void validateYorN(String ans) {
        Answer.validateYorN(ans);
    }
}
