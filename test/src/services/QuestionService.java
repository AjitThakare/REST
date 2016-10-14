package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dao.Question;
import dbHandler.QuestionDBHandler;

@Path("/questions")
public class QuestionService {
	QuestionDBHandler qdb;
	
	@GET
	@Path("/table")
	public String createTable()      // Creates table if not present
	{
		qdb= new QuestionDBHandler();
		qdb.createTable();
		return "table created";
	}

@GET
public List<Question> getAllQuestions( @DefaultValue("null")@QueryParam ("topic") String topicName)     // list of Questions
{
	List<Question>list= new ArrayList<Question>();
	qdb= new QuestionDBHandler();
	if(topicName.equals("null"))     // get all questions
	{
		list=qdb.getAllQuestions();
	}
	else
		{
			list=qdb.getQuestionsByTopic(topicName); // by topic
		}
	return list;
}
	
@GET
@Path("/{id}")
public Question getQuesByID(@PathParam("id") int id)     // Single Question by ID
{
	
	qdb= new QuestionDBHandler();
	 return qdb.getQuestionByID(id);
}

@PUT
@Path("/{id}")
public Question updateQuestion(@PathParam("id") int id,Question question)     // Update Question
{	
	qdb= new QuestionDBHandler();
	 return qdb.updateQuestionByID(id,question);
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Question adQuestion(Question ques)
{
	qdb= new QuestionDBHandler();
	return qdb.addQuestion(ques.getQuestion(), ques.getOption1(),ques.getOption2(),ques.getOption3(), ques.getOption4(), ques.getCorrectAnswer(),ques.getTopic());
}
	public static void main(String[] args) {
		
		//QuestionDBHandler	qdb= new QuestionDBHandler();
		//qdb.addQuestion("Question","Option1", "option2", "option3", "option4", 2, "topicName");
	
		
		
		
	}

}
