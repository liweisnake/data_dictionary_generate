package skynet.dictgen;

public class DataSource {

	private String driverClass = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://localhost:3306/mydb";

	private String username = "root";

	private String password = "123456";

	public DataSource() {
		super();
	}

	public DataSource(String driverClass, String url, String username,
			String password) {
		super();
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
