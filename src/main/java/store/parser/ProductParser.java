package store.parser;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;

public class ProductParser {

    public List<Product> parse(List<String> rawProducts) {
        List<Product> products = new ArrayList<>();
        for (String rawProduct : rawProducts) {
            String[] split = rawProduct.split(",");

            String name = split[0];
            int price = Integer.parseInt(split[1]);
            int count = Integer.parseInt(split[2]);
            String promotion = split[3];

            if (promotion.equals("null")) {
                products.add(new Product(name, price, count,""));
                continue;
            }
            products.add(new Product(name, price, count, promotion));

        }
        return products;
    }
}
