package store.controller;

import store.domain.Convenience;
import store.view.OutputView;

public class ConvenienceController {

    private final OutputView outputView;
    private final Convenience convenience;

    public ConvenienceController(OutputView outputView, Convenience convenience) {
        this.outputView = outputView;
        this.convenience = convenience;
    }

    public void run() {
        outputView.printWelcomeMessage();
        outputView.printProducts(convenience.getInventory());

    }
}
