package com.che.dao.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.beanutils.PropertyUtils;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * @author zhaojianjun
 * @version 1.0
 */
@SuppressWarnings(value = { "unused", "unchecked", "rawtypes" })
public abstract class BaseIbatis3Dao<E, PK extends Serializable> implements EntityDao<E, PK> {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	@Qualifier("sqlSessionTemplate")
	protected SqlSessionTemplate sqlSession;

	public Object getById(PK primaryKey) {
		Object object = sqlSession.selectOne(getFindByPrimaryKeyStatement(), primaryKey);
		return object;
	}

	public List<E> getList(E entity) {
		return sqlSession.selectList(getFindListStatement(), entity);
	}

	public Object getByUserId(String userId) throws DataAccessException {
		return sqlSession.selectOne(getFindByUserIdStatement(), userId);
	}

	public Object getByOrderId(String orderId) {
		Object object = sqlSession.selectOne(getFindByOrderIdStatement(), orderId);
		return object;
	}

	public void deleteById(PK id) {
		int affectCount = sqlSession.delete(getDeleteStatement(), id);
	}

	public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = sqlSession.insert(getInsertStatement(), entity);
	}

	public void update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = sqlSession.update(getUpdateStatement(), entity);
	}

	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * 
	 * @param o
	 */
	protected void prepareObjectForSaveOrUpdate(E o) {
	}

	public String getIbatisMapperNamesapce() {
		throw new RuntimeException("not yet implement");
	}

	public String getFindByPrimaryKeyStatement() {
		return getIbatisMapperNamesapce() + ".getById";
	}

	public String getFindListStatement() {
		return getIbatisMapperNamesapce() + ".getList";
	}

	public String getFindByUserIdStatement() {
		return getIbatisMapperNamesapce() + ".getByUserId";
	}

	public String getFindByOrderIdStatement() {
		return getIbatisMapperNamesapce() + ".getByOrderId";
	}

	public String getInsertStatement() {
		return getIbatisMapperNamesapce() + ".insert";
	}

	public String getUpdateStatement() {
		return getIbatisMapperNamesapce() + ".update";
	}

	public String getDeleteStatement() {
		return getIbatisMapperNamesapce() + ".delete";
	}

	public String getCountStatementForPaging(String statementName) {
		return statementName + ".count";
	}

	protected Page pageQuery(String statementName, PageRequest pageRequest) {
		return pageQuery(statementName, getCountStatementForPaging(statementName), pageRequest);
	}

	public Page pageQuery(String statementName, String countStatementName, PageRequest pageRequest) {

		Number totalCount = (Number) sqlSession.selectOne(countStatementName, pageRequest);
		if (totalCount == null || totalCount.longValue() <= 0) {
			return new Page(pageRequest, 0);
		}

		Page page = new Page(pageRequest, totalCount.intValue());

		// 其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用.
		// 与getSqlMapClientTemplate().queryForList(statementName,
		// parameterObject)配合使用
		Map filters = new HashMap();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());

		Map parameterObject = PropertyUtils.describe(pageRequest);
		filters.putAll(parameterObject);

		// List list = sqlSession..selectList(statementName, filters,
		// page.getFirstResult(), page.getPageSize());
		List list = sqlSession.selectList(statementName, filters,
				new RowBounds(page.getFirstResult(), page.getPageSize()));
		page.setResult(list);
		return page;
	}

	public List findAll() {
		throw new UnsupportedOperationException();
	}

	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}

	public void flush() {
		// ignore
	}

}
