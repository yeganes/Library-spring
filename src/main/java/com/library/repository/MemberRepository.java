package com.library.repository;



import com.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member , Integer> {


    List<Member> findByNameContaining(String name);

     Member findMemberByMemberId(int id);

    List<Member> findByActiveTrue();

    List<Member> findByActiveFalse();



}


