package com.library.model;

import jakarta.persistence.*;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_title")
    private String title;
    @Column(name = "author_name")
    private  String author;

    private  Integer totalPages;

    private boolean available;

    private int bookStock;

    public Integer getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getBookStock() {
        return bookStock;
    }

    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }

    public Book(){

    }
    public Book( String title, String author, int totalPages, boolean available , int bookStock) {

        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.available = available;
        this.bookStock = bookStock;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", page=" + totalPages +
                ", available=" + available +
                ", bookStock=" + bookStock+
                '}';
    }
}