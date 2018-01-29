package com.bracu.hrm.dao;

import com.bracu.hrm.model.util.Holiday;

import java.util.List;

public interface HolidayDao {
    void save(Holiday holiday);

    boolean uniqueNameTest(Integer id, String name);

    Holiday getHolidayById(Integer id);

    List<Holiday> getHolidayList();
}
