package com.restaurant.a3.RestaurantApi.Jwt;

import com.restaurant.a3.RestaurantApi.models.UserModel;
import com.restaurant.a3.RestaurantApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getToken(String username) {
        UserModel.Role role = userService.findRoleByUsername(username);
        return  JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}