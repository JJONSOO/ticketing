package com.pilot.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pilot.member.domain.Member;
import com.pilot.member.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberFinder {
	public final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member findById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow();

	}

}
