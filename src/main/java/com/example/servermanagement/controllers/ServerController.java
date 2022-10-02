package com.example.servermanagement.controllers;

import com.example.servermanagement.model.Server;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
public interface ServerController {
    @PostMapping
    Server create(Server server);

    @GetMapping
    Server ping(String ip_address) throws IOException;

    @GetMapping
    Collection<Server> list(int limit);

    @GetMapping
    Server get(Long id);

    @PutMapping
    Server update(Server server);

    @DeleteMapping
    Boolean delete(Long id);

}
