package com.example.cometcanvasbackend.Repositories;

import com.example.cometcanvasbackend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
