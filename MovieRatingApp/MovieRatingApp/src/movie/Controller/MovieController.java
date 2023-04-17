package movie.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import movie.Model.Movie;
import movie.Model.User;
import movie.Utility.Colors;
public class MovieController {
	private static Scanner input = new Scanner(System.in);
	private static User loginUser;
	private static Map<String, Movie> movies;
	private static List<User> allUsers;
	
	
	private static void initMovies() {
		movies = new HashMap<String, Movie>();
		movies.put("Jaws", new Movie("Jaws"));
		movies.put("Batman", new Movie("Batmane"));
		movies.put("Ironman", new Movie("Iron Man"));
		movies.put("Avatar", new Movie("Avatar"));
		movies.put("Saving Private Ryan", new Movie("Saving Private Ryan"));
		movies.put("The Phantom Menace", new Movie("The Phantom Menace"));
		movies.put("Twister", new Movie("Twister"));
		movies.put("The Shining", new Movie("The Shining"));
		movies.put("The Ritual", new Movie("The Ritual"));
	}
	
	public static void run() {
		initMovies();
		allUsers = new ArrayList<User>();
		
		loginMenu();
	
		mainMenu();
		
		
	}
	

	
	private static void exit() {
		System.out.println(Colors.GREEN + "Goodbye!" + Colors.RESET);		
		input.close();
		System.exit(0);
	}

	private static void loginMenu() {
		int option = 0;

		do {
			System.out.println(Colors.WHITE);
			System.out.println("+----------------+");
			System.out.println("| Movie Ratings! |");
			System.out.println("+----------------+");
			System.out.println(Colors.RESET);


			
			System.out.println("1. Create New Account");
			System.out.println("2. Login");
			System.out.println("3. Exit\n");

			System.out.print(Colors.GREEN + "Enter Choice (1, 2, or 3): " + Colors.BLUE);
			try {
				option = Integer.parseInt(input.nextLine().trim());
			} catch (Exception e) {
				System.out.println(Colors.GREEN + "Not a valid choice. Try again." + Colors.RESET);
				continue;
			}
			if (option < 0 || option > 3)
				System.out.println(Colors.GREEN + "Not a valid choice. Try again." + Colors.RESET);

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

	private static void login() {
		System.out.println(Colors.WHITE);
		System.out.println("+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+");
		System.out.println(Colors.RESET);
		System.out.print("Email: " + Colors.GREEN);
		String email = input.nextLine().trim();
		System.out.print(Colors.RESET + "Password: " + Colors.GREEN);
		String password = input.nextLine().trim();
		System.out.print(Colors.RESET);

		User found = allUsers.stream().filter(e -> e.getEmail().equals(email)).findFirst().orElse(null);
		if (found != null && found.getPassword().equals(password)) {
			loginUser = found;
			System.out.println(Colors.GREEN + "Thanks for logging in!\n" + Colors.RESET);
		}
		else { 
			System.out.println(Colors.GREEN + "Invalid Credentials." + Colors.RESET);
			loginMenu();
		}
	}

	private static void createAccount() {
		System.out.println(Colors.WHITE);
		System.out.println("+-----------------------+");
		System.out.println("| Enter Account Details |");
		System.out.println("+-----------------------+");
		System.out.println(Colors.RESET);
		
		String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		String email = "";
		
		do {
			System.out.print("Email: " + Colors.WHITE);
			email = input.nextLine().trim();
			
			if(!pattern.matcher(email).matches())
				System.out.println(Colors.WHITE + "Email must be valid. Try again." + Colors.RESET);
		} while(!pattern.matcher(email).matches());
		
		String password, password2;
		do {
			System.out.print(Colors.RESET + "Password: " + Colors.WHITE);
			password = input.nextLine().trim();
			
			System.out.print(Colors.RESET + "Password Confirmation: " + Colors.WHITE);
			password2 = input.nextLine().trim();
			
			if(!password.equals(password2)) {
				System.out.println(Colors.GREEN + "Passwords do not match! Try again." + Colors.RESET);
			}
		} while(!password.equals(password2));

		loginUser = new User(email, password);
		allUsers.add(loginUser);

		System.out.println(Colors.GREEN + "Account created.\n" + Colors.RESET);
	}
	
	
	private static void mainMenu() {
		System.out.println(Colors.GREEN);
		System.out.println("+----------------+");
		System.out.println("| Movie Ratings! |");
		System.out.println("+----------------+");
		System.out.println(Colors.RESET);
		
		if(loginUser.getUserRatings().isEmpty())
			System.out.println(Colors.GREEN + "You have no ratings!\n" + Colors.RESET);
		else {
			System.out.println(Colors.GREEN + "Your Movie Ratings:");
			loginUser.printUserRatings();
			System.out.println(Colors.RESET);
		}
		
		printAllMovies();
		
		boolean found = false;
		do {
			System.out.print(Colors.RESET + "Enter Movie To Rate ('q' to log out):" + Colors.GREEN);
			String movieTitle = input.nextLine().trim();
			
			if(movieTitle.equalsIgnoreCase("q")) {
				loginUser = null;
				loginMenu();
				
				if(loginUser == null)
					exit();
				
				mainMenu();
				return;
			}
			System.out.println(Colors.RESET);
			
			found = movies.containsKey(movieTitle);
			
			if(found) {
				double rating = -1;
				
				do {
					System.out.print(Colors.RESET + "Enter Rating (0-5):" + Colors.BLUE);
					try {
						rating = Double.parseDouble(input.nextLine().trim());
					} catch (Exception e) {
						System.out.println(Colors.WHITE + "Not a valid choice. Try again." + Colors.RESET);
						continue;
					}
					
					if(rating < 0 || rating > 5)
						System.out.println(Colors.WHITE + "Rating must be between 0 and 5. Try again." + Colors.RESET);
				} while(rating < 0 || rating > 5);
				System.out.println(Colors.RESET);
				
				loginUser.addRating(movies.get(movieTitle), rating);
			}
			else {
				System.out.println(Colors.WHITE + "Invalid Movie Selected. Try Again" + Colors.RESET);
			}
				
		} while(!found);
		
		mainMenu();
	}

	private static void printAllMovies() {
		System.out.print(Colors.GREEN);
		System.out.printf("%-30s\t%s\t%s%n", "Title", "Average Rating", "Number of Ratings");
		System.out.print(Colors.BLUE);
		movies.forEach((title, movie) -> {
			int movieNumRatings = movie.getNumberRatings();
			String movieAvgRating = movieNumRatings > 0 ? String.valueOf(movie.getAverageRating()) : "N/A";
			System.out.printf("%-30s\t%s\t\t%s%n", title, movieAvgRating, movieNumRatings);
		});
		System.out.println(Colors.RESET + "\n");
	}
}
