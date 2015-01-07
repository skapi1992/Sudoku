package pl.edu.pw.elka.stepien.pawel.view;

import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import pl.edu.pw.elka.stepien.pawel.view.objects.BtnCoordinates;
import pl.edu.pw.elka.stepien.pawel.view.objects.Frame;

/** 
 * MVC View Class
 * 
 * @author Pawel Stepien 
 */
public class View {
	
	private Frame frame;
	
	/**
	 * View class constructor
	 */
	public View(final BlockingQueue<String> blockingQueue){
		frame = new Frame(blockingQueue);
	}
	
	/**
	 * Put the number onto the board.
	 * @param x coordinates on the board
	 * @param y coordinates on the board
	 * @param number which should be put
	 * @param correct if the number has collision with something on the board or not
	 */
	public void putNumberToBtn(int x, int y, int number, boolean correct){
		frame.putNumberToBtn(new BtnCoordinates(x,y), number, correct);
	}
	
	/**
	 * Generate new board
	 * @param json board model
	 */
	public void newBoard(String json){
		frame.newBoard(json);
	}
	
	public void win() {
		JOptionPane.showMessageDialog(frame,"Congratulation! You win!");
	}
}
