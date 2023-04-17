package movie.Model;

public class Movie {
	private String title;
	private double totalRating;
	private int numberRatings;
	private double averageRating; 
	

	public void addRating(double rating) {
		if(totalRating < 0) totalRating = 0;
		totalRating += rating;
		numberRatings++;
		averageRating = totalRating / numberRatings;
	}
	
	public Movie (String title) {
		this.title = title;
		totalRating = -1; 
		numberRatings = 0;
		averageRating = 0;
	}
	
	public int getNumberRatings() {
		return numberRatings;
	}

	public void setNumberRatings(int numberRatings) {
		this.numberRatings = numberRatings;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getTotalRating() {
		return totalRating;
	}
	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}
	
	
}
