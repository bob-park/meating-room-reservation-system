package com.m2rs.userservice.configure.user.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConstructorBinding
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    @NestedConfigurationProperty
    private final CompanyProperty company;

    public UserProperties(CompanyProperty company) {
        this.company = company;
    }

    public CompanyProperty getCompany() {
        return company;
    }
}
