package com.smile.AuthServer.DO;

public class DBConnectionInfo {
	private String jdbcDriverName;
	private String url;
	private String userid;
	private String password;
	
	public DBConnectionInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public DBConnectionInfo(String jdbcDriverName, String url, String userid, String password) {
		super();
		this.jdbcDriverName = jdbcDriverName;
		this.url = url;
		this.userid = userid;
		this.password = password;
	}

	public String getJdbcDriverName() {
		return jdbcDriverName;
	}

	public void setJdbcDriverName(String jdbcDriverName) {
		this.jdbcDriverName = jdbcDriverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "DBConnectionInfo [jdbcDriverName=" + jdbcDriverName + ", url=" + url + ", userid=" + userid
				+ ", password=" + password + "]";
	}
	
	

}
