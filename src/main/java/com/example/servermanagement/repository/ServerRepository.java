package com.example.servermanagement.repository;

import com.example.servermanagement.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ip_address);
}
