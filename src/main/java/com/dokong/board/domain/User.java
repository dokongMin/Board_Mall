package com.dokong.board.domain;

import com.dokong.board.domain.baseentity.BaseTimeEntity;

import javax.persistence.*;

@Entity(name = "user")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;

    @Embedded
    private Address address;

}
