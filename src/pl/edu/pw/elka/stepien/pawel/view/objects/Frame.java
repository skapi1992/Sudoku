package pl.edu.pw.elka.stepien.pawel.view.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.edu.pw.elka.stepien.pawel.exceptions.WrongBoardModelException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/** 
 * Swing frame which will be displayed
 * 
 * @author Pawel Stepien 
 */
public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton[][] button;
	private JButton newGame;
	private BtnCoordinates activeBtn;
		
	/**
	 * Construct all the buttons and generally frame with buttons and listeners
	 */
	public Frame(final BlockingQueue<String> blockingQueue) {		
		super("Sudoku");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(460, 560);
		setLocation(300,200);
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(new BorderLayout());
		right.setPreferredSize(new Dimension(460, 460));
		right.setLayout(new GridLayout(3,3));		
        add(left, BorderLayout.PAGE_END);
        add(right, BorderLayout.PAGE_START);
        
        //create 9 JPanel containers for buttons
        JPanel container[][] = new JPanel[3][3];
        for(int i = 0; i<3; ++i){
			for(int j = 0; j<3; ++j){
				container[i][j] = new JPanel();
				container[i][j].setPreferredSize(new Dimension(150, 150));
				container[i][j].setLayout(new GridLayout(3,3));
				container[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				right.add(container[i][j]);
			}
        }
        //creating the board
        button = new JButton[9][9];
        for(int i = 0; i<3; ++i){
			for(int j = 0; j<3; ++j){
				for(int a = 0; a<3; ++a){
					for(int b = 0; b<3; ++b){
						button[a + 3*i][b + 3*j] = new JButton();
						button[a + 3*i][b + 3*j].setBackground(Color.WHITE);
						button[a + 3*i][b + 3*j].setFont(new Font("Arial", Font.BOLD, 25));
						button[a + 3*i][b + 3*j].setFocusPainted(false);
						button[a + 3*i][b + 3*j].setActionCommand(Integer.toString(a + 3*i)+";"+Integer.toString(b + 3*j));
						button[a + 3*i][b + 3*j].addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent e) {
				            	String[] part = e.getActionCommand().split(";");
				            	btnActivate(new BtnCoordinates(Integer.parseInt(part[0]), Integer.parseInt(part[1])));
				            }
				        });
						container[i][j].add(button[a + 3*i][b + 3*j]);
					}
				}
			}
        }
        
        left.setPreferredSize(new Dimension(460, 70));
        newGame = new JButton("New Game");
        newGame.setPreferredSize(new Dimension(460, 70));
        newGame.setBackground(Color.LIGHT_GRAY);
        newGame.setActionCommand("newgame");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	putToBlockingQueue("newgame", blockingQueue);
            }
        });
        left.add(newGame, BorderLayout.PAGE_START);
		setVisible(true);
		
		activeBtn = new BtnCoordinates(0,0);
		btnActivate(activeBtn);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getID() == KeyEvent.KEY_PRESSED) {
	            	if(e.getKeyCode() == KeyEvent.VK_DELETE)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 0, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_1)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 1, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_2)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 2, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_3)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 3, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_4)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 4, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_5)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 5, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_6)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 6, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_7)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 7, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_8)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 8, blockingQueue);
			    	else if(e.getKeyCode() == KeyEvent.VK_9)
			    		putToBlockingQueue(activeBtn.getX(), activeBtn.getY(), 9, blockingQueue);
	            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
	            } else if (e.getID() == KeyEvent.KEY_TYPED) {
	            }
	            return false;
	        }
		});
	}
	
	/**
	 * Activate button after click, and deactivate last active button
	 * @param c button coordinates
	 */
	public void btnActivate(BtnCoordinates c){
		btnDeactivate(activeBtn);
		button[c.getX()][c.getY()].setBackground(Color.YELLOW);
		activeBtn.setX(c.getX());
		activeBtn.setY(c.getY());
	}
	
	/**
	 * Deactivate button
	 * @param c button coordinates
	 */
	public void btnDeactivate(BtnCoordinates c){
		button[c.getX()][c.getY()].setBackground(Color.WHITE);
	}
	
	/**
	 * Set text to the button
	 * @param c button coordinates
	 * @param number text which should be set
	 * @param correct if true text color will be green if false red
	 */
	public void putNumberToBtn(BtnCoordinates c, int number, boolean correct){
		if(correct)
			button[c.getX()][c.getY()].setForeground(Color.GREEN);
		else
			button[c.getX()][c.getY()].setForeground(Color.RED);
		
		if(number != 0)
			button[c.getX()][c.getY()].setText(Integer.toString(number));
		else
			button[c.getX()][c.getY()].setText("");
	}
	
	/**
	 * Sets all the buttons using json template
	 * @param json template
	 */
	public void newBoard(String json){
		Gson gson = new Gson();
		int[][] val = new int[9][9];
		try{
			val = gson.fromJson(json, int[][].class);
		}catch(JsonSyntaxException ex){
			throw new WrongBoardModelException("WrongBoardModelInViewException: "+ex);
		}catch(ArrayIndexOutOfBoundsException ex){
			throw new WrongBoardModelException("WrongBoardModelInViewException: "+ex);
        }
		boolean foundActiveBtn = false;
		for(int i = 0; i<9; ++i){
			for(int j = 0; j<9; ++j){
				if(val[i][j] != 0){
					button[i][j].setForeground(Color.BLACK);
					button[i][j].setText(Integer.toString(val[i][j]));
					button[i][j].setEnabled(false);
				}else{
					button[i][j].setEnabled(true);
					button[i][j].setText("");
					if(!foundActiveBtn){
						btnActivate(new BtnCoordinates(i,j));
						foundActiveBtn = true;
					}
				}
			}
		}
	}
	
	/**
	 * Convert 3 integers into array a[] convert into JSON and put into blocking queue
	 * @param x coordinate of active button
	 * @param y coordinate of active button
	 * @param number number which should be put into the button
	 * @param blockingQueue
	 */
	public void putToBlockingQueue(int x, int y, int number, final BlockingQueue<String> blockingQueue){
		Gson gson = new Gson();
		int[] val = new int[3];
		val[0] = x;
		val[1] = y;
		val[2] = number;
		String json = gson.toJson(val);
		try {
			blockingQueue.put(json);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Put string directly to blocking queue
	 * @param info
	 * @param blockingQueue
	 */
	public void putToBlockingQueue(String info, final BlockingQueue<String> blockingQueue){
		try {
			blockingQueue.put(info);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
