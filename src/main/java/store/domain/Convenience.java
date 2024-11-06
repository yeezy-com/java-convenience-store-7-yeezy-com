package store.domain;

import java.util.List;
import store.StringsToProductsParser;
import store.file.ProductFileReader;
import store.view.InputView;
import store.view.OutputView;

public class Convenience {

    private final StringsToProductsParser parser = new StringsToProductsParser();
    private ProductFileReader productFileReader;
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public void run() {
        outputView.printWelcomeMessage();

        productFileReader = new ProductFileReader("src/main/resources/products.md");
        List<String> read = productFileReader.read();
        Products products = parser.parser(read);

        outputView.printProducts(products);

    }
}
