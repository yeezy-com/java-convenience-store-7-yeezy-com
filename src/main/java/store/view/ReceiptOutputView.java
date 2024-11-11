package store.view;

import store.domain.Receipt;
import store.domain.inventory.CartItem;

public class ReceiptOutputView {

    private static final String RECEIPT_START_MSG = "===========W 편의점=============%n";
    private static final String RECEIPT_CATEGORY = String.format("%s\t\t\t%s\t\t%s%n", "상품명", "수량", "금액");
    public static final String FREE_PRODUCT_INSTRUCTION = "===========증\t정=============%n";
    public static final String CONTOUR = "==============================%n";

    public static final String TOTAL_PRICE = "총구매액";
    public static final String PROMOTION_DISCOUNT = "행사할인";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    public static final String RECEIVE_PRICE = "내실돈";

    public void printReceipt(Receipt receipt) {
        printReceiptInstruction();
        printBuyProductsArea(receipt);
        printFreeProductsArea(receipt);
        printReceiptEndInformation(receipt);
    }

    private void printBuyProductsArea(Receipt receipt) {
        System.out.printf(RECEIPT_CATEGORY);
        printBuyProducts(receipt);
    }

    private void printFreeProductsArea(Receipt receipt) {
        System.out.printf(FREE_PRODUCT_INSTRUCTION);
        printFreeProducts(receipt);
        System.out.printf(CONTOUR);
    }

    private void printReceiptEndInformation(Receipt receipt) {
        System.out.printf("%s\t\t\t%,d\t\t%,6d%n", TOTAL_PRICE, receipt.getTotalCount(), receipt.calculateTotalPrice());
        System.out.printf("%s\t\t\t\t\t-%,d%n", PROMOTION_DISCOUNT, receipt.totalPromotionPrice());
        System.out.printf("%s\t\t\t\t\t-%,d%n", MEMBERSHIP_DISCOUNT, receipt.getMembershipDiscount());
        System.out.printf("%s\t\t\t\t\t%,6d%n", RECEIVE_PRICE, receipt.calculateTotalPrice() - receipt.getMembershipDiscount() - receipt.totalPromotionPrice());
    }

    private void printFreeProducts(Receipt receipt) {
        for (CartItem cartItem : receipt.getProduct()) {
            if (receipt.promotionContains(cartItem.getName()) && receipt.getPromotionCount(cartItem.getName()) != 0) {
                printFreeProductWithLengthCondition(receipt, cartItem);
            }
        }
    }

    private void printBuyProducts(Receipt receipt) {
        for (CartItem product : receipt.getProduct()) {
            printBuyProductWithLengthCondition(product);
        }
    }

    private void printReceiptInstruction() {
        System.out.printf(RECEIPT_START_MSG);
    }

    private void printBuyProductWithLengthCondition(CartItem product) {
        if (product.getName().length() < 3) {
            System.out.printf("%s\t\t\t\t%d\t\t%,d%n", product.getName(), product.getQuantity(), product.calculatePrice());
            return;
        }
        System.out.printf("%s\t\t\t%d\t\t%,d%n", product.getName(), product.getQuantity(), product.calculatePrice());
    }

    private void printFreeProductWithLengthCondition(Receipt receipt, CartItem cartItem) {
        if (cartItem.getName().length() < 3) {
            System.out.printf("%s\t\t\t\t%d%n", cartItem.getName(),
                    receipt.getPromotionCount(cartItem.getName()));
            return;
        }
        System.out.printf("%s\t\t\t%d%n", cartItem.getName(),
                receipt.getPromotionCount(cartItem.getName()));
    }
}
