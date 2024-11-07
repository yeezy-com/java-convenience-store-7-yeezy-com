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

            products.add(new BuyingProduct(productName, productCount));
        }
        return products;
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

    }
}
