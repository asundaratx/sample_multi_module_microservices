package org.example.microsvc.mobileapp.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.example.microsvc.mobileapp.model.data.UserModel;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<UserModel,Long> {
    UserModel findByUserId(String userId);
    UserModel findByEmail(String email);
    @Transactional
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.firstName = ?1, u.lastName = ?2, u.email = ?3 where u.userId = ?4")
    void updateUserByUserId(String firstname, String lastname, String email, String userId);
}
