package com.simonsejse.basketkoth.models;

import com.simonsejse.basketkoth.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>{

    Optional<ApplicationUser> findApplicationUserByUsername(String username);

    Optional<ApplicationUser> findApplicationUserByEmail(String email);

    @Query("SELECT s FROM ApplicationUser s where s.id = ?1")
    Optional<ApplicationUser> findApplicationUserById(Long id);
}
