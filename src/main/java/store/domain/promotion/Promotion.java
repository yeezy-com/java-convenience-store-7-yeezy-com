package store.domain.promotion;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int standardCount;
    private final int freeCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int standardCount, int freeCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.standardCount = standardCount;
        this.freeCount = freeCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String type() {
        return name;
    }

    public boolean isWithinPromotionPeriod(LocalDate date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }

    public int getStandardCount() {
        return standardCount;
    }

    public int getFreeCount() {
        return freeCount;
    }
}
