package ru.mboychook.webQuestions.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_user")
@DynamicUpdate
@DynamicInsert
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @Size(min=4, message = "More than 3 symbols required")
    @Column(name = "username", unique = true)
    private String username;

    @Size(min=4, message = "More than 3 symbols required")
    @Column(name = "password")
    private String password;

    @Email(message = "Should be correct e-mail")
    @Column(name = "email")
    private String email;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = { CascadeType.PERSIST,
                            CascadeType.MERGE } )
    @JoinTable(name = "t_user_role",
               joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
               inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") } )
    @Valid
    @Size(min = 1, message = "Must have at least one role")
    private List<Role> roles = new ArrayList<Role>(); //new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    public User() {

    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return this.password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        Set<Role> rolesSet = new HashSet<>(this.roles);
                  rolesSet.addAll(roles);
        this.roles = new ArrayList<>(rolesSet);
    }

    // for Set<Role> roles
    /*public List<Role> getRolesList() {
        return roles.stream().toList();
    }

    public void setRolesList(List<Role> roles) {
        this.roles = new HashSet<>(roles);
    }*/

    public String getRolesStr() {
        return this.roles.stream().map(Role::toString).collect(Collectors.joining(";"));
    }

    public void addRole(Role role){
        Set<Role> rolesSet = new HashSet<>(this.roles);
                  rolesSet.add(role);
        this.roles = new ArrayList<>(rolesSet);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
    }

    public void removeRole(int index){
        this.roles.remove(index);
    }

    /*public void removeRole(int index){
        Role roleToRemove = new ArrayList<Role>( this.roles ).get(index);
        this.roles = this.roles.stream()
                         .filter(r -> r.equals(roleToRemove))
                         .collect(Collectors.toSet());
    }*/

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
        //return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof User other)) return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else return id.equals(other.id);
        return true;
        //return super.equals(obj);
    }

    @Override
    public String toString() {
        //return super.toString();
        return "User [id=" + this.id + ", password=" + this.password
                + ", userName=" + this.username + ", email=" + this.email + "]";
    }
}