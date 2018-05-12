package com.fenazola.mxcome.db;

import java.sql.SQLException;

import com.fenazola.mxcome.app.BaseApplication;
import com.j256.ormlite.dao.Dao;

@SuppressWarnings("hiding")
public class BaseDaoImpl<T, Integer> extends BaseDao<T, Integer> {

	private Class<T> clazz;

	public BaseDaoImpl() {
	}

	public BaseDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Dao<T, Integer> getDao() throws SQLException {
		return BaseApplication.getInstance().getDbHelper().getDao(clazz);
	}
}
