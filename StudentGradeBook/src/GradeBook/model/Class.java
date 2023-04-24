package GradeBook.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import GradeBook.util.Colors;

public class Class {
	private String name;
	private List<Student> students;
	private double average;
	private double median;
	
	public Class(String name) {
		this.name = name;
		students = new ArrayList<Student>();
		average = 100;
		median = 100;
	}
	
	public void printClass() {
		System.out.println(Colors.CYAN + "\nClass " + name + ":" + Colors.RESET);
		System.out.print(Colors.PURPLE);
		System.out.println("+----------+");
		System.out.println("| Students |");
		System.out.println("+----------+");
		System.out.print(Colors.CYAN);
		
		students.forEach(System.out::println);
		
		if(students.size() > 0)
			updateStats();
		System.out.println(Colors.GREEN + "Class Average:" + average + Colors.RESET);
		System.out.println(Colors.GREEN + "Class Median:" + median + Colors.RESET);
	}
	
	private void updateStats() {
		average = students.stream().mapToDouble(Student::getGrade).average().getAsDouble();
		median = students.stream()
	        .map(Student::getGrade)
	        .sorted()
	        .collect(Collectors.collectingAndThen(
	                Collectors.toList(),
	                grades -> {
	                    int count = grades.size();
	                    if (count % 2 == 0) { 
	                        return (grades.get(count / 2 - 1) + grades.get(count / 2)) / 2;
	                    } else { 
	                        return grades.get(count / 2);
	                    }
	                }));
	}
	
	public void addStudent(Student student) {
		students.add(student);		
	}
	
	public boolean removeStudent(String name) {
		return students.remove(students.stream().
				filter((student) -> (student.getName().equals(name))).
				findFirst().
				orElse(null));
	}
	
	public boolean changeGrade(String name, double grade) {
		Student s = students.stream().
				filter((student) -> (student.getName().equals(name))).
				findFirst().
				orElse(null);
		if(s != null) {
			s.setGrade(grade);
			return true;
		}
		else
			return false;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
	public void setStudents(List<Student> studentsIn) {
		students = new ArrayList<Student>();
		studentsIn.stream().forEach((student) -> students.add(student));
	}
}
