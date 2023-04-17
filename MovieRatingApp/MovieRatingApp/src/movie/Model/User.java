package movie.Model;

import java.util.HashMap;
import java.util.Map;

public class User {
	private String email;
	private String password;
	private Map<Movie, Double> userRatings;
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;		
		userRatings = new HashMap<Movie, Double>();
	}
	
	public void addRating(Movie movie, Double rating) {
		userRatings.put(movie, rating); //update USER's movie rating list
		movie.addRating(rating); //update GLOBAL movie data
	}
	
	public void printUserRatings() {
		System.out.printf("%-30s\t%s%n", "Title", "User Rating");
		System.out.print(movie.Utility.Colors.BLUE);
		userRatings.forEach((movie, rating) -> {
			String title = movie.getTitle();
			double userRating = rating;
			
			System.out.printf("%-30s\t%s%n", title, userRating);
		});
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", userRatings=" + userRatings + "]";
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Map<Movie, Double> getUserRatings() {
		return userRatings;
	}
}
