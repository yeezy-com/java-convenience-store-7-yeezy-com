package store.domain.seller;

import store.domain.promotion.PromotionManager;

public class Pos {

    private final PromotionManager promotionManager;

    public Pos(PromotionManager promotionManager) {
        this.promotionManager = promotionManager;
    }

    public boolean isY(String s) {
        return s.equals("Y");
    }
}
