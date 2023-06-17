package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByEmail(String email);
    public User findUserByUserName(String name);
}
