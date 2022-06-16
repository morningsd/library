package edu.demian.model.entity;

public enum BookStatus {
    IN_STOCK, ORDERED, SUBSCRIPTION, READING_ROOM;

    public static BookStatus getBookStatus(final Book book) {
        final int bookStatusId = book.getStatusId();
        return BookStatus.values()[bookStatusId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
