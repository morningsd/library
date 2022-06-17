package edu.demian.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reserve implements BaseEntity {

    private Long id;
    private Long accountId;
    private Long bookId;
    private LocalDate createdDate;
    private LocalDate finalDate;
    private LocalDate submittedDate;
    private Boolean isActive;
    private BigDecimal fine;

    private Book book;
    private Account account;

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
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
