package com.ansh.rating.repository;

import com.ansh.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepo extends MongoRepository<Rating, String> {

    //custom finder methods creation (No default implementation)
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
