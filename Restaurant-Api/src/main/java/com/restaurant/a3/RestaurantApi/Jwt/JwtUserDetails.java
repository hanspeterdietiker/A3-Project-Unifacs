package com.restaurant.a3.RestaurantApi.Jwt;

import com.restaurant.a3.RestaurantApi.models.UserModel;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {

    UserModel userEntity;
    public JwtUserDetails(UserModel userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), AuthorityUtils.createAuthorityList(userEntity.getRole().name()));
        this.userEntity = userEntity;
    }

    public Long getId() {
        return userEntity.getId();
    }

    public String getRole() {
        return  userEntity.getRole().name();
    }
}
