package com.pilot.ticketing.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pilot.member.application.MemberFinder;
import com.pilot.member.domain.Member;
import com.pilot.ticket.application.TicketFinder;
import com.pilot.ticket.domain.Ticket;
import com.pilot.ticketing.application.dto.request.TicketingRequest;
import com.pilot.ticketing.application.dto.response.TicketingResponse;
import com.pilot.ticketing.domain.MemberTicket;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TicketingService {
	private final MemberTicketAppender memberTicketAppender;
	private final MemberFinder memberFinder;
	private final TicketFinder ticketFinder;
	private final TicketingValidator ticketingValidator;

	public TicketingResponse ticketing(Long memberId, TicketingRequest ticketingRequest) {
		Ticket ticket = ticketFinder.findById(ticketingRequest.ticketId());
		Member member = memberFinder.findById(memberId);
		ticketingValidator.isAlreadyReserved(member, ticket);

		int reserveSequence = ticket.bookAndRetrieveCount();
		MemberTicket memberTicket = ticket.createMemberTicket(member, reserveSequence, LocalDateTime.now());
		memberTicketAppender.append(memberTicket);
		return TicketingResponse.from(memberTicket, reserveSequence);
	}

}
