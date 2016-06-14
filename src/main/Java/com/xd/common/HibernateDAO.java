package com.xd.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings({"rawtypes","static-access","unchecked","deprecation"})
public class HibernateDAO extends HibernateDaoSupport {

    /**
     * 新增对象
     *
     * @param bean
     */
    public void save(BaseBean bean) {
        this.getHibernateTemplate().setFlushMode(getHibernateTemplate().FLUSH_AUTO);
        this.getHibernateTemplate().save(bean);
        this.getHibernateTemplate().flush();
    }

    /**
     * 修改对象
     *
     * @param bean
     */
    public void update(BaseBean bean) {
        this.getHibernateTemplate().update(bean);
        this.getHibernateTemplate().flush();
    }

    /**
     * 删除对象
     *
     * @param bean
     */
    public void delete(BaseBean bean) {
        this.getHibernateTemplate().delete(bean);
        this.getHibernateTemplate().flush();
    }

    /**
     * 根据hql查找对象集合
     *
     * @param hql
     * @return
     */
    public List find(String hql) {
        return this.getHibernateTemplate().find(hql);
    }

    /**
     * 查找记录<br>
     *
     * @param record
     *            需查找的pojo的Class
     * @param key
     *            此pojo的主键值(此为单主键的查找方法)
     * @return 返回从数据库查找到的对象，如数据库无此记录则返回null
     */
    public Object selectByRecordKey(Class<?> record, String key) {
        Object o = getHibernateTemplate().get(record, key);
        return o;
    }

    /**
     * 通过hibernate获得数据库连接<br>
     *
     * @return 数据库Connection
     *
     */
    public Connection getConnection() {
        return (Connection) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException{
                Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                return connection;
            }
        });
    }

    /**
     * 传入hql语句，查找相应的记录,此方法有参数值
     *
     * @param hql
     *            例如: from User user where user.name = ? and user.password = ?
     * @param parameters
     *            提供hql的参数的值,例如:new Object[]{"张三","123456"}
     * @return
     */
    public List find(final String hql, final Object[] parameters) {
        List<?> list = getHibernateTemplate().find(hql, parameters);
        return list;
    }

    /**
     * 根据hql语句和参数删除
     *
     * @param hql
     * @param parameters
     */
    public void deleteByHql(final String hql, final Object[] parameters) {
        this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        query.setParameter(i, parameters[i]);
                    }
                }
                int i = query.executeUpdate();
                return new Integer(i);
            }
        });
    }

    /**
     * 通过hql语句修改记录
     *
     * @param queryString
     *            修改记录的hql语句,例如： update User user set user.name = ? where
     *            user.name = ? and user.password = ?
     * @return
     */
    public Integer updateByHql(final String queryString, final Object[] parameters) {
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(queryString);
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        query.setParameter(i, parameters[i]);
                    }
                }
                int i = query.executeUpdate();
                return new Integer(i);
            }
        });
    }

    /**
     * 通过sql语句查询出符合条件的list
     */
    public List findBySql(final String sql) {
        return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = (ResultSet) stmt.executeQuery(sql);
                List listResult = new ArrayList();
                while (resultSet.next()) {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    Object[] arrObj = new Object[columnCount];
                    for (int j = 0; j < arrObj.length; j++) {
                        int i = rsmd.getColumnType(j + 1);
                        if (i == Types.DATE || i == Types.TIMESTAMP) {
                            arrObj[j] = resultSet.getTimestamp(j + 1);
                        } else {
                            arrObj[j] = resultSet.getObject(j + 1);
                        }
                    }
                    listResult.add(arrObj);
                }
                resultSet.close();
                stmt.close();
                return listResult;
            }
        });
    }

    /**
     * 通过sql语句获取记录的数量
     */
    public int findCountBySql(final String sql) {
        return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                String _sql = "Select count(1) from (" + sql + ")";
                ResultSet resultSet = (ResultSet) stmt.executeQuery(_sql);
                Integer count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
                resultSet.close();
                stmt.close();
                return count;
            }
        });
    }

    /**
     * 通过sql语句修改记录
     */
    public int updateBySql(final String sql) {
        return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                logger.info(sql);
                Integer count = stmt.executeUpdate(sql);
                stmt.close();
                return count;
            }
        });
    }

    /**
     * 通过sql语句修改记录
     */
    public boolean executeBySql(final String sql) {
        return (Boolean) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                logger.info(sql);
                boolean is = stmt.execute(sql);
                stmt.close();
                return is;
            }
        });
    }

    /**
     * 根据类型和主键获取对象
     *
     * @param clazz
     * @param id
     * @return
     */
    public Object get(Class<?> clazz, Serializable id) {
        return getHibernateTemplate().get(clazz, id);
    }
}
