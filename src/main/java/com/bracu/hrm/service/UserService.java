package com.bracu.hrm.service;

import com.bracu.hrm.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
