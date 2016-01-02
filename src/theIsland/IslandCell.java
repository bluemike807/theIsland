package theIsland;


public class IslandCell {
	
	public organism occupant;
	public int x;
	public int y;
	
	IslandCell(int ix, int iy){
		this.x=ix;
		this.y=iy;
	}
	
	
	public boolean occupied(){
		return (occupant!=null);
	}
}
