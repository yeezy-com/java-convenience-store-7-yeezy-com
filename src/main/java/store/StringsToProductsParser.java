package store;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Products;

public class StringsToProductsParser {

    public Products parser(List<String> rawProducts) {
        List<Product> products = new ArrayList<>();
        for (String product : rawProducts) {
            String[] splitedProduct = product.split(",");
            products.add(new Product(splitedProduct[0], Integer.parseInt(splitedProduct[1]),
                    Integer.parseInt(splitedProduct[2]), splitedProduct[3]));
        }
        return new Products(products);
    }
}
