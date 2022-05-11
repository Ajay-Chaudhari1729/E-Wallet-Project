package com.example.majorproject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
    // if we use this then we don't need to query
    User findByUserId(String userId);
}
