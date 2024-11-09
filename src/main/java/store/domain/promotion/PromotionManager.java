package store.domain.promotion;

import java.util.Collections;
import java.util.List;

public class PromotionManager {

    private final List<Promotion> promotions;

    public PromotionManager(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
