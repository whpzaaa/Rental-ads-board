package org.example.informationsystem.Service;


import org.example.informationsystem.pojo.entity.AdminLogs;

import java.util.List;

public interface AdminLogsService {
    List<AdminLogs> getAllLogs();

    AdminLogs getLogById(Long id);

    boolean createLog(AdminLogs adminLogs);

    boolean deleteLog(Long id);

    boolean updateById(AdminLogs adminLogs);
}
