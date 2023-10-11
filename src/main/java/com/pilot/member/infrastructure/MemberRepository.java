package com.pilot.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pilot.member.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
