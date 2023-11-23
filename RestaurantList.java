package project3;
import java.util.*;
/**
 * The RestaurantList class stores all the valid Restaurant objects. 
 * Acts like a restaurant database.
 * InheritS from the ArrayList< Restaurant> class
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */

public class RestaurantList extends LinkedList<Restaurant> {
	/**
	 * 
	 */
	public RestaurantList () {}
	/**
	 * @param keyword should be a restaurant name that is in this RestaurantList
	 * @return list of Restaurant objects whose names contain 
	 * the keyword as a substring (case insensitive).
	 */
	public RestaurantList getMatchingRestaurants ( String keyword ) {
		if ( keyword == null || keyword.equals("") ) {
			return null;
		}
		RestaurantList matchingName = new RestaurantList();
		for ( Restaurant each : this ) {
			// check for substring
			if ( each.getName().toUpperCase().contains(keyword.toUpperCase()) ) {
				matchingName.add( each );
			}
		}
		if ( matchingName.isEmpty() ) { return null; }
		// sort in alphabetical order
		//Collections.sort(matchingName);
		return matchingName;
	}
	/**
	 * @param keyword should be a valid zip code in San Francisco
	 * @return list of top three Restaurant objects whose zip codes are equal to the keyword
	 */
	public RestaurantList getMatchingZip ( String keyword ) {
		if ( keyword == null || keyword.equals("") ) {
			return null;
		}
		RestaurantList matchingZip = new RestaurantList();
		for (Restaurant each : this ) {
			if ( each.getZip().equals(keyword) ) {
				matchingZip.add( each );
			}
		}
		if ( matchingZip.isEmpty() ) { return null; }
		// sort in alphabetical order
		Collections.sort(matchingZip);
		if (matchingZip.size() <= 3) {
			return matchingZip;
		}
		else {
			// add top three zip matches to list
			RestaurantList topThree = new RestaurantList();
			topThree.add(matchingZip.get(0));
			topThree.add(matchingZip.get(1));
			topThree.add(matchingZip.get(2));
			return topThree;
		}
		
	}
	/*
	 * @return returnString restaurants with "; " after them except the last restaurant
	 */
	@Override
	public String toString() {
		Iterator<Restaurant> iter = this.iterator();
		String returnString = "";
		// if there is at least one restaurant in list
		while (iter.hasNext()) {
			// would not return ";" with the restaurant name
			if ( !(iter.hasNext()) ) 
				returnString += iter.next().getName();
			// else keep returning restaurant names and the ";"
			returnString += iter.next().getName() + "; ";
		}
		// return empty string if list is empty
		return returnString;
	}
}
