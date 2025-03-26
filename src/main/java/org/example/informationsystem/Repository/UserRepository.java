package org.example.informationsystem.Repository;

import jakarta.transaction.Transactional;
import org.example.informationsystem.Entity.DTO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            "u.username = :#{#user.username}, " +
            "u.passwordHash = :#{#user.passwordHash}, " +
            "u.email = :#{#user.email}, " +
            "u.role = :#{#user.role}, " +
            "u.isActive = :#{#user.isActive}, " +
            "u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.userId = :#{#user.userId}")
    void updateById(@Param("user") User user);
}
