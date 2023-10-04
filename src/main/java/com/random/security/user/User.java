package com.random.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.random.security.account.Account;
import com.random.security.role.Role;
import com.random.security.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Token> tokens;

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Account> accounts;

    private LocalDateTime loginAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @JsonIgnore
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         for( Role role : roles){
             authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
         }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
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
}
