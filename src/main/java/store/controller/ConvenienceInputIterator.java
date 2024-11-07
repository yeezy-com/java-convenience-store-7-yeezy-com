package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Receipt;
import store.dto.BuyingProduct;
import store.view.InputView;

public class ConvenienceInputIterator {

    private final InputIterator inputIterator;
    private final InputView inputView;

    public ConvenienceInputIterator(InputIterator inputIterator, InputView inputView) {
        this.inputIterator = inputIterator;
        this.inputView = inputView;
    }

    public Receipt buyingProductInput(Inventory inventory) {
        List<BuyingProduct> buyingProducts = inputIterator.retryUntilSuccess(() -> {
            String[] rawProducts = inputView.readItem().split(",", -1);
            List<BuyingProduct> products = new ArrayList<>();
            for (String rawProduct : rawProducts) {
                String[] parts = rawProduct.substring(1, rawProduct.length() - 1).split("-", 2);
                String productName = parts[0];
                int productCount = Integer.parseInt(parts[1]);

                validateProductContainInInventory(inventory, productName);
                validateStockCountInInventory(inventory, productName, productCount);

                Product contain = inventory.getContain(productName);
                products.add(new BuyingProduct(productName, productCount, contain.getPrice()));
            }
            return products;
        });
        return new Receipt(buyingProducts);
    }

    public String membershipAskInput() {
        String answer = inputIterator.retryUntilSuccess(() -> {
            String ans = inputView.readMembershipAsk();
            validateYorN(ans);
            return ans;
        });
        return answer;
    }

    private void validateProductContainInInventory(Inventory inventory, String productName) {
        if (inventory.contain(productName)) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    private void validateStockCountInInventory(Inventory inventory, String productName, int productCount) {
        if (inventory.isAvailable(productName, productCount)) {
            return;
        }
        throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    private void validateYorN(String ans) {
        if (ans.equals("Y") || ans.equals("N")) {
            return;
        }
        throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
