package project3;
/**
 * The Inspection class represent a particular inspection of a restaurant. 
 * It stores the date of the inspection, the assigned score, the violation 
 * description and the risk category. 
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */
public class Inspection {
	private Date date;
	private int score;
	private String violation;
	private String risk;
	/**
	 * Constructs a restaurant's inspection.
	 * @param date of inspection
	 * @param score of inspection
	 * @param violation found in inspection
	 * @param risk of going to the restaurant
	 * @throws IllegalArgumentException when date parameter is null or score is the range [0, 100].
	 */
	public Inspection (Date date, int score, String violation, String risk) throws IllegalArgumentException {
		if (date == null || (score < 0 && score > 100)) {
			String message = "Invalid date or score or both.";
			throw new IllegalArgumentException(message);
		} else {
			setDate(date);
			setScore(score);
			setViolation(violation);
			setRisk(risk);
		}
	}
	/**
	 * Prints the inspection data for a given restaurant instance.
	 * If the risk and/or violation data is not given, the inspection would still be printed 
	 * with those spaces left blank. 
	 * @return the inspection data
	 */
	@Override
	public String toString () {
		if ( (risk == null || risk == "") || (violation == null || risk == "") ) {
			return score + ", " + date;
		}
		return score + ", " + date + ", " + risk + ", " + violation;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the violation
	 */
	public String getViolation() {
		return violation;
	}
	/**
	 * @param violation the violation to set
	 */
	public void setViolation(String violation) {
		this.violation = violation;
	}
	/**
	 * @return the risk
	 */
	public String getRisk() {
		return risk;
	}
	/**
	 * @param risk the risk to set
	 */
	public void setRisk(String risk) {
		this.risk = risk;
	}
}
