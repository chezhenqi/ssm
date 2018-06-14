package com.che.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.che.dao.base.BaseIbatis3Dao;
import com.che.model.vo.UserVo;

@Repository
public class UserDao extends BaseIbatis3Dao<UserVo, Integer> {

	@Override
	public String getIbatisMapperNamesapce() {
		return "userVoMapper";
	}

	public void saveOrUpdate(UserVo entity) throws DataAccessException {

	}

}
