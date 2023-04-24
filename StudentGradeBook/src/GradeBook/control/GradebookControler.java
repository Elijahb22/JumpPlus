package GradeBook.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import GradeBook.model.Class;
import GradeBook.model.Student;
import GradeBook.model.Teacher;
import GradeBook.util.Colors;

public class GradebookControler {
	private static Scanner input = new Scanner(System.in);
	private static List<Teacher> teachers = new ArrayList<Teacher>();
	private static Teacher currentUser;
	private static Class currentClass;
	
	public static void run() {
		currentUser = null;
		currentClass = null;
		loginMenu();
		chooseClass();
	}

	private static void loginMenu() {
		int option = 0;
		
		do {
			System.out.println(Colors.PURPLE);
			System.out.println("+---------------+");
			System.out.println("| Teacher Login |");
			System.out.println("+---------------+");
			System.out.println(Colors.RESET);
			
			System.out.println("1. Create New Account");
			System.out.println("2. Login");
			System.out.println("3. Exit\n");

			System.out.print(Colors.GREEN + "Enter Choice (1, 2, or 3): " + Colors.YELLOW);
			try {
				option = Integer.parseInt(input.nextLine().trim());
			} catch (Exception e) {
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);
				continue;
			}
			if (option <= 0 || option > 3)
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);

		} while (option <= 0 || option > 3);

		switch (option) {
		case 1:
			createAccount();
			break;
		case 2:
			login();
			break;
		case 3:
			exit();
		default:
			System.out.println("User is null.");
		}
	}
	
	private static void exit() {
		input.close();
		System.out.println(Colors.CYAN + "Goodbye!" + Colors.RESET);
		System.exit(0);
	}

	private static void login() {
		System.out.println(Colors.PURPLE);
		System.out.println("+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+");
		System.out.println(Colors.RESET);
		System.out.print("Username: " + Colors.GREEN);
		String username = input.nextLine().trim();
		System.out.print(Colors.RESET + "Password: " + Colors.GREEN);
		String password = input.nextLine().trim();
		System.out.print(Colors.RESET);

		Teacher found = teachers.stream().filter(e -> e.getUsername().equals(username)).findFirst().orElse(null);
		if (found != null && found.getPassword().equals(password)) {
			currentUser = found;
			System.out.println(Colors.GREEN + "Thank you for logging in!\n" + Colors.RESET);
		}
		else { 
			System.out.println(Colors.RED + "Invalid Credentials." + Colors.RESET);
			loginMenu();
		}
	}

	private static void createAccount() {
		System.out.println(Colors.PURPLE);
		System.out.println("+-----------------------+");
		System.out.println("| Enter Account Details |");
		System.out.println("+-----------------------+");
		System.out.println(Colors.RESET);
		
		System.out.print("Username: " + Colors.YELLOW);
		String username = input.nextLine().trim();
		
		String password;
		do {
			System.out.print(Colors.RESET + "Password: " + Colors.YELLOW);
			password = input.nextLine().trim();
			
			
			
		}while(password == password);

		currentUser = new Teacher(username, password);
		teachers.add(currentUser);

		System.out.println(Colors.CYAN + "Account created.\n" + Colors.RESET);
	}

	private static void chooseClass() {
		printClasses();
		
		int option = 0;
		do {
			System.out.println("1. Add a Class");
			System.out.println("2. Select a Class");
			System.out.println("3. Log out\n");

			System.out.print(Colors.GREEN + "Enter Choice (1, 2, or 3): " + Colors.YELLOW);
			try {
				option = Integer.parseInt(input.nextLine().trim());
			} catch (Exception e) {
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);
				continue;
			}
			if (option <= 0 || option > 3)
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);

		} while(option <= 0 || option > 3);
		
		switch(option) {
		case 1:
			addClass();
			chooseClass();
			break;
		case 2:
			if(currentUser.getClasses().isEmpty()) {
				System.out.println(Colors.RED + "There are no classes to choose from." + Colors.RESET);
				chooseClass();
			}
			else {
				selectClass();
				chooseClass();
			}
			break;
		case 3:
			run();
			break;
		}
	}

	private static void selectClass() {
		printClasses();
		
		Class found = null;
		do {
			System.out.print(Colors.GREEN + "Choose a class: " + Colors.YELLOW);
			String classChosen = input.nextLine();
			
			found = currentUser.getClasses().stream().filter(e -> e.getName().equals(classChosen)).findFirst().orElse(null);
			
			System.out.println(Colors.RESET);
			
			if(found != null)
				currentClass = found;
			else
				System.out.println(Colors.RED + "Not a valid class. Try again." + Colors.RESET);

		} while(found == null);
		
		System.out.println(Colors.GREEN + "You've selected " + currentClass.getName() + Colors.RESET);
		
		classActions();
		
		currentClass = null;
	}

	private static void classActions() {
		currentClass.printClass();
		
		int option = 0;
		do {
			System.out.println("\n1. Add a student");
			System.out.println("2. Remove a student");
			System.out.println("3. Change a student's grade");
			System.out.println("4. Sort by alphabetical order");
			System.out.println("5. Sort by highest grade");
			System.out.println("6. Choose another class\n");

			System.out.print(Colors.GREEN + "Enter Choice (1-6): " + Colors.YELLOW);
			try {
				option = Integer.parseInt(input.nextLine().trim());
			} catch (Exception e) {
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);
				continue;
			}
			if (option <= 0 || option > 6)
				System.out.println(Colors.RED + "Not a valid choice. Try again." + Colors.RESET);

		} while(option <= 0 || option > 6);

		switch(option) {
		case 1:
			System.out.print(Colors.RESET + "Name of student to add:" + Colors.YELLOW);
			currentClass.addStudent(new Student(input.nextLine().trim()));
			System.out.println(Colors.RESET);
			break;
		case 2:
			System.out.print(Colors.RESET + "Name of student to remove:" + Colors.YELLOW);
			String nameRemove = input.nextLine().trim();
			if(currentClass.removeStudent(nameRemove)) //successful removal
				System.out.println(Colors.RED + nameRemove + " is removed from the class." + Colors.RESET);
			else
				System.out.println(Colors.RED + "Could not find student with name: " + nameRemove + Colors.RESET);
			break;
		case 3:
			System.out.print(Colors.RESET + "Name of student:" + Colors.YELLOW);
			String nameGradeChange = input.nextLine().trim();
			
			double grade = -1;
			do {
				System.out.print(Colors.RESET + "Enter new grade for student:" + Colors.YELLOW);
				try {	
					grade = Double.parseDouble(input.nextLine().trim());
				} catch(Exception e) {
					System.out.println(Colors.RED + "Grade must be between 0-100. Try again." + Colors.RESET);
				}
				if(grade < 0 || grade > 100)
					System.out.println(Colors.RED + "Grade must be between 0-100. Try again." + Colors.RESET);
			} while(grade < 0 || grade > 100);
			
			if(currentClass.changeGrade(nameGradeChange, grade))
				System.out.println(Colors.GREEN + "Student " + nameGradeChange + " now has a grade of " + grade + Colors.RESET + "\n");
			else
				System.out.println(Colors.RED + "Could not find student with name: " + nameGradeChange + Colors.RESET);
			break;
		case 4:
			currentClass.setStudents(
					currentClass.getStudents().stream().
					sorted((student1, student2) -> student1.getName().compareTo(student2.getName())).
					toList());
			break;
		case 5:
			currentClass.setStudents(
					currentClass.getStudents().stream().
					sorted((student1, student2) ->  (int) (student2.getGrade() - student1.getGrade() + 0.5)).
					toList());
			break;
		case 6:
			return;
		}
		classActions();
	}

	private static void addClass() {
		System.out.print(Colors.RESET + "Name of class to add:" + Colors.YELLOW);
		
		currentUser.addClass(input.nextLine().trim());
		
		System.out.print(Colors.RESET);
		
		chooseClass();
	}

	private static void printClasses() {
		System.out.println(Colors.PURPLE);
		System.out.println("+-----------------+");
		System.out.println("| List of Classes |");
		System.out.println("+-----------------+");
		System.out.println(Colors.CYAN);
		
		if(currentUser.getClasses().isEmpty())
			System.out.println(Colors.RED + "There are no classes to choose from." + Colors.RESET);
		
		currentUser.getClasses().stream().forEach((c) -> {
			System.out.println(c.getName());
		});
		System.out.println(Colors.RESET);
	}
}
