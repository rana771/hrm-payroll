package com.bracu.hrm.dao;

import com.bracu.hrm.dao.AbstractDao;
import com.bracu.hrm.dao.HolidayDao;
import com.bracu.hrm.model.util.Holiday;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("holidayDao")
@Transactional
public class HolidayDaoImpl extends AbstractDao<Integer, Holiday> implements HolidayDao {
    @Override
    public void save(Holiday holiday) {
        super.persist(holiday);
    }

    @Override
    public Holiday getHolidayById(Integer id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id",id));
        Holiday holiday = (Holiday) criteria.uniqueResult();
        return  holiday;
    }

    @Override
    public List<Holiday> getHolidayList() {
        List<Holiday> holidayList =null;
        Criteria criteria = createEntityCriteria();
        holidayList = (List<Holiday>)criteria.list();
        return holidayList;

    }


}
