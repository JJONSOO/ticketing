package com.pilot.ticket.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pilot.ticket.domain.Ticket;
import com.pilot.ticket.infrastructure.TicketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TicketFinder {
	private final TicketRepository ticketRepository;

	@Transactional(readOnly = true)
	public Ticket findById(Long ticketId) {
		return ticketRepository.findByIdForUpdate(ticketId).orElseThrow();
	}
}
