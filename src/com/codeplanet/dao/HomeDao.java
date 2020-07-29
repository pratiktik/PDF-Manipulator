package com.codeplanet.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.codeplanet.model.User;
import com.codeplanet.model.UserFeed;

@Repository
public class HomeDao {
@Autowired
JdbcTemplate jdbcTemplate;

/*
 * public void insertInfo(User user) throws SQLException {
 * 
 * Connection con =jdbcTemplate.getDataSource().getConnection();
 * 
 * CallableStatement pst=con.prepareCall("call test(?,?,?)");
 * pst.setString(1,"add"); pst.setString(2,""); pst.setInt(3,0); ResultSet
 * i=pst.executeQuery(); while(i.next()) System.out.println(i.getString(2));
 * con.close(); }
 */







	public String getInfo()  {
		Connection con=null;
		
		String s = null;
		try {
			
			con = jdbcTemplate.getDataSource().getConnection();
			Statement st=con.createStatement();
			String str="select * from sdetails";
			ResultSet rs=st.executeQuery(str);
			while(rs.next()){
				 s=rs.getInt(1)+""+rs.getString(2);
			    }	
			
			}
		catch(SQLException e) {e.printStackTrace();}
		finally {
			try {con.close();} 
			catch (SQLException e) {e.printStackTrace();}			
		}
		
		return s;
		
		}
	
	
	public void insertInfo(UserFeed userfeed) throws SQLException {
		
		Connection con =jdbcTemplate.getDataSource().getConnection();
		
		PreparedStatement pst=con.prepareStatement("insert into feedback values(?,?,?)");
		String x1 = userfeed.getName();
		String x2 = userfeed.getEmail();
		String x3 = userfeed.getFeedback();
		
		pst.setString(1, x1);
		pst.setString(2, x2);
		pst.setString(3, x3);
		
		int i=pst.executeUpdate();
		
		System.out.println(i);
		con.close();
	}

	

	public ArrayList<UserFeed> getInfo1() {
Connection con=null;
		
ArrayList<UserFeed> s = new ArrayList<UserFeed>();
		try {
			
			con = jdbcTemplate.getDataSource().getConnection();
			Statement st=con.createStatement();
			String str="select * from feedback";
			ResultSet rs=st.executeQuery(str);
			while(rs.next()){
				
				UserFeed u=new UserFeed();
				u.setName(rs.getString(1));
				u.setFeedback(rs.getString(3));
				s.add(u);
			    }	
			
			}
		catch(SQLException e) {e.printStackTrace();}
		finally {
			try {con.close();} 
			catch (SQLException e) {e.printStackTrace();}			
		}
		
		return s;
		
		
		
		
	}

	public HashMap<String, String> getPassword() {
		Connection con=null;		
		HashMap<String, String> hm=new 	HashMap<String, String>();
		try {
			
			con = jdbcTemplate.getDataSource().getConnection();
			Statement st=con.createStatement();
			String str="select emailId,password from sdetails";
			ResultSet rs=st.executeQuery(str);
			while(rs.next()){
			
				String k=rs.getString(1);
				
				String v=rs.getString(2);
				
				hm.put(k, v);
				
			    }	
		    }
		catch(SQLException e) {
			e.printStackTrace();
			}
		
		finally
		{
			try {
				con.close();
				} 
			catch (SQLException e) {
				e.printStackTrace();
				}			
		}
		
		return hm;
	}
	
	public ArrayList<User> getProfile(String email) {
		Connection con=null;
				
		ArrayList<User> s = new ArrayList<User>();
				try {
					
					con = jdbcTemplate.getDataSource().getConnection();
					Statement st=con.createStatement();
					String str="select emailId,userName,mobile,userId from sdetails";
					ResultSet rs=st.executeQuery(str);
					while(rs.next()){
						
						User u=new User();
						u.setEmailId(rs.getString(1));
						u.setUserName(rs.getString(2));
						u.setMobile(rs.getString(3));
						u.setUserId(rs.getString(4));						
						s.add(u);
					    }	
					
					}
				catch(SQLException e) {e.printStackTrace();}
				finally {
					try {con.close();} 
					catch (SQLException e) {e.printStackTrace();}			
				}
				
				return s;

	}

	
	public void insertInfo(User user) throws SQLException {
		
		Connection con =jdbcTemplate.getDataSource().getConnection();

		PreparedStatement pst=con.prepareStatement("insert into sdetails values(?,?,?,?,?)");
		String x1 = user.getEmailId();
		String x2 = user.getMobile();
		String x3 = user.getPassword();
		String x4 = user.getUserName();
		String x5 = user.getUserId();
		
		pst.setString(1, x4);
		pst.setString(2, x1);
		pst.setString(3, x2);
		pst.setString(4, x3);
		pst.setString(5, x5);
		
		int i=pst.executeUpdate();
		
		System.out.println(i);
		
		con.close();
	}


	}
