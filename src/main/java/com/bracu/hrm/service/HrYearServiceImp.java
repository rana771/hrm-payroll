package com.bracu.hrm.service;

import com.bracu.hrm.dao.HrYearDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.util.HrYear;
import com.bracu.hrm.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 1/17/2018.
 */
@Service
@Transactional
public class HrYearServiceImp implements HrYearService {
    @Autowired
    HrYearDao hrYearDao;
    @Autowired
    MessageSource messageSource;
    @Override
    public void save(HrYear hrYear) {
        hrYear.setIsOpen(true);
        hrYear.setVersion(0);
        hrYear.setDateCreate(new Date());
        hrYearDao.save(hrYear);

    }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getlist() {
        List<HrYear> list = hrYearDao.findAll();
        return JSONUtil.getJsonObject(list);
    }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getEntityById(int i) {
        HrYear hrYear = hrYearDao.findById(i);
        return JSONUtil.getJsonObject(hrYear);
    }

}
