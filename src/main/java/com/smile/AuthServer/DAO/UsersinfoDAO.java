package com.smile.AuthServer.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.smile.AuthServer.DO.DBConnectionInfo;
import com.smile.AuthServer.DO.UsersDO;

public class UsersinfoDAO{
	protected DBConnectionInfo dbInfo;
	
	protected Connection conn = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	
	public UsersinfoDAO(DBConnectionInfo dbInfo) {
		super();
		this.dbInfo = dbInfo;
	}
	
	protected void connect() throws ClassNotFoundException, SQLException
	{
		Class.forName(dbInfo.getJdbcDriverName());
		conn = DriverManager.getConnection(dbInfo.getUrl(), dbInfo.getUserid(), dbInfo.getPassword());
	}

	protected void disconnect() throws SQLException{
		// disconnect from DB server
		if(rs != null){
			rs.close();
		}
		if(stmt != null){
			stmt.close();
		}
		if(conn != null){
			conn.close();	
		}
	}	
	
	public List<UsersDO> selectAllUsers() {
		List<UsersDO> usersList = null;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = "select * from login order by id";
				rs = stmt.executeQuery(sql);
				
				if(rs.isBeforeFirst()){
					usersList = new ArrayList<UsersDO>();
					
					while(rs.next()) {
						UsersDO users = new UsersDO();
						users.setId(rs.getString("id"));
						users.setPassword(rs.getString("password"));
						users.setName(rs.getString("name"));
						
						usersList.add(users);
					}
				}
			}	
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return usersList;
		
	}
	
	public UsersDO selectUser(String id) {
		UsersDO user = null;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = String.format("select * from login where id = '%s'", id);
				rs = stmt.executeQuery(sql);
				
				if(rs.isBeforeFirst()){
					user = new UsersDO();
					
					rs.next();
						user.setId(rs.getString("id"));
						user.setPassword(rs.getString("password"));
						user.setName(rs.getString("name"));
					}
				}
			
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public UsersDO loginID(UsersDO users) {
		UsersDO user = null;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = String.format("select * from login where id = '%s' and password = '%s'", users.getId(), users.getPassword());
				rs = stmt.executeQuery(sql);

				if(rs.isBeforeFirst()){
					user = new UsersDO();
					
					rs.next();
					user.setId(rs.getString("id"));
					user.setPassword(rs.getString("password"));
					user.setName(rs.getString("name"));
					user.setPermission(rs.getInt("permission"));
				}
			}
			
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public int insertUser(UsersDO user) {
		int result = -1;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = String.format("insert into login(id, password, name)"
						+ "values ('%s', '%s', '%s') ", user.getId(), user.getPassword(), user.getName());
				result = stmt.executeUpdate(sql);
			}
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int updateUser(UsersDO user) {
		int result = -1;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = String.format("update login set password = '%s', "
						+ "name = '%s' where id = '%s'", user.getPassword(), user.getName(), user.getId());
				result = stmt.executeUpdate(sql);
			}
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteUser(String user) {
		int result = -1;
		
		try {
			connect();
			
			if(conn != null){
				stmt = conn.createStatement();
				
				String sql = String.format("delete from login where id = '%s'", user);
				result = stmt.executeUpdate(sql);
			}
			disconnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
}
