package com.unicamp.library_api.loan.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record LoanData(
        UUID id,
        String bookIsbn,
        String readerCpf,
        LocalDate loanDate,
        LocalDate returnDate
) {}
