package store.view;

import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Receipt;
import store.dto.BuyingProduct;

/**
 * 출력을 보여주는 일 담당 클래스
 * 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.
 */
public class OutputView {

    public static final String ERROR_PRIFIX = "[ERROR] ";

    public void printWelcomeMessage() {
        System.out.printf("안녕하세요. W편의점입니다.%n");
        System.out.printf("현재 보유하고 있는 상품입니다.%n%n");
    }

    public void printProducts(Inventory inventory) {
        List<Product> products = inventory.getProducts();
        for (Product product : products) {
            System.out.print("- ");
            System.out.printf("%s %,d원", product.getName(), product.getPrice());
            if (product.getCount() > 0) {
                System.out.printf(" %d개 %s%n", product.getCount(), product.getPromotion());
                continue;
            }
            System.out.printf(" %s %s%n", "재고 없음", product.getPromotion());
        }
        System.out.printf("%n");
    }

    public void printErrorMessage(Exception e) {
        System.out.println(ERROR_PRIFIX + e.getMessage());
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("===========W 편의점============");
        System.out.printf("%-10s\t%5s\t%6s%n", "상품명", "수량", "금액");
        for (BuyingProduct buyingProduct : receipt.getProducts()) {
            System.out.printf("%-10s\t%,5d\t%,10d%n", buyingProduct.name(), buyingProduct.count(), buyingProduct.count() * buyingProduct.price());
        }
        System.out.println("===========증   정=============");
        System.out.println("==============================");
        System.out.printf("%-10s %5d %,11d%n", "총구매액", 10, 10000);
        System.out.printf("%-20s %,-20d%n", "행사할인", -2000);
        System.out.printf("%-20s %,-20d%n", "멤버십할인", -0);
        System.out.printf("%-20s %,-20d%n", "내실돈", receipt.getDiscountPrice());
    }
}
