package com.smile.AuthServer.DO;

import java.security.NoSuchAlgorithmException;

import com.smile.AuthServer.util.SHA256;

public class UsersDO {
	private String id;
	private String password;
	private String name;
	private int permission;
	private SHA256 sha256;
	
	public UsersDO() {
		sha256 = new SHA256();
	}
	
	public UsersDO(String id, String password, String name, int permission) {
		super();
		sha256 = new SHA256();
		this.id = id;
		this.password = passwordEncrypt(password);
		this.name = name;
		this.permission = permission;
	}

	private String passwordEncrypt(String password)
	{
		String passwd = password;
		for(int i = 0; i<3;i++)
		{
			try {
				passwd = sha256.encrypt(passwd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return passwd;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		
		this.password = passwordEncrypt(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	

}
