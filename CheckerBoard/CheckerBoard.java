package CheckerBoard;

import CheckerPiece.*;
import Exceptions.*;

public class CheckerBoard 
{

	int numSquares = 64;
	int startingPieces = 12;
	
	CheckerPiece[] RedPieces;
	CheckerPiece[] BlackPieces;
	
	public CheckerBoard(int players)
	{
		
		RedPieces = new CheckerPiece[12];
		BlackPieces = new CheckerPiece[12];
		
		/*
		 * Starting with an index of 0, 0 at the bottom left corner
		 * The bounds are therefore 7 and 7
		 * Red pieces are at (0, 0), (2, 0), (4,0), (6,0)
		 * (1, 1), (3,1), (5,1), (7,1)
		 * (0,2), (2, 2), (4,2), (6,2)
		 * 
		 * Black pieces are at (1, 5), (3, 5), (5, 5), (7, 5), 
		 * (0, 6), (2, 6), (4, 6), (6, 6)
		 * (1, 7), (3, 7), (5, 7), (7, 7)
		 */
		
		for (int i = 0; i < 12; i++)
		{
			if (i < 4) //first four pieces
			{
				RedPieces[i] = new CheckerPiece(i*2, 0, 1);
				BlackPieces[i] = new CheckerPiece(1 + i*2, 5, 0);
			}
			else if (i < 8)// middle four pieces
			{
				RedPieces[i] = new CheckerPiece( (i-4)*2 + 1, 1, 1);
				BlackPieces[i] = new CheckerPiece((i-4)*2, 6, 0);
			}
			else //last four
			{
				RedPieces[i] = new CheckerPiece((i-8)*2, 2, 1);
				BlackPieces[i] = new CheckerPiece((i-8)*2 + 1, 7, 0);
			}
				
		}
	}
	
	public void onRedMove(int startx, int starty, int destx, int desty,Boolean isJump) throws NotValidLocationException
	{
		//check to make sure there is no red/black piece at the destination
		for (int k = 0; k < 12; k++)
		{
			if (RedPieces[k].getXCoord() == destx && RedPieces[k].getYCoord() == desty)
				throw new NotValidLocationException("Pieces in the way of moving");
			if (BlackPieces[k].getXCoord() == destx && BlackPieces[k].getYCoord() == desty)
				throw new NotValidLocationException("Pieces in the way of moving");
		}
		
		if (!isJump)
		{
			//double check to see that there is in fact a piece at startx,starty
			for (int i = 0; i < 12; i ++)
			{
				if (RedPieces[i].getXCoord() == startx && RedPieces[i].getYCoord()==starty)
				{
					//there is a piece there, check to see if destx, desty is a valid spot
					//check to see if king
					//loop around should be ok?
					if (RedPieces[i].isKing())
					{
						if (desty == RedPieces[i].getYCoord() + 1 || desty == RedPieces[i].getYCoord() - 1)
						{
							//valid y coordinate, check x now
							if (destx == RedPieces[i].getXCoord()+1 || destx == RedPieces[i].getXCoord() - 1)
							{
								//valid x coordinate
								RedPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("Not valid destination");
					}
					else //the piece is not a king
					{
						if (desty == RedPieces[i].getYCoord() + 1)
						{
							//valid y coordinate, check x now
							if (destx == RedPieces[i].getXCoord()+1 || destx == RedPieces[i].getXCoord() - 1)
							{
								//valid x coordinate
								RedPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("Not valid destination");
					}
				}	
				if (i == 11) //checked all the pieces, none of them are at the indicated start location
					throw new NotValidLocationException("No pieces at start location");
			}
			//if it reaches here, piece moved successfully
		}
		else //piece is jumping
		{
			//double check to see that there is in fact a piece at startx,starty
			for (int i = 0; i < 12; i ++)
			{
				if (RedPieces[i].getXCoord() == startx && RedPieces[i].getYCoord()==starty)
				{
					//there is a piece there, check to see if destx, desty is a valid spot
					//check to see if king
					//loop around should be ok?
					if (RedPieces[i].isKing())
					{
						if (desty == RedPieces[i].getYCoord() + 2 || desty == RedPieces[i].getYCoord() - 2)
						{
							//valid y coordinate, check x now
							if (destx == RedPieces[i].getXCoord() + 2 || destx == RedPieces[i].getXCoord() - 2)
							{
								//valid x coordinate, make sure there is a piece in the middle
								//checking black pieces
								for(int j = 0; j < 12; j ++)
								{
									if (BlackPieces[j].getXCoord() == (destx + startx)/2 
											&&BlackPieces[j].getYCoord() == (desty+starty) / 2)
									{
										BlackPieces[j].pieceGone();
										break;
									}
									
									if (j == 11)
									{
										throw new NotValidLocationException("No pieces to jump over");
									}
								}
								RedPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("End destination not reachable");
					}
					else //the piece is not a king
					{
						if (desty == RedPieces[i].getYCoord() + 2)
						{
							//valid y coordinate, check x now
							if (destx == RedPieces[i].getXCoord()+2 || destx == RedPieces[i].getXCoord() - 2)
							{
								//valid x coordinate
								//check to make sure there is a piece in the middle
								for(int j = 0; j < 12; j ++)
								{
									if (BlackPieces[j].getXCoord() == (destx + startx)/2 
											&&BlackPieces[j].getYCoord() == (desty+starty) / 2)
									{
										BlackPieces[j].pieceGone();
										break;
									}
									
									if (j == 11)
									{
										throw new NotValidLocationException("No pieces to jump over");
									}
								}
								RedPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("End square not reachable");
					}
				}	
				if (i == 11) //checked all the pieces, none of them are at the indicated start location
					throw new NotValidLocationException("No pieces at start location");
			}
			//if it reaches here, piece moved successfully
		}
		
	}
	
	public void onBlackMove(int startx, int starty, int destx, int desty,Boolean isJump) throws NotValidLocationException
	{
		//check to make sure there is no red/black piece at the destination
		for (int k = 0; k < 12; k++)
		{
			if (RedPieces[k].getXCoord() == destx && RedPieces[k].getYCoord() == desty)
				throw new NotValidLocationException("Can't move to square, piece already there");
			if (BlackPieces[k].getXCoord() == destx && BlackPieces[k].getYCoord() == desty)
				throw new NotValidLocationException("Can't move to square, piece already there");
		}
		
		if (!isJump)
		{
			//double check to see that there is in fact a piece at startx,starty
			for (int i = 0; i < 12; i ++)
			{
				if (BlackPieces[i].getXCoord() == startx && BlackPieces[i].getYCoord()==starty)
				{
					//there is a piece there, check to see if destx, desty is a valid spot
					//check to see if king
					//loop around should be ok?
					if (BlackPieces[i].isKing())
					{
						if (desty == BlackPieces[i].getYCoord() + 1 || desty == BlackPieces[i].getYCoord() - 1)
						{
							//valid y coordinate, check x now
							if (destx == BlackPieces[i].getXCoord()+1 || destx == BlackPieces[i].getXCoord() - 1)
							{
								//valid x coordinate
								BlackPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("End destination is not a move-able square");
					}
					else //the piece is not a king
					{
						if (desty == BlackPieces[i].getYCoord() - 1)
						{
							//valid y coordinate, check x now
							if (destx == BlackPieces[i].getXCoord() + 1 || destx == BlackPieces[i].getXCoord() - 1)
							{
								//valid x coordinate
								BlackPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("End destination is not a move-able square");
					}
				}	
				if (i == 11) //checked all the pieces, none of them are at the indicated start location
					throw new NotValidLocationException("No black pieces are at start location");
			}
			//if it reaches here, piece moved successfully
		}
		else //piece is jumping
		{
			//double check to see that there is in fact a piece at startx,starty
			for (int i = 0; i < 12; i ++)
			{
				if (BlackPieces[i].getXCoord() == startx && BlackPieces[i].getYCoord()==starty)
				{
					//there is a piece there, check to see if destx, desty is a valid spot
					//check to see if king
					//loop around should be ok?
					if (BlackPieces[i].isKing())
					{
						if (desty == BlackPieces[i].getYCoord() + 2 || desty == BlackPieces[i].getYCoord() - 2)
						{
							//valid y coordinate, check x now
							if (destx == BlackPieces[i].getXCoord() + 2 || destx == BlackPieces[i].getXCoord() - 2)
							{
								//valid x coordinate, make sure there is a piece in the middle
								//checking black pieces
								for(int j = 0; j < 12; j ++)
								{
									if (RedPieces[j].getXCoord() == (destx + startx)/2 
											&&RedPieces[j].getYCoord() == (desty+starty) / 2)
									{
										RedPieces[j].pieceGone();
										break;
									}
									
									if (j == 11)
									{
										throw new NotValidLocationException("No pieces to be jumped");
									}
								}
								BlackPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("not move-able destination");
					}
					else //the piece is not a king
					{
						if (desty == BlackPieces[i].getYCoord() - 2)
						{
							//valid y coordinate, check x now
							if (destx == BlackPieces[i].getXCoord()+2 || destx == BlackPieces[i].getXCoord() - 2)
							{
								//valid x coordinate
								//check to make sure there is a piece in the middle
								for(int j = 0; j < 12; j ++)
								{
									if (RedPieces[j].getXCoord() == (destx + startx)/2 
											&&RedPieces[j].getYCoord() == (desty+starty) / 2)
									{
										RedPieces[j].pieceGone();
										break;
									}
									
									if (j == 11)
									{
										throw new NotValidLocationException("No pieces to jump");
									}
								}
								BlackPieces[i].pieceMoved(destx, desty);
								break;
							}
						}
						//something wrong with function call, throw exception
						throw new NotValidLocationException("Destination square not moveable to");
					}
				}	
				if (i == 11) //checked all the pieces, none of them are at the indicated start location
					throw new NotValidLocationException("no pieces at start location");
			}
			//if it reaches here, piece moved successfully
		}
		
	}
	
	public void redking(int x, int y) throws NotValidLocationException
	{
		//check to see that piece exists there
		for (int i = 0; i < 12; i++)
		{
			if (RedPieces[i].getXCoord() == x && RedPieces[i].getYCoord() == y)
			{
				RedPieces[i].makeKing();
				break;
			}
			if (i == 11)
				throw new NotValidLocationException("No red piece to be kinged");
		}
	}
	
	public void blackking(int x, int y) throws NotValidLocationException
	{
		//check to see that piece exists there
		for (int i = 0; i < 12; i++)
		{
			if (BlackPieces[i].getXCoord() == x && BlackPieces[i].getYCoord() == y)
			{
				BlackPieces[i].makeKing();
				break;
			}
			if (i == 11)
				throw new NotValidLocationException("No black piece to be kinged");
		}
	}
	
	public boolean hasPiece(int x, int y)
	{
		for (int i = 0; i < 12; i++)
		{
			if (RedPieces[i].getXCoord() == x && RedPieces[i].getYCoord() == y)
				return true;
			if (BlackPieces[i].getXCoord() == x && BlackPieces[i].getYCoord() == y)
				return true;
		}
		return false;
	}
	
	public boolean hasRedPiece(int x, int y)
	{
		for (int i = 0; i < 12; i++)
		{
			if (RedPieces[i].getXCoord() == x && RedPieces[i].getYCoord() == y)
				return true;
		}
		return false;
	}
	
	public boolean hasBlackPiece(int x, int y)
	{
		for (int i = 0; i < 12; i++)
		{
			if (BlackPieces[i].getXCoord() == x && BlackPieces[i].getYCoord() == y)
				return true;
		}
		return false;
	}
	
	public CheckerPiece getBlackPiece(int x, int y)
	{
		for (int i = 0; i < 12; i ++)
		{
			if (BlackPieces[i].getXCoord() == x && BlackPieces[i].getYCoord() == y)
				return BlackPieces[i];
		}
		return null;
	}
	
	public CheckerPiece getRedPiece(int x, int y)
	{
		for (int i = 0; i < 12; i ++)
		{
			if (RedPieces[i].getXCoord() == x && RedPieces[i].getYCoord() == y)
				return RedPieces[i];
		}
		return null;
	}
	
}
