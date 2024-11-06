package store.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 입력에 대한 View 클래스
 * 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.
 */
public class InputView {
    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        return input;
    }
}
