package skynet.dictgen;

public class FieldMetaInfo {

	private int position;

	private String defaultValue;

	private String fieldName;

	private String fieldType;

	private boolean isNullable;

	private boolean isAutoIncrement;

	private boolean isPrimaryKey;

	private String fieldRestrict;

	private String comment;

	public FieldMetaInfo() {
		super();
	}

	public FieldMetaInfo(int position, String defaultValue, String fieldName,
			String fieldType, boolean isAutoIncrement, boolean isNullable, boolean isPrimaryKey,
			String fieldRestrict, String comment) {
		super();
		this.position = position;
		this.defaultValue = defaultValue;
		this.isAutoIncrement = isAutoIncrement;
		this.isPrimaryKey = isPrimaryKey;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.isNullable = isNullable;
		this.fieldRestrict = fieldRestrict;
		this.comment = comment;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getFieldRestrict() {
		return fieldRestrict;
	}

	public void setFieldRestrict(String fieldRestrict) {
		this.fieldRestrict = fieldRestrict;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
