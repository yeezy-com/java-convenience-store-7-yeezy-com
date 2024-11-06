package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.dto.BuyingProduct;
import store.view.InputView;

public class ConvenienceInputIterator {

    private final InputIterator inputIterator;
    private final InputView inputView;

    public ConvenienceInputIterator(InputIterator inputIterator, InputView inputView) {
        this.inputIterator = inputIterator;
        this.inputView = inputView;
    }

    public List<BuyingProduct> buyingProductInput() {
        String item = inputIterator.retryUntilSuccess(inputView::readItem);
        String[] split = item.split(",", -1);

        List<BuyingProduct> products = new ArrayList<>();
        for (String s : split) {
            String productName = s.substring(1, s.indexOf("-"));
            int productCount = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.length() - 1));

            products.add(new BuyingProduct(productName, productCount));
        }
        return products;
    }
}
