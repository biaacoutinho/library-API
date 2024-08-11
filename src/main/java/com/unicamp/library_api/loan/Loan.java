package com.unicamp.library_api.loan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.cglib.core.Local;

import com.unicamp.library_api.author.Author;
import com.unicamp.library_api.book.Book;
import com.unicamp.library_api.loan.DTO.LoanRequestPayload;
import com.unicamp.library_api.reader.Reader;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_cpf", nullable = false)
    private Reader reader;

    public Loan(Book book, Reader reader)
    {
        this.loanDate = LocalDate.now();
        this.returnDate = this.loanDate.plusDays(15);
        this.book = book;
        this.reader = reader;
    }
}
