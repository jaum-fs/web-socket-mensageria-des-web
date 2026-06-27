package com.desweb.synchchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private List<String> historico;
    
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Usuario owner;

    private String password;

    @ManyToMany
    @JoinTable(
        name = "room_users",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Usuario> users;
    

    // Construtor personalizado
    public Room(Usuario user) {
        this.owner = user;
        this.historico = new ArrayList<>();
        this.users = new ArrayList<>();
        this.password = null;
    }
}