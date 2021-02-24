package com.example.restspringboot.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.restspringboot.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
  CUSTOMER(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, RESTO_READ, MENU_READ, OWNER_READ)),
  OWNER(
      Sets.newHashSet(
          CUSTOMER_READ, RESTO_READ, RESTO_WRITE, MENU_READ, MENU_WRITE, OWNER_READ, OWNER_WRITE)),
  STAFF(Sets.newHashSet(CUSTOMER_READ, RESTO_READ, MENU_READ, OWNER_READ));

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions =
        getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());

    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return permissions;
  }
}
