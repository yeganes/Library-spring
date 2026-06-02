package com.library.entity;




import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    public enum Gender {
        MALE,
        FEMALE
    }

    private Integer memberId;
    private String name;
    private int age;
    private String phoneNumber;
    private Integer borrowLimit;
    private Integer borrowedBooksNum;
    private Gender gender;
    private LocalDateTime deletedAt;
    private List<Borrow> borrows;
    private boolean active = true;


    @Column(name = "active" , nullable = false )
    @ColumnDefault("1")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Member() {
    }

    public Member(String name, int age, String phoneNumber, Gender gender, Integer borrowLimit , Integer borrowedBooksNum ,  boolean active) {

        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.borrowLimit = borrowLimit;
        this.borrowedBooksNum = borrowedBooksNum;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @NotNull
    @Column(name = "member_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "borrow_limit_number")
    public Integer getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(Integer borrowLimit) {
        this.borrowLimit = borrowLimit;
    }

    public Integer getBorrowedBooksNum() {
        return borrowedBooksNum;
    }

    public void setBorrowedBooksNum(Integer borrowedBooksNum) {
        this.borrowedBooksNum = borrowedBooksNum;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    @OneToMany(mappedBy = "member",
            fetch = FetchType.LAZY
    )
    public List<Borrow> getBorrows() {
        return borrows;
    }

    @Column
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", borrowLimit=" + borrowLimit +
                ", borrowedBooksNum=" + borrowedBooksNum +
                ", gender=" + gender +
                "'deletedAt=" + deletedAt +
                '}';
    }
}