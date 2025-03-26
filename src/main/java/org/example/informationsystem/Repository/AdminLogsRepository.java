package org.example.informationsystem.Repository;

import jakarta.transaction.Transactional;
import org.example.informationsystem.Entity.DTO.AdminLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLogsRepository extends JpaRepository<AdminLogs, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE AdminLogs a SET a.actionText = :#{#adminLogs.actionText}, a.createdAt = :#{#adminLogs.createdAt} WHERE a.id = :#{#adminLogs.id}")
    void updateById(@Param("adminLogs") AdminLogs adminLogs);
}
