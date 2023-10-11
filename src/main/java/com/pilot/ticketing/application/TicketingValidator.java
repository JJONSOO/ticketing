package com.pilot.ticketing.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pilot.member.domain.Member;
import com.pilot.ticket.domain.Ticket;
import com.pilot.ticketing.infrastructure.MemberTicketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TicketingValidator {
	private final MemberTicketRepository memberTicketRepository;

	@Transactional(readOnly = true)
	public void isAlreadyReserved(Member member, Ticket ticket) {
		if (memberTicketRepository.existsByOwnerAndTicket(member, ticket)) {
			throw new RuntimeException();
		}
	}
}
