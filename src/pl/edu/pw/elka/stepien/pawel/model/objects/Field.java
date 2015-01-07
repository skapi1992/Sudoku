package pl.edu.pw.elka.stepien.pawel.model.objects;

/** 
 * Contains details about each field on the board.
 * 
 * @author Pawel Stepien 
 */
public class Field {
	
	/** if field is changeable */
	private boolean changeable = true;
	/** value of the field, can be between 1 - 9 or 0 if not set */
	private int value = 0;
	
	/** 
	 * @return true if the field is changeable or false if not 
	 */
	public boolean isChangeable() {
		return changeable;
	}
	
	/** Set if the field is changeable or not */
	public void setChangeable(boolean changeable) {
		this.changeable = changeable;
	}
	
	/** 
	 * @return value of the field
	 */
	public int getValue() {
		return value;
	}

	/** 
	 * Set value of the field. Value should be between 1 -9 if not then it will be set to 0.
	 */
	public void setValue(int value) {
		if(value > 0 && value < 10)
			this.value = value;
		else
			this.value = 0;
	}
	
	/** 
	 * Restore default values to the field.
	 */
	public void clear(){
		this.setChangeable(true);
		this.setValue(0);
	}
}
