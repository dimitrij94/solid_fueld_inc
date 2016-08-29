package com.example.constants;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public enum Authorities {
    ADMIN, SUPER_ADMIN;

    public static final String SUPER_ADMIN_VAL = "SUPER_ADMIN";

    public String getRole(){
        return "ROLE_"+this.name();
    }
    public String getShort(){
        return this.name();
    }

    public static String ROLE_ADMIN_VAL = "ADMIN";
    public static String ROLE_ADMIN = "ROLE_" + ROLE_ADMIN_VAL;

}
