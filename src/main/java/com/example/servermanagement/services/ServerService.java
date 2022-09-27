package com.example.servermanagement.services;


import com.example.servermanagement.controllers.ServerController;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.model.Status;
import com.example.servermanagement.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
public class ServerService implements ServerController {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        System.out.printf("Saving new server: %s", server.getName());
        server.setImgUrl(setServerImgUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ip_address) throws IOException {
        System.out.printf("Pinging server IP: %s", ip_address);
        Server server = serverRepository.findByIp_address(ip_address);
        InetAddress address = InetAddress.getByName(ip_address);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        return null;
    }

    @Override
    public Server get(Long id) {
        return null;
    }

    @Override
    public Server update(Server server) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private String setServerImgUrl() {
        return "";
    }
}
