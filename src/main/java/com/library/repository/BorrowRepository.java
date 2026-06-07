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


    public List<Borrow> findByMember(Member member ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        try {
            Query<Borrow> borrowQuery = session.createQuery("FROM Borrow b\n" +
                    "JOIN FETCH b.book\n" +
                    "WHERE b.member = :member", Borrow.class);

            borrowQuery.setParameter("member", member);


            List<Borrow> list = borrowQuery.list();
            tx.commit();
            return list;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    public void returnBook(Integer borrowId){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Borrow borrow =  session.get(Borrow.class, borrowId);
        borrow.setReturnDate(LocalDateTime.now());
        tx.commit();


    }


}
