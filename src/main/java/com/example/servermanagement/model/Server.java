package com.example.servermanagement.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "ip address should not be empty")
    private String ip_address;
    private String serverName;
    private String memory;
    private String type;
    private String imgUrl;
    private Status status;

    public String getName() {
        return this.serverName;
    }
}
