package store.domain;

import store.controller.ConvenienceInputIterator;

public class Membership {

    public static final int MEMBERSHIP_DISCOUNT_PERCENT = 30;
    public static final int PERCENTAGE_DIVISOR = 100;
    public static final int MEMBERSHIP_MAX_MONEY = 8000;

    private final ConvenienceInputIterator convenienceInputIterator;

    public Membership(ConvenienceInputIterator convenienceInputIterator) {
        this.convenienceInputIterator = convenienceInputIterator;
    }

    public int membershipProcess(int totalPrice, int promotionPrice) {
        String membershipAnswer = convenienceInputIterator.readMembershipApply();
        return calculateMembershipDiscount(membershipAnswer, totalPrice, promotionPrice);
    }

    public int calculateMembershipDiscount(String membership, int totalPrice, int promotionPrice) {
        int membershipDiscount = 0;
        if (membership.equals("Y")) {
            membershipDiscount = applyMembershipDiscount(totalPrice - promotionPrice);
        }
        return membershipDiscount;
    }

    private int applyMembershipDiscount(int totalPrice) {
        int membershipDiscount = totalPrice * MEMBERSHIP_DISCOUNT_PERCENT / PERCENTAGE_DIVISOR;
        return Math.min(membershipDiscount, MEMBERSHIP_MAX_MONEY);
    }

}
