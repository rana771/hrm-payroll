package com.bracu.hrm.service;

import com.bracu.hrm.model.User;
import com.bracu.hrm.model.org.Company;
import com.bracu.hrm.repository.RoleRepository;
import com.bracu.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CompanyService companyService;
    @Override
    public void save(User user) {
       Company company = companyService.findByName("BRAC University");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCompany(company);
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
    

}
