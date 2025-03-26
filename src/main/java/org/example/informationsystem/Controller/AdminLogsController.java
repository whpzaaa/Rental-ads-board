package org.example.informationsystem.Controller;


import org.example.informationsystem.Service.AdminLogsService;
import org.example.informationsystem.Entity.DTO.AdminLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-logs")
public class AdminLogsController {

    @Autowired
    private AdminLogsService adminLogsService;

    // 获取所有日志
    @GetMapping
    public ResponseEntity<List<AdminLogs>> getAllLogs() {
        List<AdminLogs> logs = adminLogsService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    // 根据ID获取日志
    @GetMapping("/{id}")
    public ResponseEntity<AdminLogs> getLogById(@PathVariable Long id) {
        AdminLogs log = adminLogsService.getLogById(id);
        return log != null ? ResponseEntity.ok(log) : ResponseEntity.notFound().build();
    }

    // 记录管理员操作日志
    @PostMapping
    public ResponseEntity<AdminLogs> createLog(@RequestBody AdminLogs adminLogs) {
        boolean success = adminLogsService.createLog(adminLogs);
        return success ? ResponseEntity.status(HttpStatus.CREATED).body(adminLogs)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 删除日志
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        boolean success = adminLogsService.deleteLog(id);
        return success ? ResponseEntity.ok("删除成功") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("日志不存在");
    }
    // 修改日志
    @PutMapping("/{id}")
    public ResponseEntity<AdminLogs> updateLog(@PathVariable Long id, @RequestBody AdminLogs adminLogs) {
        // 检查日志是否存在
        AdminLogs existingLog = adminLogsService.getLogById(id);
        if (existingLog == null) {
            return ResponseEntity.notFound().build(); // 日志不存在
        }
        // 更新日志
        boolean success = adminLogsService.updateById(adminLogs); // 调用服务层的更新方法
        return success ? ResponseEntity.ok(adminLogs) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
