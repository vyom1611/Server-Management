package com.example.servermanagement.controllers;

import com.example.servermanagement.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface IServerService {
    Server create(Server server);

    Server ping(String ip_address) throws IOException;

    Collection<Server> list(int limit);

    Server get(Long id);

    Server update(Server server);

    Boolean delete(Long id);

}
