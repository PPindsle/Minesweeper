package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Cell {

	public static int cellSize = 30;
	public static int mineSize = 15;

	public int x;
	public int y;
	public int col;
	public int row;

	public boolean isRevealed = false;
	public boolean isMine = false;
	public boolean isFlagged = false;
	public boolean clickedMine = false;

	public float minePercentage = 0.15f;

	public Cell(int x, int y, int col, int row) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;

		Random rand = new Random();
		if (rand.nextFloat() < minePercentage) isMine = true;
	}

	public void draw(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 15));

		// border
		g.setColor(Color.decode("#9a9a9a"));
		g.drawRect(x, y, cellSize, cellSize);

		// draw white cell
//		g.setColor(Color.white);
//		g.fillRect(x, y, cellSize, cellSize);

		// draw cooler looking cell
		drawPrettierCell(g);

		if (isRevealed) {
			if (isMine) {
				// cell background
				if (clickedMine) g.setColor(Color.red);
				else g.setColor(Color.decode("#bdbdbd"));
				
				g.fillRect(this.x, this.y, cellSize, cellSize);

				// mine
				g.setColor(Color.black);
				g.fillOval(this.x + mineSize / 2, this.y + mineSize / 2, mineSize, mineSize);
			} else {
				g.setColor(Color.decode("#bdbdbd"));
				g.fillRect(this.x, this.y, cellSize, cellSize);

				// number of surrounding mines
				g.setColor(Color.black);
				g.drawString(getSurroundingMines(), this.x + cellSize / 2 - 5, this.y + cellSize / 2 + 5);
			}
		}
		if (isFlagged) {
			drawFlag(g);
		}
	}

	private void drawPrettierCell(Graphics g) {
		// cell
		g.setColor(Color.decode("#bdbdbd"));
		g.fillRect(x, y, cellSize, cellSize);

		g.setColor(Color.white);
		// top shadow
		int[] xPoints = { this.x, this.x + cellSize, this.x + cellSize - 3, this.x, this.x + 3 };
		int[] yPoints = { this.y, this.y, this.y + 3, this.y + 3, this.y + cellSize };
		g.fillPolygon(xPoints, yPoints, 4);

		// left shadow
		int[] xPoints2 = { this.x, this.x + 3, this.x + 3, this.x, this.x };
		int[] yPoints2 = { this.y, this.y + 3, this.y + cellSize - 3, this.y + cellSize, this.y };
		g.fillPolygon(xPoints2, yPoints2, 4);

		g.setColor(Color.decode("#7b7b7b"));
		// right shadow
		int[] xPoints3 = { this.x + cellSize, this.x + cellSize, this.x + cellSize - 3, this.x + cellSize - 3, this.x + 3 };
		int[] yPoints3 = { this.y, this.y + cellSize, this.y + cellSize, this.y + 3, this.y };
		g.fillPolygon(xPoints3, yPoints3, 4);

		// bottom shadow
		int[] xPoints4 = { this.x + cellSize, this.x + cellSize, this.x, this.x + 3, this.x + cellSize };
		int[] yPoints4 = { this.y + cellSize - 3, this.y + cellSize, this.y + cellSize, this.y + cellSize - 3, this.y };
		g.fillPolygon(xPoints4, yPoints4, 4);
	}

	private void drawFlag(Graphics g) {
		g.setColor(Color.red);
		g.drawLine(this.x + 15, this.y + 5, this.x + 15, this.y + 20);

		int[] xPoints5 = { this.x + 7, this.x + 16, this.x + 16, this.x + 7 };
		int[] yPoints5 = { this.y + 10, this.y + 5, this.y + 15, this.y + 10 };
		g.fillPolygon(xPoints5, yPoints5, 3);

		int[] xPoints6 = { this.x + 15, this.x + 20, this.x + 10, this.x + 15 };
		int[] yPoints6 = { this.y + 20, this.y + 25, this.y + 25, this.y + 20 };
		g.fillPolygon(xPoints6, yPoints6, 3);
	}

	public String getSurroundingMines() {
		String string = "";
		int count = 0;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (this.col + i >= 0 && this.col + i < Game.col && this.row + j >= 0 && this.row + j < Game.row) {
					if (Game.cell[this.col + i][this.row + j].isMine) {
						count++;
					}
				}
			}
		}
		if (count != 0) string += count;
		return string;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
