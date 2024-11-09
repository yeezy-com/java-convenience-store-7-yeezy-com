package store.view;

import store.domain.Receipt;
import store.domain.inventory.Inventory;

/**
 * 출력을 보여주는 일 담당 클래스
 * 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.
 */
public class OutputView {

    private static final String ERROR_PRIFIX = "[ERROR] ";

    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.%n";
    private static final String INVENTORY_INSTRUCTION = "현재 보유하고 있는 상품입니다.%n";

    public static final String NEW_LINE = "%n";
    public static final String ERROR_MSG_FORMAT = "%s%s%n";

    private final ReceiptOutputView receiptOutputView;
    private final InventoryOutputView inventoryOutputView;

    public OutputView(ReceiptOutputView receiptOutputView, InventoryOutputView inventoryOutputView) {
        this.receiptOutputView = receiptOutputView;
        this.inventoryOutputView = inventoryOutputView;
    }

    public void printWelcomeMessage() {
        System.out.printf(WELCOME_MESSAGE);
        System.out.printf(INVENTORY_INSTRUCTION);
        printNewLine();
    }

    public void printHasProducts(Inventory inventory) {
        inventoryOutputView.printHasProducts(inventory);
        printNewLine();
    }

    public void printReceipt(Receipt receipt) {
        printNewLine();
        receiptOutputView.printReceipt(receipt);
    }

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.printf(ERROR_MSG_FORMAT, ERROR_PRIFIX, e.getMessage());
    }

    public void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
