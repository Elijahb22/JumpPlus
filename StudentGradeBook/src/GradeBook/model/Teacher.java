package GradeBook.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	private List<Class> classes;
	private String username;
	private String password;
	
	public Teacher(String username, String password) {
		this.username = username;
		this.password = password;
		classes = new ArrayList<Class>();
	}
	
	public Class addClass(String name) {
		Class ret = new Class(name);
		classes.add(ret);
		return ret;
	}
	
	public void addStudent(Class classGiven, Student student) {
		classGiven.addStudent(student);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Class> getClasses() {
		return classes;
	}
}
