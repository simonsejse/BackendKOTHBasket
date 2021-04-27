package com.simonsejse.basketkoth.user;

public enum ApplicationUserPermission {

    USER_ADD("create");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }


    public String getPermission() {
        return permission;
    }
}
