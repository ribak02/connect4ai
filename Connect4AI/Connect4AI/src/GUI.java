import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI implements ActionListener {
	
	// instance variables
	private JFrame frame;
	private JPanel panel;
	
	private JButton[] buttons;
	private JLabel[][] slots;
	private ImageIcon iconBlue;
	private ImageIcon iconRed;
	private ImageIcon iconBlack;
	//private ImageIcon face1;
	//private ImageIcon face2;
	
	private OnePlayer c4;
	private int human = 1;
	private int ai = 2;
	
	// constructor
	public GUI() {
		
		// initializing variables
		frame = new JFrame("Connect 4"); 
		panel = new JPanel(new GridLayout(7,7));
		c4 = new OnePlayer();
		buttons = new JButton[7];
		slots = new JLabel[6][7];
		iconBlue = new ImageIcon("src/resources/downarrowblue.png");
		iconRed = new ImageIcon("src/resources/downarrowred.png");
		iconBlack = new ImageIcon("src/resources/downarrowblack.png");
		//face1 = new ImageIcon("src/resources/face1.png");
		//face2 = new ImageIcon("src/resources/face2.png");
		
		// buttons setup
		for (int i=0;i<7;i++) {
			buttons[i] = new JButton(iconRed);
			buttons[i].addActionListener(this);
			buttons[i].setPressedIcon(iconBlack);
			panel.add(buttons[i]);
		}
		
		// slots setup
		for (int row=0;row<6;row++) {
			for (int col=0;col<7;col++) {
				slots[row][col] = new JLabel(); 
				// new LineBorder(Color.black)
				slots[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.black));
				slots[row][col].setBackground(Color.green);
				panel.add(slots[row][col]);
			}
		}
		// panel setup
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createMatteBorder(0, 50, 50, 50, Color.white));
		
		// frame setup
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
		// set who goes first
		/*
		Object[] options = {"Human", "AI"};
		int pop = JOptionPane.showOptionDialog(frame, "Select who goes first", "Select an Option", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (pop == JOptionPane.YES_OPTION) {
			c4.setCurrentPlayer(human);
		}
		if (pop == JOptionPane.NO_OPTION) {
			c4.setCurrentPlayer(ai);
		}
		
		// if ai's turn do his turn
		if (c4.getCurrentPlayer() == ai && c4.checkWin() == 0) {
			c4.play();
			updateBoard();
			setButtonIcon();
		}
		*/
	}
	// changes icon color based on player turn
	public void setButtonIcon() {
		
		for (int i=0;i<7;i++) {
			if (c4.getCurrentPlayer() == 1) {
				buttons[i].setIcon(iconRed);
			}
			else {
				buttons[i].setIcon(iconBlue);
			}
		}
	}
	
	// match console board with GUI board method
	public void updateBoard() {
		
		for (int row=0;row<6;row++) {
			for (int col=0;col<7;col++) {
				if (c4.boardEquals(row, col, human)) {
					slots[row][col].setOpaque(true);
					slots[row][col].setBackground(Color.red);
					//slots[row][col].setIcon(face1);
				}
				else if (c4.boardEquals(row, col, ai)) {
					slots[row][col].setOpaque(true);
					slots[row][col].setBackground(Color.blue);
					//slots[row][col].setIcon(face2);
				}
			}
		}
		// pop up messages on win or tie
		int pop = -1;
		if (c4.checkWin() == 1) {
			pop = JOptionPane.showConfirmDialog(frame, "RED WINS, PLAY AGAIN?", "Select an Option",
					JOptionPane.YES_NO_OPTION);
		}
		if (c4.checkWin() == 2) {
			pop = JOptionPane.showConfirmDialog(frame, "BLUE WINS, PLAY AGAIN?", "Select an Option",
					JOptionPane.YES_NO_OPTION);
		}
		if(c4.checkWin() == -1) {
			pop = JOptionPane.showConfirmDialog(frame, "TIE, PLAY AGAIN?", "Select an Option",
					JOptionPane.YES_NO_OPTION);
		}
		// button event of YES
		if (pop == JOptionPane.YES_OPTION) {
			
			// resets board GUI
			for (int row=0;row<6;row++) {
				for (int col=0;col<7;col++) {
					if (c4.boardEquals(row, col, human)) {
						slots[row][col].setOpaque(false);	
						slots[row][col].setBackground(Color.white);
						// can reset icon here
					}
					else if (c4.boardEquals(row, col, ai)) {
						slots[row][col].setOpaque(false);
						slots[row][col].setBackground(Color.white);
						// can reset icon here
					}
				}
			}
			// resets game manager
			c4 = new OnePlayer();	
			
			c4.setCurrentPlayer(human);
			// resets who goes first
			/*
			Object[] options = {"Human", "AI"};
			int pop1 = JOptionPane.showOptionDialog(frame, "Select who goes first", "Select an Option", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (pop1 == JOptionPane.YES_OPTION) {
				c4.setCurrentPlayer(human);
			}
			if (pop1 == JOptionPane.NO_OPTION) {
				c4.setCurrentPlayer(ai);
			}
			
			// if ai's turn do his turn
			if (c4.getCurrentPlayer() == ai && c4.checkWin() == 0) {
				c4.play();
				updateBoard();
				setButtonIcon();
			}
			*/
		}
		// button event of NO
		if (pop == JOptionPane.NO_OPTION) {
			frame.dispose();
		}
	}
	
	// main method
	public static void main(String[] args) {
		new GUI();
	}

	// button actions
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (c4.checkWin() == 0) {
			
			if (e.getSource() == buttons[0]) {c4.butToCol(0);}
			if (e.getSource() == buttons[1]) {c4.butToCol(1);}
			if (e.getSource() == buttons[2]) {c4.butToCol(2);}
			if (e.getSource() == buttons[3]) {c4.butToCol(3);}
			if (e.getSource() == buttons[4]) {c4.butToCol(4);}
			if (e.getSource() == buttons[5]) {c4.butToCol(5);}
			if (e.getSource() == buttons[6]) {c4.butToCol(6);}
			c4.play();
			updateBoard();
			setButtonIcon();
			
			// ai's turn
			if (c4.checkWin() == 0) {
				System.out.println("AI going");
				c4.play();
				updateBoard();
				setButtonIcon();
			}
			
		}
	}
}
