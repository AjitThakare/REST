package dbHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import dao.User;

public class UserDBHandler {

	String user="ajit";
	String pass="Ajit@1234";
	String dbname="users";
	String servername="localhost";
	
	public Connection getDBConnection()
	{		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pass);
		dataSource.setServerName(servername);
		dataSource.setDatabaseName(dbname);		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
		} catch (SQLException e) {
					e.printStackTrace();
		}
		return conn; // Initialized null
	}
	
	public String createTable()
	{
		try {			
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				String query="create table if not exists userinformation (id int auto_increment primary key, username varchar(20) not null, password varchar(20) not null, mobile varchar(10), emailid varchar(30))";
				stmt.executeUpdate(query);
				
				stmt.close();
				conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();			
		}
		return "table created!";
	}
	public String login(String username,String pass)
	{
		String result="NotAUser";
		
		ResultSet rs;
		Connection conn = null;
		Statement stmt ;
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT username,password from userinformation where username='"+username+"';");
			
			if(rs.first())
			{
				if(pass.equals(rs.getString(2)))
					{
						result="pass";
					}
				else{
					result="fail";
				}
			}
			rs.close();
			stmt.close();
			conn.close();  // After user close them
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return result;		
	}
	
	public User register(String username,String password,String mobile,String emailID)
	{
		if(getUser(username)==null)
		{
			System.out.println("Username is available");
		
		try {
			Connection conn=getDBConnection();
		//	User user= new User(id,username,password,mobile,emailID);		
					
			String query = " insert into userinformation (username,password,mobile,emailid)"+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, username);
		      preparedStmt.setString (2, password);
		      preparedStmt.setString (3, mobile);
		      preparedStmt.setString (4, emailID);
		      preparedStmt.execute();			
		      preparedStmt.close();
			conn.close();  // After user close them
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
		return getUser(username);
		}
		return null;   // if username is already present
	}

	public List<User> getAllUsers() {
		List<User> list= new ArrayList<User>();
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;
			User user;
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from userinformation;");
				while(rs.next())
				{
					user= new User();
					user.setUserId(rs.getInt(1));
					user.setUsrName(rs.getString(2));
					user.setPassword(rs.getString(3));
					user.setMobileNo(rs.getString(4));
					user.setEmailID(rs.getString(5));
					list.add(user);					
				}						
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		return list;
}
	
	
	public User getUser(String name) {
		User user = null;
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from userinformation where username='"+name+"';");
				if(rs.first())
				{
					user= new User();
					user.setUserId(rs.getInt(1));
					user.setUsrName(rs.getString(2));
					user.setPassword(rs.getString(3));
					user.setMobileNo(rs.getString(4));
					user.setEmailID(rs.getString(5));					
				}
				else
				{
					System.out.println("No such user");
				}				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return user;
}
}