package com.unicamp.library_api.reader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.library_api.reader.DTO.ReaderData;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    @Autowired
    private ReaderRepository repository;

    @GetMapping
    public List<ReaderData> getAll()
    {
        List<Reader> readers = this.repository.findAll();

        List<ReaderData> list = readers.stream().map(reader -> new ReaderData(reader.getCpf(), reader.getName(), reader.getEmail(), reader.getPhone(), reader.getCep())).toList();
        
        return list;
    }
}
