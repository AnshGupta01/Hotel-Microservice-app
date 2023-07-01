package com.ansh.user.service.repositories;

import com.ansh.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

    //custom methods

}
