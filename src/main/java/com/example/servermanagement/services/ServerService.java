package com.example.servermanagement.services;


import com.example.servermanagement.controllers.ServiceMapper;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.model.Status;
import com.example.servermanagement.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;


@RequiredArgsConstructor
@Service
@Transactional
public class ServerService implements ServiceMapper {

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
        System.out.println("Fetching all the servers...");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }


    @Override
    public Server get(Long id) {
        System.out.printf("Fetching server with id: %s", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        System.out.printf("Updating server: %s", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        System.out.printf("Deleting server with id: %s", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImgUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
