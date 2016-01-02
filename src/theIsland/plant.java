package theIsland;

/* the plant abstract class. Basis and functions for this simple class of organisms who do not move */

public class plant extends organism {
	
	plant(String[] profile, IslandCell incCell){
		super(profile, incCell);
	}

	@Override //override for eat function - represents photosynthesis.
	public boolean eat(IslandCell cell){
		this.foodCurrent++;
		return true;
	}
	
	@Override
	public void move(boolean run){
		//Plants dont move
	}

}
