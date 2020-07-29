package com.codeplanet.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplanet.dao.HomeDao;
import com.codeplanet.model.User;
import com.codeplanet.model.UserFeed;

@Service
public class HomeService {
@Autowired
HomeDao homeDao;

	public void insertInfo(UserFeed userfeed) throws SQLException {
		
		System.out.println("service first");	
		homeDao.insertInfo(userfeed);
		System.out.println("service last");	
	}
	
	public String getInfo() {
		String s=homeDao.getInfo();
		return s;
	}

	public ArrayList<UserFeed> getInfo1() {
		ArrayList<UserFeed> s=homeDao.getInfo1();
		return s;
	}
	public HashMap<String, String> getPassword(){
		
		HashMap<String, String> hm=homeDao.getPassword();
		return hm;	
	} 
	public ArrayList<User> getProfile(String email) 
	{
		ArrayList<User> s=homeDao.getProfile(email);
		return s;
	}

	
	public void insertInfo(User user) throws SQLException
	{
		
		homeDao.insertInfo(user);
	}

}
