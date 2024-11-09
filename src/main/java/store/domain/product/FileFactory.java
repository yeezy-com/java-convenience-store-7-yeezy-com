package store.domain.product;

import java.util.List;
import store.util.FileConverter;
import store.util.MdFileReader;
import store.domain.promotion.Promotion;

public class FileFactory {

    public static final String PRODUCTS_FILE_NAME = "products";
    public static final String PROMOTIONS_FILE_NAME = "promotions";

    private final MdFileReader mdFileReader;
    private final FileConverter fileConverter;

    public FileFactory(MdFileReader mdFileReader, FileConverter fileConverter) {
        this.mdFileReader = mdFileReader;
        this.fileConverter = fileConverter;
    }

    public List<Product> productGetFromFile(List<Promotion> promotions) {
        List<String> readFileString = mdFileReader.read(PRODUCTS_FILE_NAME);
        return fileConverter.convertToProducts(readFileString, promotions);
    }

    public List<Promotion> promotionsGetFromFile() {
        List<String> readFileString = mdFileReader.read(PROMOTIONS_FILE_NAME);
        return fileConverter.convertToPromotions(readFileString);
    }
}
