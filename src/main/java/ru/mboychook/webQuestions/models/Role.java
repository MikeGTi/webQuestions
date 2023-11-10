package ru.mboychook.webQuestions.models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@Table(name = "t_role")
@DynamicUpdate
@DynamicInsert
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name = RoleEnum.GUEST;

    @ManyToMany(mappedBy = "roles")
    private List<User> users =  new ArrayList<>();

    public Role() {

    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = RoleEnum.valueOf(name.toUpperCase());
    }

    public Role(Long id, RoleEnum name) {
        this.id = id;
        this.name = name;
    }

    public Role(Long id, RoleEnum name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = RoleEnum.valueOf(name.toUpperCase());
    }

    /*public void setName(int index) {
        this.name = RoleEnum.convertIntToStringEnum(index);
    }*/

    public List<User> getUsers() { return this.users; }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }

    public void removeUser(int index){
        this.users.remove(index);
    }

    @Override
    public String getAuthority() {
        return getName().toString();
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(id);
    }

    @Override
    public boolean equals(Object obj) {
        Role role = (Role)obj;
        if (role.getId()==null && this.id==null) {
            return false;
        }
        return Objects.equals(role.getId(), this.id);
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}