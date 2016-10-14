package dbHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import dao.Question;

public class QuestionDBHandler {


	String user="ajit";
	String pass="Ajit@1234";
	String dbname="questionsdb";
	String servername="localhost";
	String tableName="questionstable";
	
	
	
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
	
	public void createTable()
	{
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				String query="create table if not exists "+tableName+" (id int auto_increment primary key,question varchar(70) not null, option1 varchar(20) not null,option2 varchar(20) not null,option3 varchar(20) not null,option4 varchar(20) not null, correctans int not null, topic varchar(40))";
				stmt.executeUpdate(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();			
		}
		
	}
	public Question addQuestion(String question, String option1,String option2,String option3,String option4,int correctAns,String topicName)
	{
		Question q= null;
		if(getQuestionByQuestion(question)==null)
		{
			try {
			Connection conn=getDBConnection();
		//	User user= new User(id,username,password,mobile,emailID);		
					
			String query = " insert into "+tableName+" (question,option1,option2,option3,option4,correctans,topic)"+ " values (?, ?, ?, ?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, question);
		      preparedStmt.setString (2, option1);
		      preparedStmt.setString (3, option2);
		      preparedStmt.setString (4, option3);
		      preparedStmt.setString (5, option4);
		      preparedStmt.setInt(6, correctAns);
		      preparedStmt.setString (7, topicName);
		      preparedStmt.execute();			
		      preparedStmt.close();
		      
		      ResultSet rs;
		      Statement stmt ;			
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * from "+tableName+" ;");
		      rs.last();
		    q= getQuestionByID(rs.getInt(1));
		      stmt.close();
		      rs.close();
			conn.close();  // After user close them
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		}
		
		return q;
	}
	
	public Question getQuestionByID(int id) {
		Question q=null;
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from "+tableName+" where id='"+id+"';");
				if(rs.first())
				{
					q= new Question();
					q.setId(rs.getInt(1));
					q.setQuestion(rs.getString(2));
					q.setOption1(rs.getString(3));
					q.setOption2(rs.getString(4));
					q.setOption3(rs.getString(5));
					q.setOption4(rs.getString(6));
					q.setCorrectAnswer(rs.getInt(7));
					q.setTopic(rs.getString(8));									
				}
				else
				{
					System.out.println("No question with this id");
				}				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return q;
			}	
	
	public Question updateQuestionByID(int id,Question question)
	{Question q=null;
	try {
		Connection conn = null;
		Statement stmt ;			
			conn = getDBConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("update "+tableName+" set question='"+question.getQuestion()+
					"',option1='"+question.getOption1()+"',option2='"+question.getOption2()+
					"',option3='"+question.getOption3()+"',option4='"+question.getOption4()+
					"',correctans='"+question.getCorrectAnswer()+"',topic='"+question.getTopic()+"' where id='"+id+"';");
						
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return getQuestionByID(id);
		}	
	public Question getQuestionByQuestion(String quest) {
		Question q=null;
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from "+tableName+" where question='"+quest+"';");
				if(rs.first())
				{
					q= new Question();
					q.setId(rs.getInt(1));
					q.setQuestion(rs.getString(2));
					q.setOption1(rs.getString(3));
					q.setOption2(rs.getString(4));
					q.setOption3(rs.getString(5));
					q.setOption4(rs.getString(6));
					q.setCorrectAnswer(rs.getInt(7));
					q.setTopic(rs.getString(8));									
				}
				else
				{
					System.out.println("No question found!");
				}				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return q;
			}	
	public List<Question> getAllQuestions() {
		List<Question>list= new ArrayList<Question>();
		Question q=null;
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from "+tableName+" ;");
				while(rs.next())
				{
					q= new Question();
					q.setId(rs.getInt(1));
					q.setQuestion(rs.getString(2));
					q.setOption1(rs.getString(3));
					q.setOption2(rs.getString(4));
					q.setOption3(rs.getString(5));
					q.setOption4(rs.getString(6));
					q.setCorrectAnswer(rs.getInt(7));
					q.setTopic(rs.getString(8));
					list.add(q);
				}			
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return list;
			}
	
	public List<Question> getQuestionsByTopic(String topicName) {
		List<Question>list= new ArrayList<Question>();
		Question q=null;
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * from "+tableName+" where topic='"+topicName+"';");
				while(rs.next())
				{
					q= new Question();
					q.setId(rs.getInt(1));
					q.setQuestion(rs.getString(2));
					q.setOption1(rs.getString(3));
					q.setOption2(rs.getString(4));
					q.setOption3(rs.getString(5));
					q.setOption4(rs.getString(6));
					q.setCorrectAnswer(rs.getInt(7));
					q.setTopic(rs.getString(8));
					list.add(q);
				}			
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return list;
			}	
	
	public int deleteQuestionById(int id)
	{
		try {
			ResultSet rs;
			Connection conn = null;
			Statement stmt ;			
				conn = getDBConnection();
				stmt = conn.createStatement();
				return stmt.executeUpdate("delete from "+tableName+" where id='"+id+"';");						
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		return id;
	}
	
}
