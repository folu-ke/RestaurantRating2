package project3;
import java.util.Comparator;
/**
 * The DateComparator class is used to sort the Inspection objects from earliest dates to latest dates. 
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */
public class DateComparator implements Comparator<Inspection>{
	/**
	 * Compares two inspections according to their dates using the 
	 * Date class's compareTo() method.
	 * @returns int 0, 1, or -1 according to Date class compareTo().
	 * @see compareTo() in Date.java.
	 */
	@Override
	public int compare(Inspection in1, Inspection in2) {
		return in2.getDate().compareTo( in1.getDate() );
	}
	
}

