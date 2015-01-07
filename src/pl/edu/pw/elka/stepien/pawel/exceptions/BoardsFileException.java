package pl.edu.pw.elka.stepien.pawel.exceptions;

/**
 * When there is some problem with containing boards templates file.
 * 
 * @author Pawel Stepien 
 */
public class BoardsFileException extends RuntimeException{
	
private static final long serialVersionUID = 1L;
	
	public BoardsFileException(){
		super("BoardsFileException");
	}
	
	public BoardsFileException(String message){
		super(message);
	}
}
