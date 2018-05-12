package com.fenazola.mxcome.db;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends OrmLiteSqliteOpenHelper {

	private static int version = 1;

	public DbHelper(Context context) {
		super(context, "mxcome.db", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource source) {
		try {
			TableUtils.createTableIfNotExists(source, TableArea.class);
			TableUtils.createTableIfNotExists(source, TableTalk.class);
			TableUtils.createTableIfNotExists(source, TableChat.class);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int arg2, int arg3) {
		try {
			TableUtils.dropTable(source, TableArea.class, true);
			TableUtils.dropTable(source, TableTalk.class, true);
			TableUtils.dropTable(source, TableChat.class, true);
		}catch (Exception e){
			e.printStackTrace();
		}
		onCreate(db, source);
	}

}
