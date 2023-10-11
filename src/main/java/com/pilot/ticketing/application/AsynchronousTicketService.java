package com.pilot.ticketing.application;

import org.springframework.stereotype.Service;

import com.pilot.ticketing.application.dto.request.TicketingRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AsynchronousTicketService {
	private final TicketingService ticketingService;

	public synchronized void asynchronousTicketing(Long memberId, TicketingRequest ticketingRequest) {
		ticketingService.ticketing(memberId, ticketingRequest);
	}
}
