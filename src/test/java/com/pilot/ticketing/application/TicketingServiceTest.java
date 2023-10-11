package com.pilot.ticketing.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.pilot.member.domain.Member;
import com.pilot.member.infrastructure.MemberRepository;
import com.pilot.ticket.domain.Ticket;
import com.pilot.ticket.infrastructure.TicketRepository;
import com.pilot.ticketing.application.dto.request.TicketingRequest;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketingServiceTest {

	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TicketingService ticketingService;
	@Autowired
	AsynchronousTicketService asynchronousTicketService;

	@Test
	void ticketing() throws InterruptedException {
		int memberCount = 30;
		int totalAmount = 10;
		String ticketName = "ticket";
		Ticket ticket = ticketRepository.save(new Ticket(totalAmount, ticketName));

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(memberCount);

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();

		List<Member> memberList = saveMemberWithMemberCount(memberCount);
		for (int tryCount = 0; tryCount < memberCount; tryCount++) {
			int finalTryCount = tryCount;
			executorService.submit(() -> {
				try {
					Long memberId = memberList.get(finalTryCount).getId();
					TicketingRequest ticketingRequest = new TicketingRequest(ticket.getId());
					ticketingService.ticketing(memberId, ticketingRequest);
					successCount.incrementAndGet();
				} catch (Exception e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		assertThat(successCount.get()).isEqualTo(10);
		assertThat(failCount.get()).isEqualTo(20);

	}

	@Test
	void ticketingWithConcurrentWithSynchronize() throws InterruptedException {
		int memberCount = 30;
		int totalAmount = 10;
		String ticketName = "ticket";
		Ticket ticket = ticketRepository.save(new Ticket(totalAmount, ticketName));

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(memberCount);

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();

		List<Member> memberList = saveMemberWithMemberCount(memberCount);
		for (int tryCount = 0; tryCount < memberCount; tryCount++) {
			int finalTryCount = tryCount;
			executorService.submit(() -> {
				try {
					Long memberId = memberList.get(finalTryCount).getId();
					TicketingRequest ticketingRequest = new TicketingRequest(ticket.getId());
					asynchronousTicketService.asynchronousTicketing(memberId, ticketingRequest);
					successCount.incrementAndGet();
				} catch (Exception e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		assertAll(
			() -> assertThat(successCount.get()).isEqualTo(10),
			() -> assertThat(failCount.get()).isEqualTo(20));

	}

	@Test
	void ticketingWithConcurrent() throws InterruptedException {
		int memberCount = 30;
		int totalAmount = 10;
		String ticketName = "ticket";
		Ticket ticket = ticketRepository.save(new Ticket(totalAmount, ticketName));

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(memberCount);

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();

		List<Member> memberList = saveMemberWithMemberCount(memberCount);
		for (int tryCount = 0; tryCount < memberCount; tryCount++) {
			int finalTryCount = tryCount;
			executorService.submit(() -> {
				try {
					Long memberId = memberList.get(finalTryCount).getId();
					TicketingRequest ticketingRequest = new TicketingRequest(ticket.getId());
					ticketingService.ticketing(memberId, ticketingRequest);
					successCount.incrementAndGet();
				} catch (Exception e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		assertAll(
			() -> assertThat(successCount.get()).isEqualTo(10),
			() -> assertThat(failCount.get()).isEqualTo(20));

	}

	@Test
	void ticketingSequential() {
		int memberCount = 30;
		int totalAmount = 10;
		String ticketName = "ticket";
		Ticket ticket = ticketRepository.save(new Ticket(totalAmount, ticketName));

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();

		List<Member> memberList = saveMemberWithMemberCount(memberCount);
		for (int tryCount = 0; tryCount < memberCount; tryCount++) {
			try {
				Long memberId = memberList.get(tryCount).getId();
				TicketingRequest ticketingRequest = new TicketingRequest(ticket.getId());
				ticketingService.ticketing(memberId, ticketingRequest);
				successCount.incrementAndGet();
			} catch (Exception e) {
				failCount.incrementAndGet();
			}
		}

		assertAll(
			() -> assertThat(successCount.get()).isEqualTo(10),
			() -> assertThat(failCount.get()).isEqualTo(20));

	}

	private List<Member> saveMemberWithMemberCount(int memberCount) {
		List<Member> memberList = new ArrayList<>();
		for (int count = 0; count < memberCount; count++) {
			memberList.add(memberRepository.save(new Member(String.valueOf(count))));
		}
		return memberList;
	}
}