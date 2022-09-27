package com.example.servermanagement.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;
    @Column(unique = true)
    @NotEmpty(message = "ip address should not be empty")
    private String ip_address;
    private String serverName;
    private String memory;
    private String type;
    private String imgUrl;
    private Status status;
}
