package com.pilot.ticketing.domain;

import java.time.LocalDateTime;

import com.pilot.member.domain.Member;
import com.pilot.ticket.domain.Ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberTicket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Member owner;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Ticket ticket;

	private LocalDateTime localDateTime;

	@Builder
	public MemberTicket(Member owner, Ticket ticket) {
		this.owner = owner;
		this.ticket = ticket;
	}
}