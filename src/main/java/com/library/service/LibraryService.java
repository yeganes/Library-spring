package com.library.service;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.entity.Book;
import com.library.entity.Borrow;
import com.library.entity.Member;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class LibraryService {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowRepository borrowRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public LibraryService(BookService bookService, MemberService memberService, BorrowRepository borrowRepository , MemberRepository memberRepository , BookRepository bookRepository){
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowRepository = borrowRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }
    @Transactional
    public void borrow(int memberId, int bookId ) throws LimitBorrowedException, MemberNotFoundException {


        Member member = memberService.readMemberById(memberId);
        Book book = bookService.findById(bookId);


        if (book.isAvailable() && member.getBorrowLimit() > 0 && book.getBookStock() > 0 && member.isActive()) {
            if (book.getBookStock() == 0){
                book.setAvailable(false);
            }
            member.setBorrowLimit(member.getBorrowLimit() - 1);

            member.setBorrowedBooksNum(member.getBorrowedBooksNum() + 1 );

            book.setBookStock(book.getBookStock()-1);


            memberRepository.save(member);
            bookRepository.save(book);


            Borrow borrow = new Borrow();
            borrow.setMember(member);
            borrow.setBook(book);
            borrow.setBorrowDate(LocalDateTime.now());
            borrowRepository.save(borrow);

            System.out.println("the book is borrowed");



        } else if (member.getBorrowLimit() == 0){
            throw new LimitBorrowedException("can't borrow more books");

        } else if (book.getBookStock() == 0  || !book.isAvailable()) {
            System.out.println("SORRY, this book is out of stock.");
        }
    }

    @Transactional
    public void returnBook (int memberId , int bookId) throws MemberNotFoundException {

        Member member = memberService.readMemberById(memberId);
        Book book = bookService.findById(bookId);
        List<Borrow> borrowList = borrowRepository.findBorrowByBookAndMember(book , member);



        member.setBorrowLimit(member.getBorrowLimit() + 1);

        book.setBookStock(book.getBookStock() + 1);

        member.setBorrowedBooksNum(member.getBorrowedBooksNum() - 1);

        if (!book.isAvailable()) {
            book.setAvailable(true);
        }
        for(Borrow borrow : borrowList){
            borrowRepository.findById(borrow.getBorrowId());
        }

        memberRepository.save(member);
        bookRepository.save(book);

        Borrow borrow = new Borrow();
        borrow.setMember(member);
        borrow.setBook(book);
        borrow.setReturnDate(LocalDateTime.now());
        borrowRepository.save(borrow);

    }

}