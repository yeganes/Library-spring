package com.library.model;

import jakarta.persistence.*;

@Entity
public class Borrow {
    @Id
    @GeneratedValue
    private int borrowId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getId() {
        return borrowId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public Borrow(){

    }
}


