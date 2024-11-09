package store.controller;

import store.domain.seller.Seller;
import store.domain.inventory.Inventory;
import store.view.OutputView;

public class Convenience {

    private final OutputView outputView;
    private final ConvenienceInputIterator convenienceInputIterator;
    private final Seller seller;
    private final Inventory inventory;

    public Convenience(OutputView outputView, Seller seller, Inventory inventory,
                       ConvenienceInputIterator convenienceInputIterator) {
        this.outputView = outputView;
        this.seller = seller;
        this.inventory = inventory;
        this.convenienceInputIterator = convenienceInputIterator;
    }

    public void enter() {
        while (true) {
            outputView.printWelcomeMessage();
            outputView.printHasProducts(inventory);

            outputView.printReceipt(seller.processPurchase(inventory));
            if (convenienceInputIterator.readBuyAgain().equals("N")) {
                break;
            }
            outputView.printNewLine();
        }
    }
}
