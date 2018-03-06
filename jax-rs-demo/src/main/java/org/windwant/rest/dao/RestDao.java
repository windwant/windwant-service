package org.windwant.rest.dao;

import org.apache.ibatis.session.SqlSession;
import org.windwant.rest.BaseDao;

/**
 * Created by Administrator on 2017/12/19.
 */
public class RestDao {
    public String selectStudent(String name){
        SqlSession sqlSession = BaseDao.getSqlSession();
        String result = sqlSession.selectList("org.windwant.rest.mapper.RestMapper.selectStudent", name).toString();
        System.out.println(result);
        BaseDao.releaseSession(sqlSession);
        return result;
    }
}
