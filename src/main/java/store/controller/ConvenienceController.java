package store.controller;

import store.domain.Convenience;
import store.domain.Receipt;
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

        Receipt receipt = convenienceInputIterator.buyingProductInput(convenience.getInventory());
        convenience.sellProduct(receipt);
        String answer = convenienceInputIterator.membershipAskInput();
        convenience.membershipDiscount(answer, receipt);
        outputView.printReceipt(receipt);
    }
}
