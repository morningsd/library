package edu.demian.model.entity;

public enum BookStatus {
    IN_STOCK, ORDERED, SUBSCRIPTION, READING_ROOM;

    public Integer getId() {
        // id in table starts from 1, and ordinal() return values from 0, so we need to increment the returning value
        return ordinal() + 1;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public static BookStatus getBookStatus(final Integer statusId) {
        // id in db starts from 1, and values() return values from 0, so we need to decrement the status id value
        return BookStatus.values()[statusId - 1];
    }



}
