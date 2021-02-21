package com.example.restspringboot.security;

public enum ApplicationUserPermission {
    OWNER_READ("owner:read"), OWNER_WRITE("owner:write"), CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"), RESTO_READ("resto:read"), RESTO_WRITE("resto:write"), MENU_READ("menu:read"),
    MENU_WRITE("menu:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
