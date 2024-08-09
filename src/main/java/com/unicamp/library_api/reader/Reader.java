package com.unicamp.library_api.reader;

import java.util.List;

import com.unicamp.library_api.loan.Loan;
import com.unicamp.library_api.reader.DTO.ReaderData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "reader")
public class Reader {
    @Id
    private String cpf;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String cep;

    private String streetName;

    private String neighborhood;

    private String city;

    private String uf;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Reader(ReaderData payload)
    {
        this.cpf = payload.cpf();
        this.name = payload.name();
        this.email = payload.email();
        this.phone = payload.phone();
        this.cep = payload.cep();
    }
}
