package org.example.informationsystem.Service.ServiceImpl;

import org.example.informationsystem.Repository.AdminLogsRepository;
import org.example.informationsystem.Service.AdminLogsService;
import org.example.informationsystem.pojo.entity.AdminLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogsServiceImpl  implements AdminLogsService {
    @Autowired
    private AdminLogsRepository adminLogsRepository;
    @Override
    public List<AdminLogs> getAllLogs() {
        return adminLogsRepository.findAll();
    }

    @Override
    public AdminLogs getLogById(Long id) {
        return adminLogsRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createLog(AdminLogs adminLogs) {
        adminLogs.setCreatedAt(java.time.LocalDateTime.now());
        AdminLogs savedLog = adminLogsRepository.save(adminLogs);
        return savedLog != null;
    }

    @Override
    public boolean deleteLog(Long id) {
        if (adminLogsRepository.existsById(id)) {
            adminLogsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(AdminLogs adminLogs) {
        if (!adminLogsRepository.existsById(adminLogs.getId())) {
            return false;
        }
        adminLogs.setCreatedAt(java.time.LocalDateTime.now());
        adminLogsRepository.updateById(adminLogs);
        return true;
    }
}
