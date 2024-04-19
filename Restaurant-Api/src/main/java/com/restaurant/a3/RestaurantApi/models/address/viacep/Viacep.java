package com.restaurant.a3.RestaurantApi.models.address.viacep;

import com.restaurant.a3.RestaurantApi.models.address.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Viacep {

    public static Address getAddress(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Address> address = restTemplate
                .getForEntity(String.format("https://viacep.com.br/ws/%s/json/", cep), Address.class);

        return  address.getBody();
    }
}
