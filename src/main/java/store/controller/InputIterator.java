package store.controller;

import java.util.function.Supplier;
import store.view.OutputView;

public class InputIterator {

    private final OutputView outputView;

    public InputIterator(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
