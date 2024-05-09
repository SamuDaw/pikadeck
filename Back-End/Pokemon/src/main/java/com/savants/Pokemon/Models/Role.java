package com.savants.Pokemon.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role", schema = "pokemon", catalog = "")
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private Integer idRole;
    @Column(name = "role_name", nullable = false)
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role(String role)
    {
        setRole(role);
    }
    public Role(){}
}
