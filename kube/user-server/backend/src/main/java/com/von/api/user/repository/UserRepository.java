package com.von.api.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.von.api.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // JPQL Default
    @Modifying
    @Query("update users set token = :token where id = :id")
    public void modifyTokenById(@Param("id") Long id,@Param("token") String token);

    @Query("select count(id) as count from users where username = :username")
    Integer existsUsername(@Param("username") String username);

}
