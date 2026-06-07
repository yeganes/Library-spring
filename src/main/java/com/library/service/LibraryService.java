package com.library.service;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.entity.Book;
import com.library.entity.Borrow;
import com.library.entity.Member;
import com.library.repository.BookDAO;
import com.library.repository.BorrowDAO;
import com.library.repository.MemberDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class LibraryService {

    private final BookService bookService;
    private final MemberService memberService;
    private final BookDAO bookDAO;
    private final BorrowDAO borrowDAO;
    public LibraryService(BookService bookService, MemberService memberService, BookDAO bookDAO, BorrowDAO borrowDAO){
        this.bookService = bookService;
        this.memberService = memberService;
        this.bookDAO = bookDAO;
        this.borrowDAO = borrowDAO;
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


            borrowDAO.insert(member , book);
            bookDAO.updateStock(bookId , book.getBookStock());
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
        List<Borrow> borrowList = borrowDAO.selectMemberBook(member , book);



        member.setBorrowLimit(member.getBorrowLimit() + 1);

        book.setBookStock(book.getBookStock() + 1);

        member.setBorrowedBooksNum(member.getBorrowedBooksNum() - 1);

        if (!book.isAvailable()) {
            book.setAvailable(true);
        }

        bookDAO.updateStock(bookId , book.getBookStock());
        for(Borrow borrow : borrowList){
            borrowDAO.returnBook(borrow.getBorrowId());
        }
    }

}