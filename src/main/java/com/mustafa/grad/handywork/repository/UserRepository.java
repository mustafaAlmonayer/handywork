package com.mustafa.grad.handywork.repository;

import com.mustafa.grad.handywork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

    @Modifying
    @Query("update User u set u.firstName = :firstName where u.id = :id")
    @Transactional
    void updateUserFirstName(@Param(value = "id") long id, @Param(value = "firstName") String firstName);

    @Modifying
    @Query("update User u set u.lastName = :lastName where u.id = :id")
    @Transactional
    void updateUserLastName(@Param(value = "id") long id, @Param(value = "lastName") String lastName);

    @Modifying
    @Query("update User u set u.phoneNumber = :phoneNumber where u.id = :id")
    @Transactional
    void updateUserPhoneNumber(@Param(value = "id") long id, @Param(value = "phoneNumber") String phoneNumber);

    @Modifying
    @Query("update User u set u.userName = :userName where u.id = :id")
    @Transactional
    void updateUserUserName(@Param(value = "id") long id, @Param(value = "userName") String userName);

    @Modifying
    @Query("update User u set u.email = :email where u.id = :id")
    @Transactional
    void updateUserEmail(@Param(value = "id") long id, @Param(value = "email") String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    @Transactional
    void updateUserPassword(@Param(value = "id") long id, @Param(value = "password") String password);

    @Modifying
    @Query("update User u set u.profilePicture = :profilePicture where u.id = :id")
    @Transactional
    void updateUserProfilePicture(@Param(value = "id") long id, @Param(value = "profilePicture") String profilePicture);
}