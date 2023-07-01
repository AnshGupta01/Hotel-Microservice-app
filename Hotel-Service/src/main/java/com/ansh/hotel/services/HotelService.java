package com.ansh.hotel.services;

import com.ansh.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    //get
    List<Hotel> getAll();

    //get all
    Hotel get(String id);
}
