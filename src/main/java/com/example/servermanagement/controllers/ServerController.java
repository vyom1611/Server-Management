package com.example.servermanagement.controllers;

import com.example.servermanagement.model.ApiResponse;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServiceMapper serviceServer;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getServers() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serviceServer.list(30)))
                        .message("servers retrieve")
                        .statusCode(OK)
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<ApiResponse> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serviceServer.ping(ipAddress);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .statusCode(OK)
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serviceServer.create(server)))
                        .message("Server created")
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serviceServer.get(id)))
                        .message("Server obtained")
                        .statusCode(OK)
                        .build()
        );
    }
}
