package com.example.restspringboot.security;

import com.google.common.collect.Sets;

import static com.example.restspringboot.security.ApplicationUserPermission.*;

import java.util.Set;

public enum ApplicationUserRole {
    CUSTOMER(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, RESTO_READ, MENU_READ, OWNER_READ)),
    OWNER(Sets.newHashSet(CUSTOMER_READ, RESTO_READ, RESTO_WRITE, MENU_READ, MENU_WRITE, OWNER_READ, OWNER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

}
