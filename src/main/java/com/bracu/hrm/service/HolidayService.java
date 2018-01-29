package com.bracu.hrm.service;

import com.bracu.hrm.model.util.Holiday;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface HolidayService {
    boolean uniqueNameCheck(Integer id, String name);

    void save(Holiday holiday);

    String update(Holiday holiday);

    String getHolidayList();
}
