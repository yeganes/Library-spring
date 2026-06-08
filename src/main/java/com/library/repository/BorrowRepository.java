package com.library.repository;



import com.library.entity.Book;
import com.library.entity.Borrow;
import com.library.entity.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow , Integer> {


    List<Borrow> findBorrowByBookAndMember(Book book, Member member);


    List<Borrow> findByMember(Member member);


}
