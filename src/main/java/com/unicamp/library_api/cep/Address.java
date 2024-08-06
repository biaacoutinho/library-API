package com.unicamp.library_api.cep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Address {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
}
