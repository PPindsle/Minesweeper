package minesweeper;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	public static int col;
	public static int row;

	public static Cell[][] cell;

	private boolean running = true;
	private boolean firstMove = true;

	public Game(int c, int r) {
		col = c;
		row = r;
		addMouseListener(this);
		generateCells();
	}

	public void paint(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 12));

		if (win()) {
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Winner!", 120, 30);
		} else {
			g.setColor(Window.frame.getBackground());
			g.fillRect(115, 10, 80, 50);
		}

		// cells
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				cell[i][j].draw((Graphics2D) g);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// position of mouse cursor
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (running) {
			for (int i = 0; i < col; i++) {
				for (int j = 0; j < row; j++) {
					Cell c = cell[i][j];
					if (isWithinCol(mouseX, i, j) && mouseWithinRow(mouseY, i, j)) {
						// left click
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (!c.isFlagged) {
								// reveal cell(s)
								floodFill(i, j);
								if (c.isMine) {
									if (firstMove) {
										restart();
										mouseClicked(e);
									} else {
										c.clickedMine = true;
										gameOver();
									}
								} else {
									firstMove = false;
									if (win()) {
										running = false;
									}
								}
							}
						}
						// right click
						else if (e.getButton() == MouseEvent.BUTTON3) {
							if (!c.isFlagged && !c.isRevealed) {
								c.isFlagged = true;
								if (c.isMine) {
									if (win()) {
										running = false;
									}
								}
							} else if (c.isFlagged && !c.isRevealed) {
								c.isFlagged = false;
							}
						}
					}
				}
			}
		}
		if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 40) {
			restart();
		}
		repaint();
	}

	public static void floodFill(int x, int y) {
		Cell c = cell[x][y];
		if (c.getSurroundingMines() == "" && !c.isRevealed && !c.isMine && !c.isFlagged) {
			c.isRevealed = true;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (x + i < col && x + i >= 0 && y + j >= 0 && y + j < row) {
						floodFill(x + i, y + j);
					}
				}
			}
		} else {
			if (!c.isFlagged) {
				c.isRevealed = true;
			}
		}
	}

	private boolean win() {
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				Cell c = cell[i][j];
				if ((c.isMine && !c.isFlagged) || (!c.isMine && c.isFlagged) || (!c.isMine && !c.isRevealed)) {
					return false;
				}
			}
		}
		return true;
	}

	private void gameOver() {
		running = false;
		revealMines();
	}

	private void restart() {
		generateCells();
		running = true;
		firstMove = true;
	}

	private void revealMines() {
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				Cell c = cell[i][j];
				if (c.isMine) {
					c.isRevealed = true;
				}
			}
		}
	}

	private void generateCells() {
		cell = new Cell[col][row];
		int x = 0;
		int y = 50;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				cell[i][j] = new Cell(x, y, i, j);
				y += Cell.cellSize + 1;
			}
			y = 50;
			x += Cell.cellSize + 1;
		}
	}

	private boolean isWithinCol(int mouseX, int i, int j) {
		Cell c = cell[i][j];
		return mouseX > c.getX() && mouseX < c.getX() + Cell.cellSize;
	}

	private boolean mouseWithinRow(int mouseY, int i, int j) {
		Cell c = cell[i][j];
		return mouseY > c.getY() && mouseY < c.getY() + Cell.cellSize;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
