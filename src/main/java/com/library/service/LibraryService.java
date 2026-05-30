package com.library.service;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Member;
import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.MemberDAO;


import java.util.List;

public class LibraryService {
    MemberService memberService = new MemberService();
    BookService bookService = new BookService();
    BookDAO bookDAO = new BookDAO();
    BorrowDAO borrowDAO = new BorrowDAO();
    MemberDAO memberDAO = new MemberDAO();


    public void borrow(int memberId, int bookId ) throws LimitBorrowedException, MemberNotFoundException {


        Member member = memberService.readMemberById(memberId);
        Book book = bookService.findById(bookId);
        List<Borrow> borrowList = borrowDAO.selectAll();


        if (book.isAvailable() && member.getBorrowLimit() > 0 && book.getBookStock() > 0) {
            member.setBorrowLimit(member.getBorrowLimit() - 1);
            book.setBookStock(book.getBookStock()-1);
            if (book.getBookStock() < 0){
                book.setAvailable(false);
            }
            borrowDAO.insert(member , book);
            memberDAO.updateLimit(memberId , member.getBorrowLimit());
            bookDAO.updateStock(bookId , book.getBookStock());



        } else if (member.getBorrowLimit() == 0){
            throw new LimitBorrowedException("can't borrow more books");

        }
    }

    public void returnBook (int memberId , int bookId) throws MemberNotFoundException {

        Member member = memberService.readMemberById(memberId);
        Book book = bookService.findById(bookId);
        List<Borrow> borrowList = borrowDAO.selectMemberBook(member , book);



        member.setBorrowLimit(member.getBorrowLimit() + 1 );
        book.setBookStock(book.getBookStock() + 1 );
        if (!book.isAvailable()){
            book.setAvailable(true);
        }

        for (Borrow borrow : borrowList){
            if (borrow.getBook() == null || borrow.getMember() == null ){
                System.out.println("you didn't borrow any books");

            }
        }
        memberDAO.updateLimit(memberId , member.getBorrowLimit());

        bookDAO.updateStock(bookId , book.getBookStock());

        borrowDAO.delete(member , book);
    }

}