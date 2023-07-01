package com.ansh.user.service.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    private String userId;
    @Column(name = "NAME",length = 20)
    private String name;
    @Column(name = "EMAIL",length = 50)
    private String email;
    @Column(name = "ABOUT",length = 10000)
    private String about;

    @Transient
    private List<Ratings> ratings= new ArrayList<>();

}
