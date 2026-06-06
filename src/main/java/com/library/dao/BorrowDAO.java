package com.library.dao;



import com.library.entity.Book;
import com.library.entity.Borrow;
import com.library.entity.Member;
import com.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BorrowDAO {
    SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();


    public void insert(Member member , Book book ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setMember(member);
        borrow.setBorrowDate(LocalDateTime.now());
        session.persist(borrow);


        tx.commit();

    }


    public List<Borrow> selectAll(){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Borrow> borrowQuery = session.createQuery("FROM Borrow ", Borrow.class);
        List<Borrow> borrowList = borrowQuery.list();


        tx.commit();

        return borrowList;
    }


    public List<Borrow> selectMemberBook(Member member , Book book){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        Query<Borrow> borrowQuery =
                session.createQuery(
                        "FROM Borrow b WHERE b.member = :member AND b.book = :book",
                        Borrow.class
                );        borrowQuery.setParameter("member", member);
        borrowQuery.setParameter("book", book);

        List<Borrow> list = borrowQuery.list();
        tx.commit();
        return list;
    }
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
