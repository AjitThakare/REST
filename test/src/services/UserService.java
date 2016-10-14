package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.User;
import dbHandler.QuestionDBHandler;
import dbHandler.UserDBHandler;
@Path("/users")
public class UserService {
	UserDBHandler dbm= new UserDBHandler();

	@GET
	@Path("/table")
	public String createTable()      // Creates table if not present
	{
		dbm.createTable();
		return "table created";
	}

@GET
@Produces(MediaType.APPLICATION_JSON)
public List<User> getRegistredUsers()
{
	List<User> list= new ArrayList<User>();
	list=dbm.getAllUsers();
	return list;
}


public static void main(String[] args)
{
	
//	dbm.login("ajit","P@ssw0rd");
// 	dbm.register("Nitin","Axe","654654544","ni3@gmail.com");   // works good
}

@GET
@Path("/login/{username}/{password}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response login(@PathParam("username") String userName,@PathParam("password") String pass)
{
	if(dbm.login(userName, pass).equalsIgnoreCase("pass"))
		{
		return Response.status(200).entity("{\"Result\" : \"Succesful\"}").build();
		}
	else if(dbm.login(userName, pass).equalsIgnoreCase("Notauser"))
	{
		return Response.status(404).entity("{\"Result\" : \"No Such User\"}").build();
	}
	else
	{
		return Response.status(401).entity("{\"Result\": \"Failed\"}").build();
	}
	
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response registerNew(User user)
{
	 
	if(dbm.register(user.getUsrName(), user.getPassword(),user.getMobileNo(),user.getEmailID())!=null)
		{
		return Response.status(201).entity("{\"Result\": \"Registered Successfully\"}").build();
		}
	else{
		return Response.status(403).entity("{\"Result\": \"Duplicate Username\"}").build();
	}
}

}
