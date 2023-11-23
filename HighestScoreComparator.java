package project3;

import java.util.Comparator;

public class HighestScoreComparator implements Comparator<Restaurant>{

	@Override
	public int compare(Restaurant restaurant1, Restaurant restaurant2) {
		// would sort restaurants in descending order, from highest score to least
		return restaurant2.highestScore() - restaurant1.highestScore();
	}
}
