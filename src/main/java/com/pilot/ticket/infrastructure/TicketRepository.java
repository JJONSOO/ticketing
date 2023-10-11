package com.pilot.ticket.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pilot.ticket.domain.Ticket;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select t from Ticket t where t.id = :id")
	@QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
	Optional<Ticket> findByIdForUpdate(@Param("id") Long id);
}
