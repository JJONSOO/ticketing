package com.pilot.ticketing.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pilot.auth.annotation.Member;
import com.pilot.ticketing.application.TicketingService;
import com.pilot.ticketing.application.dto.request.TicketingRequest;
import com.pilot.ticketing.application.dto.response.TicketingResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TicketingController {
	private final TicketingService ticketingService;

	@PostMapping("/ticketing")
	public ResponseEntity<TicketingResponse> ticketing(@Member Long memberId, TicketingRequest ticketingRequest) {
		TicketingResponse ticketingResponse = ticketingService.ticketing(memberId, ticketingRequest);
		return ResponseEntity.ok().body(ticketingResponse);

	}
}
