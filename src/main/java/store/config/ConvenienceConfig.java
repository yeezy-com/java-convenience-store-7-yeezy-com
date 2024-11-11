package store.config;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.controller.Convenience;
import store.domain.Date;
import store.domain.Membership;
import store.util.FileConverter;
import store.util.MdFileReader;
import store.controller.ConvenienceInputIterator;
import store.controller.InputIterator;
import store.domain.seller.Seller;
import store.domain.inventory.Inventory;
import store.domain.product.FileFactory;
import store.domain.promotion.PromotionManager;
import store.view.InputView;
import store.view.InventoryOutputView;
import store.view.OutputView;
import store.view.ReceiptOutputView;

public class ConvenienceConfig {

    private InputView inputView;
    private OutputView outputView;
    private ReceiptOutputView receiptOutputView;
    private InventoryOutputView inventoryOutputView;
    private InputIterator inputIterator;
    private ConvenienceInputIterator convenienceInputIterator;
    private MdFileReader mdFileReader;
    private FileConverter fileConverter;
    private FileFactory fileFactory;
    private Convenience convenience;
    private Membership membership;

    private Date date;
    private Seller seller;
    private Inventory inventory;
    private PromotionManager promotionManager;

    private PromotionManager promotionManager() {
        if (this.promotionManager == null) {
            this.promotionManager = new PromotionManager(fileFactory().promotionsGetFromFile());
        }
        return this.promotionManager;
    }

    private Inventory inventory() {
        if (this.inventory == null) {
            this.inventory = new Inventory(fileFactory().productGetFromFile(promotionManager().getPromotions()));
        }
        return this.inventory;
    }

    private LocalDate now() {
        return DateTimes.now().toLocalDate();
    }

    private Date date() {
        if (this.date == null) {
            this.date = new Date(now());
        }
        return this.date;
    }

    private Seller seller() {
        if (this.seller == null) {
            this.seller = new Seller(convenienceInputIterator(), membership(), date());
        }
        return this.seller;
    }


    private InputView inputView() {
        if (this.inputView == null) {
            this.inputView = new InputView();
        }
        return this.inputView;
    }

    private ReceiptOutputView receiptOutputView() {
        if (this.receiptOutputView == null) {
            this.receiptOutputView = new ReceiptOutputView();
        }
        return this.receiptOutputView;
    }

    private InventoryOutputView inventoryOutputView() {
        if (this.inventoryOutputView == null) {
            this.inventoryOutputView = new InventoryOutputView();
        }
        return this.inventoryOutputView;
    }

    private OutputView outputView() {
        if (this.outputView == null) {
            this.outputView = new OutputView(receiptOutputView(), inventoryOutputView());
        }
        return this.outputView;
    }

    private InputIterator inputIterator() {
        if (this.inputIterator == null) {
            this.inputIterator = new InputIterator(outputView());
        }
        return inputIterator;
    }

    private ConvenienceInputIterator convenienceInputIterator() {
        if (this.convenienceInputIterator == null) {
            this.convenienceInputIterator = new ConvenienceInputIterator(inputIterator(), inputView());
        }
        return this.convenienceInputIterator;
    }

    private MdFileReader mdFileReader() {
        if (this.mdFileReader == null) {
            this.mdFileReader = new MdFileReader();
        }
        return this.mdFileReader;
    }

    private FileConverter fileConverter() {
        if (this.fileConverter == null) {
            this.fileConverter = new FileConverter();
        }
        return fileConverter;
    }

    private FileFactory fileFactory() {
        if (this.fileFactory == null) {
            this.fileFactory = new FileFactory(mdFileReader(), fileConverter());
        }
        return fileFactory;
    }

    private Membership membership() {
        if (this.membership == null) {
            this.membership = new Membership(convenienceInputIterator());
        }
        return this.membership;
    }

    public Convenience convenience() {
        if (this.convenience == null) {
            this.convenience = new Convenience(outputView(), seller(), inventory(), convenienceInputIterator());
        }
        return this.convenience;
    }
}
