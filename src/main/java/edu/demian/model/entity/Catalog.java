package edu.demian.model.entity;

import java.util.List;

public class Catalog implements BaseEntity {

    private Long id;

    private Long bookId;

    private Integer quantity;

    List<Book> bookList;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Book> getBookList() {   
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
