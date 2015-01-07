package pl.edu.pw.elka.stepien.pawel.exceptions;

public class WrongBoardModelException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public WrongBoardModelException(){
		super("Wrong board model exception");
	}
	
	public WrongBoardModelException(String message){
		super(message);
	}
}
