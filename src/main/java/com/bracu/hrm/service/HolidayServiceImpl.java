package com.bracu.hrm.service;

import com.bracu.hrm.cache.CacheService;
import com.bracu.hrm.dao.HolidayDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.exception.SystemException;
import com.bracu.hrm.model.util.Holiday;
import com.bracu.hrm.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@Slf4j
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean uniqueNameCheck(Integer id, String name) {
        return holidayDao.uniqueNameTest(id, name);
    }

    @Override
    @Transactional
    public String save(Holiday holiday) {
        String message = "";
        holiday.setVersion(0);
        holiday.setDateCreate(new Date());
        holiday.setUserCreated(cacheService.getUser());
        if (!holidayDao.uniqueNameTest(holiday.getId(), holiday.getName())) {
            message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
        }else if(holidayDao.uniqueDateTest(holiday.getDate())){
            message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
        } else {
            holidayDao.save(holiday);
            message = messageSource.getMessage("save.successful.message", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
            }
        return message;
    }

    @Override
    @Transactional
    public String update(Holiday holiday) {
        String message = "";
        Holiday currentHoliday = holidayDao.getEntityById(holiday.getId());
        if (holiday.getVersion() < currentHoliday.getVersion()) {
            message = messageSource.getMessage("update.version.change", new String[]{"Holiday Information", String.valueOf(holiday.getVersion())}, Locale.getDefault());
            return message;
        } else {
            currentHoliday.setName(holiday.getName());
            currentHoliday.setDescription(holiday.getDescription());
            currentHoliday.setDateLastUpdate(new Date());
            currentHoliday.setVersion(holiday.getVersion() + 1);
            currentHoliday.setUserLastUpdated(cacheService.getUser());

            if (!holidayDao.uniqueNameTest(holiday.getId(), holiday.getName())) {
                message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
            }else if(holidayDao.uniqueDateTestById(holiday.getId(), holiday.getDate())){
                message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
            } else {


                holidayDao.save(currentHoliday);

                message = messageSource.getMessage("save.updated.message", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
            }
            return message;
        }
    }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getHolidayList() {
        List<Holiday> list = holidayDao.getHolidayList();
        return JSONUtil.getJsonObject(list);
    }

    @Override
    public String getHolidayById(Integer id) {
        List<Holiday> holiday = holidayDao.getHolidayById(id);
        try {
            return JSONUtil.getJsonObject(holiday.get(0));
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new SystemException(e.getMessage());
        }

    }

    @Override
    public String delete(Holiday holiday) {
        Holiday currentHolyday = holidayDao.getEntityById(holiday.getId());
        if (!currentHolyday.getVersion().equals(holiday.getVersion()) || holiday == null) {
            String message = messageSource.getMessage("delete.version.message", new String[]{"Holyday Information", String.valueOf(currentHolyday.getVersion())}, Locale.getDefault());
            return message;
        } else {
            holidayDao.delete(currentHolyday);
            String message = messageSource.getMessage("delete.success.message", new String[]{"Holyday Information", String.valueOf(currentHolyday.getVersion())}, Locale.getDefault());
            return message;

        }
    }
}

