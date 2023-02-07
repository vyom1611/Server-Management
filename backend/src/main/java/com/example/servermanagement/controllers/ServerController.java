package com.example.servermanagement.controllers;

import com.example.servermanagement.model.ApiResponse;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.model.Status;
import com.example.servermanagement.services.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/servers")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serviceServer;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serviceServer.list(30)))
                        .message("Servers retrieved")
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", serviceServer.delete(id)))
                        .message("Server deleted")
                        .statusCode(OK)
                        .build()
        );
    }

    @GetMapping(path="/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/images/" + fileName));
    }
}
