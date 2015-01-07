package pl.edu.pw.elka.stepien.pawel.exceptions;

/**
 * When some operations try to do something which is against the game logical concept.
 * 
 * @author Pawel Stepien 
 */
public class ProhibitedOperationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProhibitedOperationException(){
		super("Prohibited operation");
	}
	
	public ProhibitedOperationException(String message){
		super(message);
	}
}
