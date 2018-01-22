package com.bracu.hrm.service;

import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.User;
import com.bracu.hrm.model.leave.LeaveType;
import com.bracu.hrm.util.JSONUtil;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
