package store.view;

import java.util.List;
import store.domain.Inventory;
import store.domain.Product;

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
            System.out.printf("%s %,d원 %d개 %s%n", product.getName(), product.getPrice(), product.getCount(), product.getPromotion());
        }
        System.out.printf("%n");
    }

    public void printErrorMessage(Exception e) {
        System.out.println(ERROR_PRIFIX + e.getMessage());
    }
}
