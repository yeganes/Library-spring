package com.library.entity;


import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "borrows")
public class Borrow {

    private Integer borrowId;
    private Book book;
    private Member member;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;


    public Borrow() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    @Column(nullable = false)
    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }



    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId=" + borrowId +
                ", book=" + book +
                ", member=" + member +
                ", borrowDate=" + borrowDate+
                ",returnDate=" + returnDate+
                '}';
    }
}