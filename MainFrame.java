import CheckerBoard.*;
import CheckerPiece.*;
import Exceptions.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.*;



public class MainFrame extends JFrame
implements MouseListener
{
	CheckerBoard board;
	Boolean pieceSelected;
	Point selectedPoint1;
	Point flashingPoint1;
	Point flashingPoint2;
	Point flashingPoint3;
	Point flashingPoint4;
	Boolean redMove;
	Boolean doubleJump;
	
	public MainFrame(String s)
	{
		super(s);
		addMouseListener(this);
		board = new CheckerBoard(2);
		paintmethod();
		pieceSelected = new Boolean(false);
		redMove = new Boolean(true);
		doubleJump = new Boolean(false);
	}
	

	public static void main(String[] args)
	{
		MainFrame f = new MainFrame("Welcome!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setPreferredSize(new Dimension(600, 600));
		
		//f.add(new JButton());
		
		f.setResizable(false);
		
		f.pack();
		
		f.setVisible(true);
	}
	
	public void paintmethod()
	{
		repaint();
	}
	
	public void paint(Graphics g)
	{
		Dimension dim = getSize();
		int dimension = dim.width > dim.height ? dim.height : dim.width;
		int circleRad = 50;
		int adjust = 70 / 2 - circleRad / 2;
		
		//clear the board
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, dim.width, dim.height);
		
		g2.setColor(Color.BLACK);
		
		//construct the board
		Line2D line = new Line2D.Double();
		Line2D line2 = new Line2D.Double();
		for (int i = 0; i < 8; i++)
		{
			//draw the checkerboard
			//vertical lines
			//line.setLine(20 + 80*i, 590, 20 + 80 * i, 30);
			//horizontal lines
			/*line2.setLine(20, 30 + 80*i, 580, 30+80*i);
			g2.draw(line);
			g2.draw(line2);*/
			//fill in the rectangles
			if(i%2 == 0)
			{
				g2.fillRect(20, 30 + 70*i, 70, 70);
				
				g2.fillRect(160, 30 + 70*i, 70, 70);
				
				g2.fillRect(300, 30 + 70*i, 70, 70);
				
				g2.fillRect(440, 30 + 70*i, 70, 70);
			}
			else
			{
				g2.fillRect(90, 30 + 70*i, 70, 70);
				g2.fillRect(230, 30 + 70*i, 70, 70);
				g2.fillRect(370, 30 + 70*i, 70, 70);
				g2.fillRect(510, 30 + 70 * i, 70, 70);
			}
		}
		
		//draw board
		g2.drawLine(20, 30, 20, 590);
		g2.drawLine(20, 30, 580, 30);
		g2.drawLine(580, 30, 580, 590);
		g2.drawLine(580, 590, 20, 590);
		
		g2.setColor(Color.YELLOW);
		//draw "flashing squares"
		if (flashingPoint1 != null)
			g2.fillRect(flashingPoint1.x * 70 + 20, (7-flashingPoint1.y) * 70 + 30, 70, 70);
		if (flashingPoint2 != null)
			g2.fillRect(flashingPoint2.x * 70 + 20, (7-flashingPoint2.y) * 70 + 30, 70, 70);
		if (flashingPoint3 != null)
			g2.fillRect(flashingPoint3.x * 70 + 20, (7-flashingPoint3.y) * 70 + 30, 70, 70);
		if (flashingPoint4 != null)
			g2.fillRect(flashingPoint4.x * 70 + 20, (7-flashingPoint4.y) * 70 + 30, 70, 70);
		
		CheckerPiece temp;
		boolean noBlack;
		boolean noRed;
		
		noBlack = true;
		noRed = true;
		
		//draw pieces
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if ((temp = board.getRedPiece(i, j)) != null)
				{
					g2.setColor(Color.RED);
					g2.fillOval(20 + 70 * i + adjust, 30 + 70 * (7-j) + adjust, circleRad, circleRad);
					if (temp.isKing())
					{
						int centerx = 20 + 70 * i + 35;
						int centery = 30 + 70 * (7-j) + 35;
						int[] xPoints = {centerx - 12, centerx - 6, centerx, centerx +6, centerx + 12, centerx +8, centerx -8};
						int [] yPoints = { centery - 12, centery - 6, centery - 12, centery - 6, centery - 12, centery, centery};
						int nPoints = 7;
						g2.setColor(Color.YELLOW);
						g2.fillPolygon(xPoints, yPoints, nPoints);
					}
					noRed = false;
				}
				if ((temp = board.getBlackPiece(i, j)) != null)
				{
					g2.setColor(Color.BLUE);
					g2.fillOval(20 + 70 * i + adjust, 30 + 70 * (7-j) + adjust, circleRad, circleRad);
					if (temp.isKing())
					{
						int centerx = 20 + 70 * i + 35;
						int centery = 30 + 70 * (7-j) + 35;
						int[] xPoints = {centerx - 12, centerx - 6, centerx, centerx +6, centerx + 12, centerx +8, centerx -8};
						int [] yPoints = { centery - 12, centery - 6, centery - 12, centery - 6, centery - 12, centery, centery};
						int nPoints = 7;
						g2.setColor(Color.YELLOW);
						g2.fillPolygon(xPoints, yPoints, nPoints);
					}
					noBlack = false;
				}
			}
		}
		if (noBlack)
		{
			int playagain = JOptionPane.showConfirmDialog(null, "Black has Lost! Play again?", 
					"Checkers", JOptionPane.YES_NO_OPTION);
			if (playagain == JOptionPane.YES_OPTION)
			{
				board = new CheckerBoard(2);
				repaint();
				return;
			}
			else
			{
				System.exit(0);
			}	
		}
		if (noRed)
		{
			int playagain = JOptionPane.showConfirmDialog(null, "Black has Lost! Play again?", 
					"Checkers", JOptionPane.YES_NO_OPTION);
			if (playagain == JOptionPane.YES_OPTION)
			{
				board = new CheckerBoard(2);
				repaint();
				return;
			}
			else
			{
				System.exit(0);
			}	
		}
	}

	public boolean redDoubleJump(Point p)
	{
		
		boolean canJump;
		canJump = false;
		
		if (board.hasBlackPiece(p.x + 1, p.y + 1) && p.x != 7 && p.y != 7)
		{
			if (!board.hasPiece(p.x + 2, p.y + 2) && p.x != 6 && p.y != 6)
			{
				flashingPoint1 = new Point(p.x+2, p.y+2);
				canJump = true;
			}
			else
				flashingPoint1 = null;
		}
		else
			flashingPoint1 = null;
		if (board.hasBlackPiece(p.x - 1, p.y + 1) && p.x != 0 && p.y != 7)
		{
			if (!board.hasPiece(p.x - 2, p.y + 2) && p.x != 1 && p.y != 6)
			{
				flashingPoint2 = new Point(p.x-2, p.y+2);
				canJump = true;
			}
			else
				flashingPoint2 = null;
		}
		else
			flashingPoint2 = null;
		if (board.getRedPiece(p.x, p.y).isKing())
		{
			if (board.hasBlackPiece(p.x + 1, p.y - 1) && p.x != 7 && p.y != 0)
			{
				if (!board.hasPiece(p.x + 2, p.y - 2) && p.x != 6 && p.y != 1)
				{
					flashingPoint3 = new Point(p.x+2, p.y-2);
					canJump = true;
				}
				else
					flashingPoint3 = null;
			}
			else
				flashingPoint3 = null;
			if (board.hasBlackPiece(p.x - 1, p.y - 1) && p.x != 0 && p.y != 0)
			{
				if (!board.hasPiece(p.x - 2, p.y - 2) && p.x != 1 && p.y != 1)
				{
					flashingPoint4 = new Point(p.x-2, p.y-2);
					canJump = true;
				}
				else
					flashingPoint4 = null;
			}
			else
				flashingPoint4 = null;
		}
		return canJump;
	}
	
	public boolean blackDoubleJump(Point p)
	{
		
		boolean canJump;
		canJump = false;
		
		if (board.hasRedPiece(p.x + 1, p.y - 1) && p.x != 7 && p.y != 0)
		{
			if (!board.hasPiece(p.x + 2, p.y - 2) && p.x != 6 && p.y != 1)
			{
				flashingPoint1 = new Point(p.x+2, p.y-2);
				canJump = true;
			}
			else
				flashingPoint1 = null;
		}
		else 
			flashingPoint1 = null;
		
		if (board.hasRedPiece(p.x - 1, p.y - 1) && p.x != 0 && p.y != 0)
		{
			if (!board.hasPiece(p.x - 2, p.y - 2) && p.x != 1 && p.y != 1)
			{
				flashingPoint2 = new Point(p.x-2, p.y-2);
				canJump = true;
			}
			else
				flashingPoint2 = null;
		}
		else
			flashingPoint2 = null;
		if (board.getBlackPiece(p.x, p.y).isKing())
		{
			if (board.hasRedPiece(p.x + 1, p.y + 1) && p.x != 7 && p.y != 7)
			{
				if (!board.hasPiece(p.x + 2, p.y + 2) && p.x != 6 && p.y != 6)
				{
					flashingPoint3 = new Point(p.x+2, p.y+2);
					canJump = true;
				}
				else
					flashingPoint3 = null;
			}
			else
				flashingPoint3 = null;
			
			if (board.hasRedPiece(p.x - 1, p.y + 1) && p.x != 0 && p.y != 7)
			{
				if (!board.hasPiece(p.x - 2, p.y + 2) && p.x != 1 && p.y != 6)
				{
					flashingPoint4 = new Point(p.x-2, p.y+2);
					canJump = true;
				}
				else
					flashingPoint4 = null;
			}
			else
				flashingPoint4 = null;
		}
		return canJump;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		JFrame frame = new JFrame();
		
		//check to see if user clicked on a piece
		//need to translate absolute coordinates into relative coordinates
		Point p = FindSquare(x, y);
		
		if(p.x == -1 || p.y == -1)
			return; //not on board
		else if (doubleJump)
		{
			try {
				if (redMove)
				{
					board.onRedMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y, true);
					if (redDoubleJump(p)) { 	
						selectedPoint1 = p;
					}
					else
					{
						flashingPoint1 = null;
						flashingPoint2 = null;
						flashingPoint3 = null;
						flashingPoint4 = null;
						redMove = false;
						doubleJump = false;
						selectedPoint1 = null;
					}
					repaint();
					return;
				}
				else
				{
					board.onBlackMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y , true);
					if (blackDoubleJump(p)) {
						selectedPoint1 = p;
					}
					else
					{
						flashingPoint1 = null;
						flashingPoint2 = null;
						flashingPoint3 = null;
						flashingPoint4 = null;
						redMove = true;
						doubleJump = false;
						selectedPoint1 = null;
					}
					repaint();
					return;
				}
				
			}
			catch (NotValidLocationException nv)
			{
				JOptionPane.showMessageDialog(frame, "Not Valid Move" + nv.getError());
			}
		}
		else
		{
			//highlight possible squares to move to if piece there
			if (redMove)
			{
				if (board.hasRedPiece(p.x, p.y))
				{
					if (pieceSelected == false)
					{
						pieceSelected = true;
						selectedPoint1 = new Point();
						selectedPoint1.x = p.x;
						selectedPoint1.y = p.y;
						System.out.println("Selected Piece" + p.x + p.y);
					
						GenerateRedFlashingPoints(p);					
						repaint();
						return;
					}
					else
					{
						selectedPoint1 = new Point();
						selectedPoint1.x = p.x;
						selectedPoint1.y = p.y;
						System.out.println("Selected Piece" + p.x + p.y);
						GenerateRedFlashingPoints(p);
						repaint();
						return;
					}
				}
				if (pieceSelected == true) //move
				{
					try
					{
						if (p.y - selectedPoint1.y == -2 || p.y - selectedPoint1.y == 2)
						{
							board.onRedMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y, true);
							
							if (redDoubleJump(p))
							{
								doubleJump = true;
								selectedPoint1 = p;
								repaint();
								return;
							}
						}
						else
							board.onRedMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y, false);
						//JOptionPane.showMessageDialog(frame, "Moved piece from " + 
						//		selectedPoint1.x + selectedPoint1.y + "to" + p.x + p.y);
						redMove = false;

					}
					catch (NotValidLocationException nv)
					{
						JOptionPane.showMessageDialog(frame, "Not Valid Move" + nv.getError());
					}
					flashingPoint1 = null;
					flashingPoint2 = null;
					flashingPoint3 = null;
					flashingPoint4 = null;
					repaint();
					pieceSelected = false;
				}
			}
			else
			{
				if (board.hasBlackPiece(p.x, p.y))
				{
					if (pieceSelected == false)
					{
						pieceSelected = true;
						selectedPoint1 = new Point();
						selectedPoint1.x = p.x;
						selectedPoint1.y = p.y;
						System.out.println("Selected Piece" + p.x + p.y);
					
						GenerateBlackFlashingPoints(p);					
						repaint();
						return;
					}
					else
					{
						selectedPoint1 = new Point();
						selectedPoint1.x = p.x;
						selectedPoint1.y = p.y;
						System.out.println("Selected Piece" + p.x + p.y);
						GenerateBlackFlashingPoints(p);
						repaint();
						return;
					}
				}
				if (pieceSelected == true) //move
				{
					try
					{
						if (p.y - selectedPoint1.y == -2 || p.y - selectedPoint1.y == 2)
						{
							board.onBlackMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y, true);
							//if it gets here, they have successfully jumped a piece
							//check for the double jump
							if (blackDoubleJump(p))
							{
								doubleJump = true;
								selectedPoint1 = p;
								repaint();
								return;
							}
							
						}
						else
							board.onBlackMove(selectedPoint1.x, selectedPoint1.y, p.x, p.y, false);
						//JOptionPane.showMessageDialog(frame, "Moved piece from " + 
						//		selectedPoint1.x + selectedPoint1.y + "to" + p.x + p.y);
						redMove = true;
					}
					catch (NotValidLocationException nv)
					{
						JOptionPane.showMessageDialog(frame, "Not Valid Move" + nv.getError());
					}
					flashingPoint1 = null;
					flashingPoint2 = null;
					flashingPoint3 = null;
					flashingPoint4 = null;
					repaint();
					pieceSelected = false;
				}
			}
		}
	}

	
	public void GenerateRedFlashingPoints(Point p)
	{
		//yellow helper squares
		if (!board.hasRedPiece(p.x+1, p.y+1) && p.x != 7 && p.y != 7)
		{
			System.out.println("Should hit here");
			if (board.hasBlackPiece(p.x + 1, p.y + 1))
			{
				System.out.println("Should also hit here");
				if (!board.hasPiece(p.x + 2, p.y + 2) && p.x != 6 && p.y != 6)
				{
					System.out.println("Should thirdly hit here");
					flashingPoint1 = new Point(p.x+2, p.y+2);
				}
			}
			else
				flashingPoint1 = new Point(p.x + 1, p.y + 1);
		}
		else
			flashingPoint1 = null;
		if (!board.hasRedPiece(p.x - 1, p.y+1) && p.x != 0 && p.y != 7)
		{
			if (board.hasBlackPiece(p.x - 1, p.y + 1))
			{
				if (!board.hasPiece(p.x - 2, p.y + 2) && p.x != 1 && p.y != 6)
					flashingPoint2 = new Point(p.x-2, p.y+2);
			}
			else
				flashingPoint2 = new Point(p.x - 1, p.y + 1);
		}
		else
			flashingPoint2 = null;
		if (board.getRedPiece(p.x, p.y).isKing())
		{
			if (!board.hasRedPiece(p.x-1, p.y-1) && p.x != 0 && p.y != 0)
			{
				if (board.hasBlackPiece(p.x - 1, p.y - 1))
				{
					if (!board.hasPiece(p.x - 2, p.y - 2) && p.x != 1 && p.y != 1)
						flashingPoint3 = new Point(p.x-2, p.y-2);
					else
						flashingPoint3 = null;
				}
				else
					flashingPoint3 = new Point(p.x - 1, p.y - 1);
			}
			else
				flashingPoint3 = null;
			if (!board.hasRedPiece(p.x+1, p.y-1) && p.x != 7 && p.y != 0)
			{
				if (board.hasBlackPiece(p.x + 1, p.y - 1))
				{
					if (!board.hasPiece(p.x + 2, p.y - 2) && p.x != 6 && p.y != 1)
						flashingPoint4 = new Point(p.x+2, p.y-2);
					else
						flashingPoint4 = null;
				}
				else
					flashingPoint4 = new Point(p.x + 1, p.y - 1);
			}
			else
				flashingPoint4 = null;
		}
	}
	
	public void GenerateBlackFlashingPoints(Point p)
	{
		//yellow helper squares
		if (!board.hasBlackPiece(p.x+1, p.y-1) && p.x != 7 && p.y != 0)
		{
			if (board.hasRedPiece(p.x + 1, p.y - 1))
			{
				if (!board.hasPiece(p.x + 2, p.y - 2) && p.x != 6 && p.y != 1)
				{
					flashingPoint1 = new Point(p.x+2, p.y-2);
				}
			}
			else
				flashingPoint1 = new Point(p.x + 1, p.y - 1);
		}
		else
			flashingPoint1 = null;
		if (!board.hasBlackPiece(p.x - 1, p.y-1) && p.x != 0 && p.y != 0)
		{
			if (board.hasRedPiece(p.x - 1, p.y - 1))
			{
				if (!board.hasPiece(p.x - 2, p.y - 2) && p.x != 1 && p.y != 1)
					flashingPoint2 = new Point(p.x-2, p.y-2);
			}
			else
				flashingPoint2 = new Point(p.x - 1, p.y - 1);
		}
		else
			flashingPoint2 = null;
		if (board.getBlackPiece(p.x, p.y).isKing())
		{
			if (!board.hasBlackPiece(p.x-1, p.y+1) && p.x != 0 && p.y != 7)
			{
				if (board.hasRedPiece(p.x - 1, p.y + 1))
				{
					if (!board.hasPiece(p.x - 2, p.y + 2) && p.x != 1 && p.y != 6)
						flashingPoint3 = new Point(p.x-2, p.y+2);
					else
						flashingPoint3 = null;
				}
				else
					flashingPoint3 = new Point(p.x - 1, p.y + 1);
			}
			else
				flashingPoint3 = null;
			if (!board.hasBlackPiece(p.x+1, p.y+1) && p.x != 7 && p.y != 7)
			{
				if (board.hasRedPiece(p.x + 1, p.y + 1))
				{
					if (!board.hasPiece(p.x + 2, p.y + 2) && p.x != 6 && p.y != 6)
						flashingPoint4 = new Point(p.x+2, p.y+2);
					else
						flashingPoint4 = null;
				}
				else
					flashingPoint4 = new Point(p.x + 1, p.y + 1);
			}
			else
				flashingPoint4 = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public Point FindSquare(int x, int y)
	{
		int returnx, returny;
		if (x > 580) {returnx = -1;}
		else if (x > 510) { returnx = 7; }
		else if (x > 440) { returnx = 6; }
 		else if (x > 370) { returnx = 5; }
		else if (x > 300) { returnx = 4; }
		else if (x > 230) { returnx = 3; }
		else if (x > 160) { returnx = 2; }
		else if (x > 90) { returnx = 1; }
		else if (x > 20) { returnx = 0; }
		else returnx = -1;
		if (y>590) { returny = -1;}
		else if (y > 520) {returny = 0;}
		else if (y > 450) {returny = 1;}
		else if (y > 380) {returny = 2;}
		else if (y > 310) {returny = 3;}
		else if (y > 240) {returny = 4;}
		else if (y > 170) {returny = 5;}
		else if (y > 100) {returny = 6;}
		else if (y > 30) {returny = 7;}
		else returny = -1;
		
		return new Point(returnx, returny);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}