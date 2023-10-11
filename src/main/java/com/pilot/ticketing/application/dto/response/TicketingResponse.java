package com.pilot.ticketing.application.dto.response;

import java.time.LocalDateTime;

import com.pilot.ticketing.domain.MemberTicket;

public record TicketingResponse(
	String ticketType,
	int number,
	LocalDateTime entryTime
) {
	public static TicketingResponse from(MemberTicket memberTicket, int reserveSequence) {
		 return new TicketingResponse(memberTicket.getTicket().getName(), reserveSequence,
		 	memberTicket.getLocalDateTime());
	}
}
