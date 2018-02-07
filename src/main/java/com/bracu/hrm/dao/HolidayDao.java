package com.bracu.hrm.dao;

import com.bracu.hrm.model.util.Holiday;

import java.util.Date;
import java.util.List;

public interface HolidayDao {
    void save(Holiday holiday);

    boolean uniqueNameTest(Integer id, String name);

    List<Holiday> getHolidayById(Integer id);

    List<Holiday> getHolidayList();
    Holiday getEntityById(int id);

    void delete(Holiday holiday);

    boolean uniqueDateTest(Date date);

    boolean uniqueDateTestById(int id, Date date);
}
