package com.bracu.hrm.service;

import com.bracu.hrm.model.util.Holiday;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface HolidayService {
    boolean uniqueNameCheck(Integer id, String name);

    String save(Holiday holiday);

    String update(Holiday holiday);

    String getHolidayList();

    String getHolidayById(Integer id);

    String delete(Holiday holiday);
}
