package org.example.informationsystem.Repository;

import jakarta.transaction.Transactional;
import org.example.informationsystem.pojo.entity.User;
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
            "u.username = CASE WHEN :#{#user.username} IS NOT NULL THEN :#{#user.username} ELSE u.username END, " +
            "u.passwordHash = CASE WHEN :#{#user.passwordHash} IS NOT NULL THEN :#{#user.passwordHash} ELSE u.passwordHash END, " +
            "u.email = CASE WHEN :#{#user.email} IS NOT NULL THEN :#{#user.email} ELSE u.email END, " +
            "u.role = CASE WHEN :#{#user.role} IS NOT NULL THEN :#{#user.role} ELSE u.role END, " +
            "u.isActive = CASE WHEN :#{#user.isActive} IS NOT NULL THEN :#{#user.isActive} ELSE u.isActive END, " +
            "u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.userId = :#{#user.userId}")
    void updateById(@Param("user") User user);

    User getByUsername(String username);

    User getByEmail(String email);
}
