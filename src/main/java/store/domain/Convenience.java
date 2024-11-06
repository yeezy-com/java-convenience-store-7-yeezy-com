package store.domain;

public class Convenience {

    private final Inventory inventory;

    public Convenience(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
