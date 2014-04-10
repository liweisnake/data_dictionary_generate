package skynet.dictgen.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import skynet.dictgen.FieldMetaInfo;
import skynet.dictgen.TableMetaInfo;

public class TableMetaInfoRetriver implements DbExecuteCallBack {

	@Override
	public Object doExecute(Connection conn) throws Exception {
		List<TableMetaInfo> result = new ArrayList<TableMetaInfo>();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet tablesResult = metaData.getTables(null, null, "%", null);
		PreparedStatement ps = conn
				.prepareStatement("SELECT TABLE_COMMENT FROM information_schema.tables where TABLE_NAME=?");
		while (tablesResult.next()) {
			String tableName = tablesResult.getString(3);
			TableMetaInfo table = new TableMetaInfo();
			table.setTableName(tableName);

			// get remark
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				table.setRemark(rs.getString(1));

			Map<String, String> primaryKeys = new HashMap<String, String>();
			ResultSet primaryKeyResult = metaData.getPrimaryKeys(null, null,
					tableName);
			while (primaryKeyResult.next()) {
				primaryKeys.put(primaryKeyResult.getString(4), "");
			}

			ResultSet columnResult = metaData.getColumns(null, null, tableName,
					null);
			while (columnResult.next()) {
				FieldMetaInfo field = new FieldMetaInfo();
				field.setFieldName(columnResult.getString(4));
				if (primaryKeys.containsKey(field.getFieldName())) {
					field.setPrimaryKey(true);
					field.setComment("Ö÷¼ü");
				} else {
					field.setPrimaryKey(false);
					field.setComment(columnResult.getString(12));
				}

				field.setPosition(columnResult.getInt(17));
				field.setDefaultValue(columnResult.getString(13));
				String type = columnResult.getString(6);
				field.setFieldType(type
						+ "("
						+ columnResult.getString(7)
						+ (type.equals("DECIMAL") ? ","
								+ columnResult.getInt(9) : "") + ")");
				field.setAutoIncrement(columnResult.getString(23).equals("YES") ? true
						: false);
				field.setNullable(columnResult.getString(18).equals("YES") ? true
						: false);
				table.addField(field);
			}
			result.add(table);
		}
		return result;
	}

}
