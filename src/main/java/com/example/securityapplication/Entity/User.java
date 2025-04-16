package com.example.securityapplication.Entity;

import com.example.securityapplication.Entity.Enums.Role;
import com.example.securityapplication.Utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(roles1 -> {
            Set<SimpleGrantedAuthority> permission = PermissionMapping.getRoles(roles1);
            authorities.addAll(permission); // adding permission(like user_view,post_create etc.) related to the user role(user,admin and creator)
            authorities.add(new SimpleGrantedAuthority("ROLE_"+roles1.name())); // also add role of the user (like user,admin and creator)
        });

        return authorities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
