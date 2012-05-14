package CheckerPiece;

public class CheckerPiece
{
	
	private int xposition;
	private int yposition;;
	int color;
	
	int radius; //for displaying and contact detection
	
	Boolean visible; //0 is not visible, 1 is visible
	Boolean isKing; //if piece is king, 1 else 0
	
	public CheckerPiece(int x, int y, int red)
	{
		this.xposition = x;
		this.yposition = y;
		if (red == 0)
		{
			//black
			color = 0;
		}
		else if (red == 1)
		{
			//red
			color = 1;
		}
		isKing = false;
	}
	
	public void setRadius(int rad)
	{
		radius = rad;
	}
	
	public int getRadius (int rad)
	{
		return this.radius;
	}
	
	public void pieceMoved(int x, int y)
	{
		this.xposition = x;
		this.yposition = y;
		if (this.color == 1 && y == 7)
			this.isKing = true;
		else if (this.color == 0 && y == 0)
			this.isKing = true;
	}
	
	public void setVisible()
	{
		this.visible = true;
	}
	
	public void setNotVisible()
	{
		this.visible = false;
	}
	
	public Boolean getVisible()
	{
		return this.visible;
	}
	
	public int getXCoord()
	{
		return this.xposition;
	}
	
	public int getYCoord()
	{
		return this.yposition;
	}
	
	public Boolean isKing()
	{
		return this.isKing;
	}
	
	public void makeKing()
	{
		this.isKing = true;
		return;
	}
	
	public void pieceGone()
	{
		//remove from drawing
		//reset position auto garbage collection
		this.xposition = -1;
		this.yposition = -1;
	}

}
