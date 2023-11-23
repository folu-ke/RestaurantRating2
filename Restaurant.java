package project3;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Restaurant Class represents restaurants. Stores the name of business; address of business;
 * phone number; zip code, and list of inspections. Most restaurants have more than one inspection records.
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */

public class Restaurant implements Comparable<Restaurant> {
	private String name, zip, address, phone;
	private ArrayList<Inspection> listInspection = new ArrayList<Inspection> ();
	/**
	 * This constructor sets the name and zip code of a restaurant.
	 * @param name
	 * @param zip
	 * @throws IllegalArgumentException if name is an empty string or 
	 * zip is not exactly five digits or address is an empty string or null or
	 * phone does not indicate a phone number.
	 */
	public Restaurant (String name, String zip) throws IllegalArgumentException {
		if (name == null || name.strip().isEmpty()) { 
			throw new IllegalArgumentException ("Name can not be empty string."); 
		}
		if (zip.length() != 5) { 
			throw new IllegalArgumentException ("Zip code must have five (5) digits."); 
		}
		this.name = name;
		this.zip = zip;
		
	}
	/**
	 * This constructor sets name and zip code of a restaurant using the first constructor.
	 * It also sets the address and phone number of a restaurant.
	 * @param address
	 * @param phone
	 * @throws IllegalArgumentException for same reason as 1st constructor
	 */
	public Restaurant(String name, String zip, String address, String phone) throws IllegalArgumentException {
		this(name, zip);
		if (phone != "") {
			if ( phone.length() != 11 ) {
				throw new IllegalArgumentException ("Phone number must have eleven (11) digits."); 
			}
		}
		this.address = address;
		this.phone = phone;
	}
	/**
	 * This method is used to add inspections to a restaurant instance.
	 * @param inspection 
	 * @return void
	 */
	public void addInspection(Inspection inspection) throws IllegalArgumentException {
		if (inspection == null) { throw new IllegalArgumentException("Inspection can not be null."); }
		listInspection.add(inspection); 

	}
	
	/**
	 * @return
	 */
	public int highestScore () {
		// sort inspections in order of earliest date to latest date
		Collections.sort( listInspection, new DateComparator() );
		// sort from largest inspection score to least
		Collections.sort( listInspection, new ScoreComparator() );
		// return highest inspection score of 
		return listInspection.get(0).getScore();
	}
	/**
	 * This method checks for the two earliest scored inspections of a restaurant and returns it as a string.
	 * @param ArrayList<Inspection> inspection for a restuarant's inspection records.
	 * @return two earliest scored inspections
	 */
	public String twoEarliestInspections(ArrayList<Inspection> inspection) {
		int size = inspection.size();
		String twoEarliestInspections = "";
		if ( size == 0 ) { return twoEarliestInspections; }
		else if (size == 1){
			if ( inspection.get(0).getScore() == 0 ) {
				return twoEarliestInspections;
			}
			return "\n" + inspection.get(0).toString(); }
		else {
			// sort inspections in order of earliest date to latest date
			Collections.sort( inspection, new DateComparator() );
			// sort from largest inspection score to least
			Collections.sort( inspection, new ScoreComparator() );
			// get earliest inspection
			Inspection earliestInspection = inspection.get(0);
			if ( earliestInspection.getScore() != 0 ) {
				// add to return string
	            twoEarliestInspections = "\n" +  earliestInspection;	
			}
            int i = 1;
            try {
	            // check for inspections with the same date as earliest inspection and add to return string
	            while ( (inspection.get(i).getDate().compareTo(earliestInspection.getDate() ) == 0) && i < size) {
	            	// do not add inspections with score == 0
	            	if ( inspection.get(i).getScore() != 0 ) {
	            		twoEarliestInspections += "\n" +  inspection.get(i);
	            	}
	                i++;
	            }
	            // add second earliest string
	            Inspection secondEarliestInspection = inspection.get(i);
	            // do not add inspections with score == 0
	            if ( secondEarliestInspection.getScore() != 0 ) {
	            	twoEarliestInspections += "\n" +  secondEarliestInspection ;
	            }
	            int j = i+1;
	            // check for inspections with the same date as second earliest inspection and add to return string
	            while ( (inspection.get(j).getDate().compareTo(secondEarliestInspection.getDate()) == 0)  && j < size) {
	            	// do not add inspections with score == 0
	            	if ( inspection.get(j).getScore() != 0 ) {
	            		twoEarliestInspections += "\n" +  inspection.get(j);
	            	}
	            	if (j == size -1 ) { return twoEarliestInspections; }
	                j++;
	            }
            } catch ( Exception e ) { return twoEarliestInspections; }
		}
		// return the strings 
        return twoEarliestInspections;
	}
		
	@Override
	/**
	 * Compares restuarants with name as primary comparator and zip as 
	 * secondary comparator according to natural String comparisons
	 * @param anotherRestaurant instance
	 */
	public int compareTo(Restaurant anotherRestaurant) {
		int nameComparison = this.name.compareToIgnoreCase(anotherRestaurant.getName());
		int zipComparison = this.zip.compareToIgnoreCase(anotherRestaurant.getZip());
		if (nameComparison == 0) { return zipComparison; }
		else { return nameComparison; }
	}
	@Override
	/**
	 * This method overrides the equals method. Two restaurants are equal when 
	 * both their names (ignoring case) and their zip codes are the same. 
	 * @param object to check with the Restaurant instance
	 * @return true if this Restaurant is the same as object or
	 *         false otherwise.
	 */
	public boolean equals (Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (!(object instanceof Restaurant))
			return false;
		Restaurant other = (Restaurant) object;
		if ( name.equalsIgnoreCase(other.name) &&  zip.equals(other.zip) ) {
			return true;
		}
		else { return false; }
	}
	@Override
	/**
	 * This function prints the restaurant object in this format:
	 * 
	 */
	public String toString () {
		String format =
		name + "\n" +
		"""
		-------------------------------
		address             :  """ + address + "\n" +
		"""
		zip                 :  """ + zip + "\n" +
		"""
		phone               : """ + phone + "\n" +
		"""
		recent inspection results: """ + 
		twoEarliestInspections(listInspection);
		
		return format;
	}
	/**
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return zip code/postal code of the restaurant
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @return address of the restaurant
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return phone number of the restaurant
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * This is a helper method used in debugging. The name and address distinguishes
	 * restaurants from each other. 
	 * @return name and address of a restaurant.
	 */
	public String getNameAndAddress() {
		return getName() + ", " + getAddress();
	}
	/**
	 * @return
	 */
	public Inspection getInspection() {
		return listInspection.get(0);
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
	
