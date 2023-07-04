package com.ansh.user.service.controllers;

import com.ansh.user.service.entities.User;
import com.ansh.user.service.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping //will directly create user on calling POST Method
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryCount=1;
    //single user get
    @GetMapping("/{userId}") // will get user by userId
//    @CircuitBreaker(name = "RATING_HOTEL_BREAKER", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "RATING_HOTEL_SERVICE", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "USER_RATE_LIMITER", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable(value = "userId") String userId){
        logger.info("retry Count: {}", retryCount);
        retryCount++;
        logger.info("Get Single User Handler: USer Controller");
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallback method for circuit breaker
    //return type of fallback method is always from main method/api return method (Parameters also should match)
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        logger.info("Fallback is executed because service is down", ex.getMessage());

        User dummy = User.builder()
                .email("Dummy@gmail.com")
                .name("Dummy")
                .about("Dummy user created because some service is down")
                .userId("1234")
                .build();
        return new ResponseEntity<>(dummy,HttpStatus.OK);
    }

    //multiple user get
    @GetMapping //will get allUsers by GET Method
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }


}
