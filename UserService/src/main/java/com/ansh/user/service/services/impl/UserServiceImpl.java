package com.ansh.user.service.services.impl;

import com.ansh.user.service.entities.Hotel;
import com.ansh.user.service.entities.Ratings;
import com.ansh.user.service.entities.User;
import com.ansh.user.service.exceptions.ResourceNotFoundException;
import com.ansh.user.service.repositories.UserRepo;
import com.ansh.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> allUsers = userRepo.findAll();
//        ArrayList<Ratings> ratingsOfUser = restTemplate.getForObject
//                ("http://localhost:8083/ratings/users/", ArrayList.class);
//
        return allUsers;
    }

    // get user from database with the help of UserRepository
    @Override
    public User getUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User not found with id: " + userId));

        //fetch rating of the above user from Rating Service
        Ratings[] ratingsOfUser = restTemplate.getForObject
                ("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Ratings[].class);
        logger.info("{}",ratingsOfUser);

        List<Ratings> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Ratings> ratingList = ratings.stream().map(rating -> {

            //api call to hotel service to get the hotel
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"
                    +rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code",forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            //return the rating
            return rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }
}
