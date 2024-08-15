package com.unicamp.library_api.loan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.book.Book;
import com.unicamp.library_api.book.BookRepository;
import com.unicamp.library_api.loan.DTO.LoanData;
import com.unicamp.library_api.loan.DTO.LoanRequestPayload;
import com.unicamp.library_api.loan.DTO.LoanResponse;
import com.unicamp.library_api.reader.Reader;
import com.unicamp.library_api.reader.ReaderRepository;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping
    public List<LoanData> getAll()
    {
        List<Loan> loans = this.repository.findAll();

        List<LoanData> list = loans.stream().map(loan -> new LoanData(loan.getId(), loan.getBook().getIsbn(), loan.getReader().getCpf(), loan.getLoanDate(), loan.getReturnDate())).toList();
        
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable UUID id) {
        Optional<Loan> loan = this.repository.findById(id);

        return loan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody LoanRequestPayload payload) {   

        Optional<Book> optionalBook = bookRepository.findByIsbn(payload.bookIsbn());
        Optional<Reader> optionalReader = readerRepository.findByCpf(payload.readerCpf());

        if (!optionalBook.isPresent())
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Could not find book with specified ISBN", null));

        if (!optionalReader.isPresent())
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Could not find reader with specified CPF", null));

        Book book = optionalBook.get();
        
        book.setBorrowed(true);
        bookRepository.save(book);

        Reader reader = optionalReader.get();

        Loan newLoan = new Loan(book, reader);

        Loan created = this.repository.save(newLoan);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created loan",
                new LoanResponse(created.getId(), reader.getName(), book.getTitle(), created.getLoanDate(), created.getReturnDate())
            ));
    }
}
