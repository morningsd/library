package edu.demian.model.entity;

import java.time.LocalDate;

public class Reserve implements BaseEntity {

    private Long id;
    private Long accountId;
    private Long bookId;
    private LocalDate createdDate;
    private Boolean isActive;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", bookId=" + bookId +
                ", createdDate=" + createdDate +
                ", isActive=" + isActive +
                '}';
    }
}
