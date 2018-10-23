package minesweeper;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window {

	public static JFrame frame;
	public static int width;
	public static int height;

	public static void main(String[] args) {
		int col = 20;
		int row = 20;
		
		width = col * (Cell.cellSize + 1);
		height = row * (Cell.cellSize + 1) + 50;
		
		Game minesweeper = new Game(col, row);
		
		frame = new JFrame();
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setTitle("Minesweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(minesweeper);

		JButton button = new JButton("Restart");
		button.setBounds(10, 10, 100, 30);
		frame.add(button);
	}

}
