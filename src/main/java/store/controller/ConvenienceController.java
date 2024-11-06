package store.controller;

import java.util.List;
import store.domain.Convenience;
import store.dto.BuyingProduct;
import store.view.OutputView;

public class ConvenienceController {

    private final OutputView outputView;
    private final Convenience convenience;
    private final ConvenienceInputIterator convenienceInputIterator;

    public ConvenienceController(OutputView outputView, Convenience convenience, ConvenienceInputIterator convenienceInputIterator) {
        this.outputView = outputView;
        this.convenience = convenience;
        this.convenienceInputIterator = convenienceInputIterator;
    }

    public void run() {
        outputView.printWelcomeMessage();
        outputView.printProducts(convenience.getInventory());

        List<BuyingProduct> buyingProducts = convenienceInputIterator.buyingProductInput();
        convenience.sellProduct(buyingProducts);
    }
}
