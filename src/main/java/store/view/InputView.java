package store.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 입력에 대한 View 클래스
 * 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.
 */
public class InputView {
    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String readPromotionNotApply(String productName, int quantity) {
        printNewLine();
        System.out.printf("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, quantity);
        return Console.readLine();
    }

    public String readWantPromotion(String productName) {
        printNewLine();
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName);
        return Console.readLine();
    }

    public String readMembershipApply() {
        printNewLine();
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public String readBuyAgain() {
        printNewLine();
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }

    private void printNewLine() {
        System.out.printf("%n");
    }
}
