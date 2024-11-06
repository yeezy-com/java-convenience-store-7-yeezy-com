package store.config;

import java.util.List;
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

    public ConvenienceController convenienceController() {
        if (this.convenienceController == null) {
            this.convenienceController = new ConvenienceController(outputView(), convenience());
        }
        return convenienceController;
    }
}
