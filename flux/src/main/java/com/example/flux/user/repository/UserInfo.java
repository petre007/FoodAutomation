package com.example.flux.user.repository;

import com.example.flux.user.model.Roles;

public interface UserInfo {

    Integer getId();

    String getUsername();

    Roles getRole();

}
