package store.domain;

import java.time.LocalDate;

public class Date {

    private final LocalDate date;

    public Date(LocalDate date) {
        this.date = date;
    }

    public LocalDate today() {
        return date;
    }
}
