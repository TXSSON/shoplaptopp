package com.vn.shoplaptopp.repository;

import java.util.List;

import com.vn.shoplaptopp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User newUser);

    List<User> findAll();
}