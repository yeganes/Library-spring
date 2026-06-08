package com.library.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    private Integer id;
    private String title;
    private String author;
    private int totalPages;
    private boolean available;
    private int bookStock;
    private List<Borrow>  borrows;
    private Integer version;



    public Book() {
    }

    public Book(String title,
                String author,
                int totalPages,
                boolean available,
                int bookStock) {

        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.available = available;
        this.bookStock = bookStock;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "book_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "author_name")
    public String getAuthor() {
        return author;
    }

    @OneToMany(mappedBy = "book",
            fetch = FetchType.LAZY
    )
    public List<Borrow> getBorrows() {
        return borrows;
    }



    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }


    public void setAuthor(String author) {
        this.author = author;
    }
    @Column(name = "total_pages")
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    @Column(name = "book_stock")
    public int getBookStock() {
        return bookStock;
    }

    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", totalPages=" + totalPages +
                ", available=" + available +
                ", bookStock=" + bookStock +
                '}';
    }
}