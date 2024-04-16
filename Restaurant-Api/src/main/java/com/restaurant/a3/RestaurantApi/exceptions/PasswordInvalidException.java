package com.restaurant.a3.RestaurantApi.exceptions;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String msg) {
        super(msg);
    }
}
