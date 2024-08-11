package com.unicamp.library_api.reader;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.cep.Address;
import com.unicamp.library_api.cep.PostmonClient;
import com.unicamp.library_api.reader.DTO.ReaderData;
import com.unicamp.library_api.reader.DTO.ReaderResponse;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    @Autowired
    private ReaderRepository repository;

    @Autowired
    private PostmonClient cepClient;

    @GetMapping
    public List<ReaderData> getAll()
    {
        List<Reader> readers = this.repository.findAll();

        List<ReaderData> list = readers.stream().map(reader -> new ReaderData(reader.getCpf(), reader.getName(), reader.getEmail(), reader.getPhone(), reader.getCep())).toList();
        
        return list;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Reader> getReader(@PathVariable String cpf) {
        Optional<Reader> reader = this.repository.findByCpf(cpf);

        return reader.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ReaderData body)
    {
        Reader reader = new Reader(body);
        String cep = body.cep();

        Address address = cepClient.fetchAddressByCep(cep);
        reader.setCity(address.getCidade());
        reader.setNeighborhood(address.getBairro());
        reader.setUf(address.getEstado());
        reader.setStreetName(address.getLogradouro());

        Reader created = this.repository.save(reader);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created new reader",
                new ReaderResponse(created.getCpf(), reader.getName(), reader.getEmail(), reader.getPhone(), reader.getCep(), reader.getCity(), reader.getStreetName(), reader.getNeighborhood(), reader.getUf())
            ));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Response> delete(@PathVariable("cpf") String cpf) {
        Optional<Reader> reader = this.repository.findByCpf(cpf);

        if (reader.isPresent()) {

            this.repository.delete(reader.get());

            return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response("Successfully deleted reader with specified cpf", null));
        }

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new Response(
                "Could not find reader with specified cpf",
                null
            ));
    }

}
