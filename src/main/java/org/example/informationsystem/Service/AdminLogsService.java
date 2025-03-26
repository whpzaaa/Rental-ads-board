package org.example.informationsystem.Service;


import org.example.informationsystem.Entity.DTO.AdminLogs;

import java.util.List;

public interface AdminLogsService {
    List<AdminLogs> getAllLogs();

    AdminLogs getLogById(Long id);

    boolean createLog(AdminLogs adminLogs);

    boolean deleteLog(Long id);

    boolean updateById(AdminLogs adminLogs);
}
