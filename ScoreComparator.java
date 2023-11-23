package project3;
import java.util.Comparator;
/**
 * The ScoreComparator class is used to sort the Inspection objects in desceding order of scores. 
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */
public class ScoreComparator implements Comparator<Inspection>{
	@Override
	public int compare(Inspection in1, Inspection in2) {
		return in2.getScore() - in1.getScore();
		
	}
}