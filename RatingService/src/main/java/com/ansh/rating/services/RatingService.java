package com.ansh.rating.services;

import com.ansh.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    //create
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getRatings();

    //get ratings by user id
    List<Rating> getRatingByUserId(String userId);

    //get ratings by hotel id
    List<Rating> getRatingByHotelId(String hotelId);
}
