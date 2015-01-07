package pl.edu.pw.elka.stepien.pawel.model.objects;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import pl.edu.pw.elka.stepien.pawel.exceptions.*;

/** 
 * Contains details about the board.
 * 
 * @author Pawel Stepien 
 */
public class Board {
	
	/** Field array*/
	private Field[][] field;
	
	/**
	 * Check collision only with specific field and only in row.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return false if there are not any collision or true if it found some.
	 */
	private boolean isCollisionInRow(int x, int y){
		//check if there is any collision in row
				for(int i = 0; i<9; ++i){
					if(y == i)
						continue;
					if(field[x][y].getValue() == field[x][i].getValue())
						return true;
				}
				return false;		
	}
	
	/**
	 * Check collision only with specific field and only in column.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return false if there are not any collision or true if it found some.
	 */
	private boolean isCollisionInColumn(int x, int y){
		//check if there is any collision in row
				for(int i = 0; i<9; ++i){
					if(x == i)
						continue;
					if(field[x][y].getValue() == field[i][y].getValue())
						return true;
				}
				return false;
	}
	
	/**
	 * Check collision only with specific field and only in column.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return false if there are not any collision or true if it found some.
	 */
	private boolean isCollisionInSquare(int x, int y){
		int row_down_lim, row_up_lim, col_down_lim, col_up_lim;
		
		if(x < 3){
			row_down_lim = 0;
			row_up_lim = 3;
		}else if(x < 6){
			row_down_lim = 3;
			row_up_lim = 6;
		}else{
			row_down_lim = 6;
			row_up_lim = 9;
		}
		
		if(y < 3){
			col_down_lim = 0;
			col_up_lim = 3;
		}else if(y < 6){
			col_down_lim = 3;
			col_up_lim = 6;
		}else{
			col_down_lim = 6;
			col_up_lim = 9;
		}
		
		for(int i = row_down_lim; i < row_up_lim; ++i){
			for(int j = col_down_lim; j < col_up_lim; ++j){
				if(x == i && y == j)
					continue;
				if(field[x][y].getValue() == field[i][j].getValue())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Board default constructor. Construct empty array[9][9] Field.
	 */
	public Board(){
		setFields(new Field[9][9]);
		for(int i = 0; i<9; ++i){
			for(int j = 0; j<9; ++j){
				field[i][j] = new Field();
			}
		}
		clearBoard();
	}
	
	/**
	 * Sets the value of the specific field.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @param value - integer between 1 -9, otherwise value will be set to 0
	 * 
	 * @exception ProhibitedOperationException The Field is not changeable.
	 */
	public void setValue(int x, int y, int value) throws ProhibitedOperationException{		
		if(isFieldChangeable(x, y)){
			field[x][y].setValue(value);
			return;
		}
		throw new ProhibitedOperationException("Prohibited operation: The Field is not changeable.");
	}
	
	/**
	 * Creates new board from string in JSON format.
	 * @param boardModel in JSON format, length should be 181 it can look for example:
	 * [[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9],[1,2,3,2,5,6,7,8,9]]
	 * in effect we will have in whole column the same numbers (1 column only "1" in 2 column only "2") and in each row numbers from 1 to 9
	 */
	public void newBoard(String boardModel) throws WrongBoardModelException{
		clearBoard();
		//check string size should be 181
		if(boardModel.length() != 181)
			throw new WrongBoardModelException("WrongBoardModelException: Wrong amount of characters");
		Gson gson = new Gson();
		int[][] val = new int[9][9];
		try{
			val = gson.fromJson(boardModel, int[][].class);
			//only to check if everything is initialized
			for(int a = 0; a<9; ++a){
				for(int b = 0; b<9; ++b){
					if(val[a][b] == 0)
						continue;
				}
			}
		}catch(JsonSyntaxException ex){
			throw new WrongBoardModelException("WrongBoardModelException: "+ex);
		}catch(ArrayIndexOutOfBoundsException ex){
			throw new WrongBoardModelException("WrongBoardModelException: "+ex);
        }
		for(int i = 0; i<9; ++i){
			for(int j = 0; j<9; ++j){
				field[i][j].setValue(val[i][j]);
				if(val[i][j] != 0)
					field[i][j].setChangeable(false);
			}
		}
	}
	
	/**
	 * @return string in JSON format containing 2 dimensional array with values of each field
	 */
	public String getJsonBoard(){
		Gson gson = new Gson();
		int[][] val = new int[9][9];
		for(int i = 0; i<9; ++i){
			for(int j = 0; j<9; ++j){
				val[i][j] = getFieldValue(i,j);
			}
		}
		String json = gson.toJson(val);
		return json;
	}
	
	/**
	 * Check if the specific field on the board is changeable.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return true if the field is changeable or false if not 
	 */
	public boolean isFieldChangeable(int x, int y){
		if(field[x][y].isChangeable())
			return true;
		return false;
	}

	/**
	 * Check collision on whole board.
	 * @return true if there are not any collision or false if it found some.
	 */
	public boolean isBoardCorrect(){
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				if(!isBoardCorrect(i,j))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Check collision only with specific field.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return true if there are not any collision or false if it found some.
	 * If value of the given field is default (0) then it will return true.
	 */
	public boolean isBoardCorrect(int x, int y){
		if(field[x][y].getValue() == 0)
			return true;
		if(isCollisionInRow(x,y) || isCollisionInColumn(x,y) || isCollisionInSquare(x,y))
			return false;
		
		return true;
	}
	
	/**
	 * Gets the value of the specific field.
	 * @param x - x coordinate of the field on the board (0-8)
	 * @param y - y coordinate of the field on the board (0-8)
	 * @return value of the field.
	 */
	public int getFieldValue(int x, int y){
		return field[x][y].getValue();
	}
	
	/**
	 * Check if the board is complete.
	 * @return true if all fields have got value between 1 - 9.
	 */
	public boolean isBoardComplete(){
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				if(getFieldValue(i,j) == 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Clear the board. Restore default values to all fields.
	 */
	public void fieldInitialize(){
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				field[i][j].setChangeable(true);
				field[i][j].setValue(0);
			}
		}
	}
	
	/**
	 * Clear the board. Restore default values to all fields.
	 */
	public void clearBoard(){
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				field[i][j].clear();
			}
		}
	}

	/** Set array of Fields */
	public void setFields(Field[][] field) {
		this.field = field;
	}
}
