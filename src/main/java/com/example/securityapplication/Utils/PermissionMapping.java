package com.example.securityapplication.Utils;

import com.example.securityapplication.Entity.Enums.Permission;
import com.example.securityapplication.Entity.Enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.securityapplication.Entity.Enums.Permission.*;
import static com.example.securityapplication.Entity.Enums.Role.*;

public class PermissionMapping {
    private static final Map<Role, Set<Permission>> map = Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE),
            ADMIN,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE,USER_DELETE,POST_DELETE,USER_CREATE)
            );

    public static Set<SimpleGrantedAuthority> getRoles(Role role){
        return map.get(role).stream()
                .map(permission->new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
