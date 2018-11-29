package org.windwant.spring.core.hibernate;

/**
 * Created by windwant on 2016/2/26.
 */
public interface TBaseDao {

    Object get(Class entiyClass, int id);
}
