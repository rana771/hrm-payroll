package com.bracu.hrm.dao;

import com.bracu.hrm.exception.ServiceConditionException;
import com.bracu.hrm.exception.SystemException;
import com.bracu.hrm.model.util.Holiday;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Repository("holidayDao")
@Transactional
@Slf4j
public class HolidayDaoImpl extends AbstractDao<Integer, Holiday> implements HolidayDao {
    @Override
    public void save(Holiday holiday) {
        try {
            super.persist(holiday);
        } catch (ConstraintViolationException e) {
            log.error(e.getMessage());
            throw new SystemException(e.getMessage());
        } catch (JDBCConnectionException e) {
            log.error(e.getMessage());
            throw new SystemException(e.getMessage());
        } catch (UnsupportedOperationException e) {
            log.error(e.getMessage());
            throw new ServiceConditionException(e.getMessage());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ServiceConditionException(e.getMessage());
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            throw new ServiceConditionException(e.getMessage());
        }

    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Holiday> getHolidayById(Integer id) {
        String sql = "SELECT * FROM holiday WHERE id =" + id;
        return executeSQL(sql);
    }

    @Override
    public List<Holiday> getHolidayList() {
        String sql =
                "SELECT * FROM holiday";
        /*List<Holiday> holidayList =null;
        Criteria criteria = createEntityCriteria();
        holidayList = (List<Holiday>)criteria.list();*/
        return executeSQL(sql);

    }

    @Override
    public Holiday getEntityById(int id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Holiday holiday = (Holiday) criteria.uniqueResult();
        return holiday;
    }

    @Override
    public boolean uniqueDateTest(Date date) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("date", date));
        if (criteria.list().size() != 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean uniqueDateTestById(int id, Date date) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));

        if (criteria.list().size() == 0) {
            return true;
        } else {
            return false;
        }
    }


}
