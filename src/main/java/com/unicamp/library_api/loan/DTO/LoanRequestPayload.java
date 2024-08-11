package com.unicamp.library_api.loan.DTO;

public record LoanRequestPayload(
        String bookIsbn,
        String readerCpf
) {}
