package store.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

public class FileConverter {
    public List<Promotion> convertToPromotions(List<String> rawData) {
        return rawData.stream()
                .skip(1)
                .map(rawPromotion -> promotionConvert(rawPromotion.split(",")))
                .toList();
    }

    private Promotion promotionConvert(String[] parts) {
        String promoName = parts[0];
        int standardCount = Integer.parseInt(parts[1]);
        int freeCount = Integer.parseInt(parts[2]);
        LocalDate startDate = LocalDate.parse(parts[3]);
        LocalDate endDate = LocalDate.parse(parts[4]);

        return new Promotion(promoName, standardCount, freeCount, startDate, endDate);
    }

    public List<Product> convertToProducts(List<String> rawData, List<Promotion> promotions) {
        List<Product> products = new ArrayList<>();
        rawData.stream()
                .skip(1)
                .forEach(rawProduct -> productConvert(rawProduct.split(","), products, promotions));
        return products;
    }

    private void productConvert(String[] parts, List<Product> products, List<Promotion> promotions) {
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int stock = Integer.parseInt(parts[2]);
        Promotion promotion = getPromotion(parts[3], promotions);

        Product product = findByName(products, name);
        if (product == null) {
            if (promotion == null) {
                products.add(new Product(name, price, stock, 0, promotion));
                return;
            }
            products.add(new Product(name, price, 0, stock, promotion));
            return;
        }

        product.updateRegularStock(stock);
    }

    private Product findByName(List<Product> products, String name) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();

        return productOptional.orElse(null);
    }

    private Promotion getPromotion(String promotionName, List<Promotion> promotions) {
        if (promotionName.equals("null")) {
            return null;
        }

        return promotions.stream()
                .filter(promotion -> promotionName.equals(promotion.type()))
                .findFirst()
                .get();
    }
}
