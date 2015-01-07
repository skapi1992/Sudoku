package pl.edu.pw.elka.stepien.pawel.model;

import pl.edu.pw.elka.stepien.pawel.model.objects.Board;
import pl.edu.pw.elka.stepien.pawel.exceptions.ProhibitedOperationException;
import pl.edu.pw.elka.stepien.pawel.exceptions.WrongBoardModelException;

/** 
 * MVC Model Class
 * 
 * @author Pawel Stepien 
 */
public class Model {
	private Board board;

	/** 
	 * Model constructor
	 */
	public Model(){
		board = new Board();
	}
	
	/** 
	 * Creates new game.
	 */
	public void newGame(String boardModel) throws WrongBoardModelException {
			board.newBoard(boardModel);
	}
	
	/**
	 * Sets the value of the specific field.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @param value - integer between 1 -9, otherwise value will be set to 0
	 * @return true if set value is not colliding with anything and false if is
	 * @exception ProhibitedOperationException The Field is not changeable.
	 */
	public boolean setFieldValue(int x, int y, int value) throws ProhibitedOperationException{
		board.setValue(x, y, value);
		if(board.isBoardCorrect(x, y))
			return true;
		return false;
	}
	
	/**
	 * Gets the value of the specific field.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return value of the field.
	 */
	public int getFieldValue(int x, int y){
		return board.getFieldValue(x, y);
	}
	
	/**
	 * Check if the specific field on the board is changeable.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return true if the field is changeable or false if not 
	 */
	public boolean isFieldChangeable(int x, int y){
		return board.isFieldChangeable(x, y);
	}
	
	/**
	 * @return string in JSON format containing 2 dimensional array with values of each field
	 */
	public String getJsonBoard(){
		return board.getJsonBoard();
	}
	
	/**
	 * Check if this is the end of the game.
	 * @return true if this is the end of the game, false if not
	 */
	public boolean isEnd(){
		if(board.isBoardComplete())
			if(board.isBoardCorrect())
				return true;
		return false;
	}
	
	/** 
	 * @return Board
	 */
	public Board getBoard() {
		return board;
	}

	/** Set Board */
	public void setBoard(Board board) {
		this.board = board;
	}
}
