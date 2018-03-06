package org.windwant.spring.core.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by windwant on 2016/2/26.
 */
@Component
public class TBaseDaoImpl implements TBaseDao {
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    protected HibernateTemplate hibernateTemplate;

    public Object get(Class entiyClass, int id) {
        return hibernateTemplate.get(entiyClass, id);
    }
}
