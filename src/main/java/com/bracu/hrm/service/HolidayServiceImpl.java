package com.bracu.hrm.service;

import com.bracu.hrm.cache.CacheService;
import com.bracu.hrm.dao.HolidayDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.util.Holiday;
import com.bracu.hrm.util.JSONUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService{

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean uniqueNameCheck(Integer id, String name) {
        return holidayDao.uniqueNameTest( id,name);
    }

    @Override
    @Transactional
    public void save(Holiday holiday) {
        holiday.setVersion(0);
        holiday.setDateCreate(new Date());
        //holiday.setUserCreated(cacheService.getUser());
        holidayDao.save(holiday);
    }

    @Override
    @Transactional
    public String update(Holiday holiday) {
        Holiday currentHoliday = holidayDao.getHolidayById(holiday.getId());
        if (holiday.getVersion() < currentHoliday.getVersion()) {
            String message = messageSource.getMessage("update.version.change", new String[]{"EmployeeType Information", String.valueOf(holiday.getVersion())}, Locale.getDefault());
            return message;
        } else {
            currentHoliday.setName(holiday.getName());
            currentHoliday.setDescription(holiday.getDescription());
            currentHoliday.setDateLastUpdate(new Date());
            currentHoliday.setVersion(holiday.getVersion()+1);
            currentHoliday.setUserLastUpdated(cacheService.getUser());
            holidayDao.save(currentHoliday);
            String message = messageSource.getMessage("save.updated.message", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
            return message;
        }
       }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getHolidayList() {
        List<Holiday> list = holidayDao.getHolidayList();
        String result = "";
        try {
//             result = JSONUtil.getJsonObject(list);
               Gson gson = new Gson();
               result = gson.toJson(list);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return  result;
    }
}

