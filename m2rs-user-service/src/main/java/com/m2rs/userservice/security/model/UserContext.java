package com.m2rs.userservice.security.model;

import com.m2rs.userservice.model.entity.User;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class UserContext extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(), user.getPassword(), authorities);
        this.user = user;
    }
}
