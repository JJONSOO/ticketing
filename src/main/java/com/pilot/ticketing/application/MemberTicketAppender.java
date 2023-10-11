package com.pilot.ticketing.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pilot.ticketing.domain.MemberTicket;
import com.pilot.ticketing.infrastructure.MemberTicketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberTicketAppender {
	private final MemberTicketRepository memberTicketRepository;

	@Transactional(readOnly = true)
	public MemberTicket append(MemberTicket memberTicket) {
		return memberTicketRepository.save(memberTicket);
	}

}
