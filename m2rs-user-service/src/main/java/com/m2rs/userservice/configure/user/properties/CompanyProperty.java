package com.m2rs.userservice.configure.user.properties;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public class CompanyProperty {

    private final String logoDir;

    public CompanyProperty(String logoDir) {
        this.logoDir = logoDir;
    }

    public String getLogoDir() {
        return logoDir;
    }
}
