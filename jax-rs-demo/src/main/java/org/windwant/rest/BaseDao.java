package org.windwant.rest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/12/18.
 */
public class BaseDao {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        getSqlSessionFactory();
    }

    public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory == null) {
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }

    public static void releaseSession(SqlSession sqlSession){
        if(sqlSession == null) return;

        sqlSession.clearCache();
        sqlSession.close();
    }

}
