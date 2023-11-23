package project3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * This is the main program. It contains the main method.  
 * It is responsible for opening and reading the csv file, 
 * obtains user input, performs data validation and handles errors that may occur.
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */
public class SFRestaurantData {
	/**
	 * The main method continues in a loop till the user quits the program.
	 * Reads the user input and prints out the corresponding output as far the user input is valid.
	 * When the user input is not valid, the program asks the user for a proper output.
	 * Based on the user input, the method compares the content with the names of valid restaurants 
	 * in the restaurant database and prints each matched restaurants to the user. Then asks the user for a new search.
	 * The query contains till the user quits the program by the input "quit".
	 * 
	 */
	public static void main(String[] args) {
		RestaurantList availableRestaurants = readCSVFile();
		Scanner userInput  = new Scanner (System.in ); 
		String userValue = "";
		System.out.println("          THE SAN FRANCISCO RESTAURANT RATING APP     \n\n"
						 + "Search the database by matching keywords to titles or actor names.\n"
				         + "  To search for matching restaurant names, enter\n"
                         + "    name KEYWORD\n"
				         + "  To search for restaurants in a zip code, enter\n"
                         + "    zip KEYWORD\n"
                         + "  To finish the program, enter\n"
                         + "    quit\n" );
		// constant loop to collect input and give correct output
		do {
			System.out.println("Enter your search query or \"quit\" to stop:\n" );
			// these lists save the restaurants matched with the user input
			RestaurantList matchedNames = new RestaurantList();
			RestaurantList matchedZips = new RestaurantList();
			try {
				//get value from the user 
				userValue = userInput.nextLine();
				// while user input is not "quit"
				if (!userValue.equalsIgnoreCase("quit")) { 
					String[] userValues = userValue.split(" ");
					// name or zip
					String nameOrZip = userValues[0]; 
					// restaurant name or zip code query by user
					String content = userValues[1];
					// check if user query is by name
					if ( nameOrZip.equalsIgnoreCase("name") ) {
						// restaurants where content is a substring 
						matchedNames = availableRestaurants.getMatchingRestaurants(content);
						for ( Restaurant restaurants : matchedNames ) {
							// print out each restaurant
							System.out.println(restaurants);
							System.out.println();
						}
					}
					// check if user query is by zip
					else if ( nameOrZip.equalsIgnoreCase("zip") ) {
						// restaurants whose zip is eqaul to content 
						matchedZips = availableRestaurants.getMatchingZip(content);
						for ( Restaurant restaurants : matchedZips ) {
							// print out each reestaurant
							System.out.println(restaurants);
							System.out.println();
						}
					}
					// if user query does not match name ... or zip ...
					else {
						System.out.println("This is not a valid query. Try again.");	
					}
				} 	
				// user quits program : Goodbye message:
				else {
					System.out.println("\nTHANKS FOR USING SAN FRANCISCO RESTAURANT RATING APP");
					System.out.println("            >>>> GOODBYE! <<<<             ");
				}
				// if user query is not found in restaurant database
			} catch (Exception e) {
				System.out.println("No matches found. Try again.\n\n");				
			}
			// keep looping till user quits
		} while (!userValue.equalsIgnoreCase("quit")); 
		userInput.close();  
	}	
	/**
	 * Reads the CSV file and calls a split function here to parse through each line of the csv file.
	 * For each parsed line(restaurant from csv file), the name, zip and inspection date is stored. A restaurant 
	 * is only valid if it has a name, zip code and inspection date. Violation and risk are not qualifiers for 
	 * restaurants. Based on csv file, each part of a restaurant object is accessed directed by index.
	 * @return a RestaurantList that is the restaurant database that works on the backend.
	 */
	public static RestaurantList readCSVFile() {
		// get the csv file with all the restaurants
		File csvFile = new File("src/SF_restaurant_scores_full.csv");
		// checks if it exists
		if (!csvFile.exists()){
			System.err.println("Error: the file "+csvFile.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		// check if readable
		if (!csvFile.canRead()){
			System.err.println("Error: the file "+csvFile.getAbsolutePath()+ " cannot be opened for reading.\n");
			System.exit(1);
		}
		//open the file for reading 
		Scanner inCsv = null;			
		try {
			inCsv = new Scanner (csvFile ) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+csvFile.getAbsolutePath()+" cannot be opened for reading.\n");
			System.exit(1);
		}
		//read the content of the file 
		HashMap <String, String> nullRestaurants = new HashMap<String, String> ();
		String line = null; 
		Scanner parseLine = null;
		RestaurantList restaurants;
		// restaurant database : RestaurantList where valid restaurants are stored
		restaurants = new RestaurantList();
		Restaurant thisRestaurant = null;
		// skip first line
		inCsv.nextLine();
		String restaurantName = "";
		String restaurantZip = "";
		// loop to keep reading file
		while (inCsv.hasNextLine()) {
			try {
				// get each line of csv file
				line = inCsv.nextLine(); 
				parseLine = new Scanner(line);
				// splitCSV(..) returns an array list of each restaurant 
				ArrayList<String> restaurant = splitCSVLine(line);
				// based on csv, restaurant name is at index 1
				restaurantName = restaurant.get(1);
				// based on csv, restaurant zip is at index 5
				restaurantZip = restaurant.get(5).split("-")[0];
				// based on csv, inspection date is at index 11
				String date = restaurant.get(11).replaceAll(" .*","");
				// make into a Date object
				Date inspectionDate = new Date(date);
				// based on csv, inspection score is at index 12
				String inspectionScoreString = restaurant.get(12);
				// assign inspection score as 0 
				int inspectionScore = 0;
				// if a score exists, assign inspectionScore to it
				if ( !(inspectionScoreString.equals("")) ) {
					inspectionScore = Integer.parseInt( restaurant.get(12) );
				}
				// based on csv, inspection violation is at index 15
				String inspectionViolation = restaurant.get(15);
				String inspectionRisk = "";
				// if there is no violation, there cannot be any risk
				if ( !(inspectionViolation.equals("")) ) {
					inspectionRisk = restaurant.get(16);
				 }
				// create an Inspection object based on parsed data
				Inspection restuarantInspection = new Inspection( inspectionDate, inspectionScore, inspectionViolation, inspectionRisk);
				// based on csv, inspection address is at index 2
				String restaurantAddress = restaurant.get(2);
				String restaurantPhone = "";
				restaurantPhone = restaurant.get(9);
				// then create restaurant object
				thisRestaurant = new Restaurant( restaurantName, restaurantZip, restaurantAddress, restaurantPhone );
				thisRestaurant.addInspection(restuarantInspection);
				// we add inspections to already existing restaurants to avoid duplicates
		        int index = restaurants.indexOf(thisRestaurant);
		        if ( thisRestaurant != null ) {
		        	// restaurant object is already in restaurantList
		        	if ( index != -1 ) {
		        		// add new inspections for existing restaurants
		        		restaurants.get(index).addInspection( thisRestaurant.getInspection() ); 
		        	}
		        	else { 
		        		// append new restaurant to database 
		        		restaurants.add(thisRestaurant);
		        	}		
		        }	
			}
			catch (Exception ex ) {
				// store invalid restuarants
				nullRestaurants.put(restaurantName, ex.getMessage() );
				continue; 	
			}
		}
		return restaurants;
	}
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author CSV
	 * @param textLine a line of text to be passed
	 * @return an ArrayList object containing all individual entries found on that line 
	 */
	 public static ArrayList<String> splitCSVLine(String textLine) {

		if (textLine == null)
			return null;

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry = false;
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			// handle smart quotes as well as regular quotes
			// what does this mean?
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} else {
					insideQuotes = true;
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if (insideQuotes || insideEntry) {
					// add it to the current entry
					nextWord.append(nextChar);
				} else { // skip all spaces between entries
					continue;
				}
			} else if (nextChar == ',') {
				if (insideQuotes) { // comma inside an entry
					nextWord.append(nextChar);
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}
		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
		return entries;
	 }	  



}
	 
