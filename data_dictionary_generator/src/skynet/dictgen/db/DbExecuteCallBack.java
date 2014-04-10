package skynet.dictgen.db;

import java.sql.Connection;

public interface DbExecuteCallBack {

	public Object doExecute(Connection conn) throws Exception;
	
}
