package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
