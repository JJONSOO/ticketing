package com.pilot.ticketing.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pilot.member.domain.Member;
import com.pilot.ticket.domain.Ticket;
import com.pilot.ticketing.domain.MemberTicket;

public interface MemberTicketRepository extends JpaRepository<MemberTicket, Long> {
	boolean existsByOwnerAndTicket(Member member, Ticket ticket);
}
