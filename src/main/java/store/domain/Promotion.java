package store.domain;

import java.time.LocalDate;

public class Promotion {

    String name;
    int standardCount;
    int freeCount;
    LocalDate startDate;
    LocalDate endDate;

    public Promotion() {
        this.name = "";
        standardCount = 0;
        freeCount = 0;
        startDate = LocalDate.MIN;
        endDate = LocalDate.MIN;
    }

    public Promotion(String name, int standardCount, int freeCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.standardCount = standardCount;
        this.freeCount = freeCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isAvailable(LocalDate now) {
        if (now.isBefore(startDate) || now.isAfter(endDate)) {
            return false;
        }

        return true;
    }
}
