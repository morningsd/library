package edu.demian.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reserve implements BaseEntity {

    private long id;
    private long accountId;
    private long bookId;
    private LocalDate createdDate;
    private LocalDate finalDate;
    private LocalDate submittedDate;
    private boolean isActive;
    private BigDecimal fine;

    private Book book;
    private Account account;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", bookId=" + bookId +
                ", createdDate=" + createdDate +
                ", finalDate=" + finalDate +
                ", isActive=" + isActive +
                ", fine=" + fine +
                '}';
    }
}
