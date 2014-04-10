package skynet.dictgen;

import java.util.ArrayList;
import java.util.List;

public class TableMetaInfo {

	private String tableName;

	private String remark;

	private List<FieldMetaInfo> fields = new ArrayList<FieldMetaInfo>();

	public TableMetaInfo() {
		super();
	}

	public TableMetaInfo(String tableName, String remark, List<FieldMetaInfo> fields) {
		super();
		this.tableName = tableName;
		this.remark = remark;
		this.fields = fields;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<FieldMetaInfo> getFields() {
		return fields;
	}

	public void addField(FieldMetaInfo field) {
		if (field != null)
			fields.add(field);
	}

}
