package pl.edu.pw.elka.stepien.pawel.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import pl.edu.pw.elka.stepien.pawel.constants.Constants;
import pl.edu.pw.elka.stepien.pawel.model.Model;
import pl.edu.pw.elka.stepien.pawel.view.View;
import pl.edu.pw.elka.stepien.pawel.exceptions.BoardsFileException;
import pl.edu.pw.elka.stepien.pawel.exceptions.WrongBoardModelException;

/** 
 * MVC Controller Class
 * 
 * @author Pawel Stepien 
 */
public class Controller {

	private Model model;
	private View view;
	private String[] boardsTemplates;
	private final BlockingQueue<String> blockingQueue;
	
	/**
     * Load boards templates from file from directory specified in Constant class. Check string correctness.
     * All the boards in string JSON format are stored in private string array boardsTemplates.
     */
	private void loadBoardFromFile() throws BoardsFileException{
		File file = new File(Constants.BOARDS_ADDRESS);
		if(!file.exists())
			throw new BoardsFileException("BoardsFileException: File does not exist at directory :"+Constants.BOARDS_ADDRESS);
		String content = null;
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);			    
			content = new String(chars);
			reader.close();
		} catch (IOException ex) {
			throw new BoardsFileException("BoardsFileException: Reader error :"+ex);
		}
		
		String temp[] = content.split("\n");
		Gson gson = new Gson();
		for(int i = 0; i < temp.length; ++i){
			if(temp[i].length() != 181)
				throw new BoardsFileException("BoardsFileException: Wrong data format");			
			try{
				int[][] value = new int[9][9];
				value = gson.fromJson(temp[i], int[][].class);
				//only to check if everything is initialized
				for(int a = 0; a<9; ++a){
					for(int b = 0; b<9; ++b){
						if(value[a][b] == 0)
							continue;
					}
				}
			}catch(JsonSyntaxException ex){
				throw new BoardsFileException("BoardsFileException: Wrong data format : JsonSyntaxException");
			}catch(ArrayIndexOutOfBoundsException ex){
				throw new BoardsFileException("BoardsFileException: Wrong data format : ArrayIndexOutOfBoundsException");
	        }
		}
		
		boardsTemplates = temp;
	}
	
	/**
     * Construct View and Model
     */
    public Controller()
    {
    	this.blockingQueue = new ArrayBlockingQueue<String>(10);
        this.model = new Model();
        this.view = new View(blockingQueue);
        try{
        	loadBoardFromFile();
        }catch(BoardsFileException ex){
        	ex.printStackTrace();
        	return;
        }
        sendRandBoard();
    }
    
    /**
     * Method to coordinate communication between View and Model
     */
    public void runGame(){
    	while(true)
        {
            try
            {
                String nextEvent = blockingQueue.take();
                react(nextEvent);
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Sends random board form boardsTemplates[] to model and to the view
     */
    public void sendRandBoard(){
    	Random generator = new Random();
    	int rand = generator.nextInt(boardsTemplates.length);
    	model.newGame(boardsTemplates[rand]);
    	view.newBoard(model.getJsonBoard());
    }
    
    /**
     * React to new event from the View
     * @param event in JSON format
     */
    public void react(String event){
    	if(event == "newgame"){
    		sendRandBoard();
    		return;
    	}
    	Gson gson = new Gson();
		int[] val = new int[3];
		try{
			val = gson.fromJson(event, int[].class);
		}catch(JsonSyntaxException ex){
			throw new WrongBoardModelException("WrongBoardModelInControllerException: "+ex);
		}
		boolean correct = model.setFieldValue(val[0], val[1], val[2]);
		boolean isEnd = false;
		if(correct)
			isEnd = model.isEnd();		
		view.putNumberToBtn(val[0], val[1], val[2], correct);
		if(isEnd)
			view.win();
		if(val[2] == 0)
			refreshView();
    }
    
    /**
     * We need to refresh board after every delete
     */
    public void refreshView(){
    	for(int a = 0; a<9; ++a){
			for(int b = 0; b<9; ++b){
				if(!model.isFieldChangeable(a, b))
					continue;
				if(model.getFieldValue(a, b) != 0){
					if(model.getBoard().isBoardCorrect(a, b))
						view.putNumberToBtn(a, b, model.getFieldValue(a, b), true);
				}
			}
		}
    }
}
