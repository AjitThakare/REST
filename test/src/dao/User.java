package dao;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	int userId;
String usrName;
String password;
String mobileNo;
String emailID;
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getUsrName() {
	return usrName;
}
public void setUsrName(String usrName) {
	this.usrName = usrName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}
public User(int id,String usrName, String password, String mobileNo, String emailID) {
	super();
	this.usrName = usrName;
	this.password = password;
	this.mobileNo = mobileNo;
	this.emailID = emailID;
	this.userId=id;
	}
public User() {
	super();
	// TODO Auto-generated constructor stub
}


}
