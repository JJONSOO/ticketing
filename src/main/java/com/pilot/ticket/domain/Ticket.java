package com.pilot.ticket.domain;

import java.time.LocalDateTime;

import com.pilot.member.domain.Member;
import com.pilot.ticketing.domain.MemberTicket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int totalAmount;

	private int reservedAmount = 0;

	private String name;

	/***/

	public void increaseReservedAmount() {
		if (reservedAmount >= totalAmount) {
			throw new IllegalArgumentException("Sold out.");
		}
		reservedAmount++;
	}

	public MemberTicket createMemberTicket(Member member, int reserveSequence, LocalDateTime localDateTime) {
		return MemberTicket.builder().owner(member).ticket(this).build();
	}

	public int bookAndRetrieveCount() {
		this.increaseReservedAmount();
		return reservedAmount;
	}

	@Builder
	public Ticket(int totalAmount, String name) {
		this.totalAmount = totalAmount;
		this.name = name;
	}
}
