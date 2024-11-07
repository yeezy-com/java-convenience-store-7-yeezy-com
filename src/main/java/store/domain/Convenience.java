package store.domain;

public class Convenience {

    private final Inventory inventory;

    public Convenience(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void sellProduct(Receipt buyingProducts) {
        inventory.popStuffCount(buyingProducts);
    }

    public void membershipDiscount(String answer, Receipt receipt) {
        if (answer.equals("Y")) {
            int totalPrice = receipt.totalPrice() - receipt.totalPrice() * 30 / 100;
            receipt.setDiscountPrice(totalPrice);
            return;
        }

        receipt.setDiscountPrice(receipt.totalPrice());
    }
}
