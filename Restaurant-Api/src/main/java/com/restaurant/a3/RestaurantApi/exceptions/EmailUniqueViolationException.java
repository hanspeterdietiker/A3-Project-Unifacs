package com.restaurant.a3.RestaurantApi.exceptions;

public class EmailUniqueViolationException extends RuntimeException{

    public EmailUniqueViolationException(String msg) {
        super(msg);
    }
}
