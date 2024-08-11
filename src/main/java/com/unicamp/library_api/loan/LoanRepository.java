package com.unicamp.library_api.loan;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, UUID> {}
