package com.unicamp.library_api.loan.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record LoanResponse(
        UUID id,
        String readerName,
        String bookTitle,
        LocalDate loanDate,
        LocalDate returnDate
) {}