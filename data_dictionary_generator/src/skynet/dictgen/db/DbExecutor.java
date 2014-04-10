package skynet.dictgen.db;

import java.sql.Connection;
import java.sql.DriverManager;

import skynet.dictgen.DataSource;

public class DbExecutor {
	
	private DbExecuteCallBack callBack;
	
	public DbExecutor(DbExecuteCallBack callBack){
		this.callBack = callBack;
	}

	public Object execute(DataSource datasource) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(datasource.getUrl(),
					datasource.getUsername(), datasource.getPassword());
			return callBack.doExecute(conn);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
