package com.restaurant.a3.RestaurantApi.services;

import com.restaurant.a3.RestaurantApi.exceptions.EntityNotFoundException;
import com.restaurant.a3.RestaurantApi.models.RestaurantModel;
import com.restaurant.a3.RestaurantApi.models.address.viacep.Viacep;
import com.restaurant.a3.RestaurantApi.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Transactional
    public RestaurantModel saveRestaurant(RestaurantModel restaurant) {
        return  restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public RestaurantModel findById(Long id) {
        return  restaurantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Restaurante de ID: '%s', não encontrado!", id))
        );
    }

    @Transactional(readOnly = true)
    public List<RestaurantModel> getAll() {
        return restaurantRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RestaurantModel findByIdWithComments(Long id) {
        return restaurantRepository.findByIdWithComments(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com id: " + id)
                );
    }

    @Transactional(readOnly = true)
    public RestaurantModel findByIdWithAssessments(Long id) {
        return restaurantRepository.findByIdWithAssessments(id).orElseThrow(
                () -> new EntityNotFoundException("Restaurante não encontrado com id: " + id)
        );
    }

    public RestaurantModel addressRegister(RestaurantModel r) {
        r.setState(Viacep.getAddress(r.getCep()).getUf());
        r.setCity(Viacep.getAddress(r.getCep()).getLocalidade());
        r.setStreet(Viacep.getAddress(r.getCep()).getLogradouro());
        r.setDistrict(Viacep.getAddress(r.getCep()).getBairro());

        return  r;
    }

    public void removeRestaurant(Long id) {
        restaurantRepository.delete(findById(id));
    }
}
