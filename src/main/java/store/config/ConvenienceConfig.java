package store.config;

import java.util.List;
import store.controller.ConvenienceInputIterator;
import store.controller.InputIterator;
import store.domain.Convenience;
import store.domain.Inventory;
import store.domain.Product;
import store.controller.ConvenienceController;
import store.file.MdFileReader;
import store.parser.ProductParser;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceConfig {

    private final MdFileReader fileReader = new MdFileReader();
    private final ProductParser productParser = new ProductParser();

    private InputView inputView;
    private OutputView outputView;
    private Inventory inventory;
    private Convenience convenience;
    private ConvenienceController convenienceController;
    private InputIterator inputIterator;
    private ConvenienceInputIterator convenienceInputIterator;

    public InputView inputView() {
        if (inputView == null) {
            this.inputView = new InputView();
        }
        return inputView;
    }

    public OutputView outputView() {
        if (outputView == null) {
            this.outputView = new OutputView();
        }
        return outputView;
    }

    public Inventory inventory() {
        if (this.inventory == null) {
            this.inventory = createInventory();
        }
        return inventory;
    }

    private Inventory createInventory() {
        List<String> rawProducts = fileReader.read("products.md");
        List<Product> products = productParser.parse(rawProducts);
        return new Inventory(products);
    }

    public Convenience convenience() {
        if (this.convenience == null) {
            this.convenience = new Convenience(inventory());
        }
        return convenience;
    }

    public InputIterator inputIterator() {
        if (this.inputIterator == null) {
            this.inputIterator = new InputIterator(outputView());
        }
        return inputIterator;
    }

    public ConvenienceInputIterator convenienceInputIterator() {
        if (this.convenienceInputIterator == null) {
            this.convenienceInputIterator = new ConvenienceInputIterator(inputIterator(), inputView());
        }
        return convenienceInputIterator;
    }

    public ConvenienceController convenienceController() {
        if (this.convenienceController == null) {
            this.convenienceController = new ConvenienceController(outputView(), convenience(), convenienceInputIterator());
        }
        return convenienceController;
    }
}
