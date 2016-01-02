package theIsland;

import java.util.Random;

abstract class organism {

	public organism target;

	public String name;

	public int age; 

	public int direction;

	public boolean alive; 

	public int sightRange;

	public Island myIsland;

	public IslandCell myCell;

	public boolean inCombat;

	public int moveSpeed;

	public int type; //organism type. This is crude, would like to replace with better option.

	public int hpMax; //Maximum HP. Also doubles as nutrient count for felled animals
	public int hpCurrent; //Animal's current HP. When 0 - animal dies.

	public int foodMax;
	public int foodCurrent; 

	public void metabolism(){
		if ((this.hpCurrent < this.hpMax) && (foodCurrent < foodMax) && (foodCurrent > 0)){
			foodCurrent--;
			hpCurrent++;
		}
	}


	/*
	 * 
		Template:
		0-Type(0-Plant, 1-Herbivore, 2-Carnivore), 1-Species/Name, 2-Age, 3-SightRange, 4-MoveSpeed,5-HPMax,6-FoodMax
	 * 
	 */

	organism(String[] profile, IslandCell incCell){ //default constructor - will use a specified File and parse it for individual animal starting traits. 
		String[] orgProfile = profile;

		Random rando = new Random();

		//Initialize each animal's specific traits

		this.alive=true;

		this.type=Integer.parseInt(orgProfile[0]);

		this.name=orgProfile[1];	

		this.myCell = incCell;

		this.age=Integer.parseInt(orgProfile[2]);

		this.sightRange=Integer.parseInt(orgProfile[3]);

		this.moveSpeed=Integer.parseInt(orgProfile[4]);

		this.hpMax=Integer.parseInt(orgProfile[5]);
		this.hpCurrent=this.hpMax;  //initialized at max HP

		this.foodMax=Integer.parseInt(orgProfile[6]);
		this.foodCurrent=this.foodMax; //Initialized at Max Food.

		this.direction = rando.nextInt(8); //randomly assigned starting direction.
	}

	public void soundOff(){
		System.out.println("I am " + this.name + ", I am " + this.age + " ticks old.");
		System.out.println("I am located at [" + this.myCell.x + ","+ this.myCell.y +"]");
		System.out.println("Facing direction :" + this.direction + " \tFacing target: " + this.target);
		System.out.println("My HP: " + this.hpCurrent + "/" + this.hpMax + "\tFood: " + this.foodCurrent + "/" + this.foodMax + "\n\n");
	}


	public boolean eat(IslandCell cell){
		//Animal will eat so long as food is present in the specified cell. 

		//		if(/*cell contains food and the animal wants to eat*/{
		//			//decrement food value of cell
		//			this.foodCurrent++;
		//		}
		//		
		return true;
	}





	//	public void takeTurn(){
	//		
	//		if(inCombat){
	//			resolveCombat();
	//		}
	//		else{
	//			if()
	//		}
	//		
	//	}

	public boolean isHungry(){
		/* Hungriness is determined if: A) the animal is carrying less than 1/3 of it's maximum food (rumbling stomach) and/or 
		 * if the animal is injured as healing will consume food faster, so more food will be needed.
		 */
		return ((this.foodCurrent < (this.foodMax/3)) || (this.hpCurrent<this.hpMax));
	}


	//	public void scan(){
	//		//Looks around it as far as it can sense
	//		for (int i=this.location.x-this.sightRange)
	//		
	//	}


	public void move(boolean run){

		int moveDist;
		int newx;
		int newy;


		Random distGenerator = new Random();

		if (run){
			moveDist=this.moveSpeed; //maximum speed
		}else{
			moveDist = distGenerator.nextInt(this.moveSpeed); //randomly selects a speed between 1 and Animal's Max.
		}

		if (this.target==null){ // if the creature doesn't have a target worth moving towards randomly select one.
			this.direction = distGenerator.nextInt(8);
		}else if (this.target.type == 2){ //If the targeted creature is a predator (may need to make more generic)
			this.direction = oppositeDir(targetDir(this.target.myCell)); // Creature turns directly away from perceived threat.
		}else{
			this.direction = targetDir(this.target.myCell); //Creature turns TOWARD intended target.
		}

		//		for (int i=0; i<=moveDist; i++){
		//			switch(this.direction){
		//			case 0: 
		//				newx = 
		//				break;
		//			}
		//		}
	}

	public int targetDir(IslandCell targetCell){
		IslandCell target = targetCell;

		int direction; 

		int difx = this.myCell.x - targetCell.x;
		int dify = this.myCell.y - targetCell.y;

		/*Determinant of direction between cell A and cell B. This is super crude, forgive me.
		 * 
		 * Direction grid:
		 * 
		 *   0  1  2
		 *   7  X  3
		 *   6  5  4
		 *
		 */
		if (difx<0){
			if (dify<0){
				direction = 6;
			}
			else if (dify>0){
				direction = 0;
			}
			else{
				direction = 7;
			}							//Oh god its so ugly.
		}else if (difx>0){
			if (dify<0){
				direction = 4;
			}
			else if (dify>0){
				direction = 2;
			}
			else{
				direction = 3;
			}
		}else{
			if (dify>0){
				direction = 1;
			}
			else{
				direction = 5;
			}
		}
		return direction;
	}




	public int oppositeDir(int dir){
		/* Returns the opposite direction that the animal is facing - in event of perceived threat. 
		 * 
		 *    0  1  2
		 *    7  X  3
		 *    6  5  4   
		 * 
		 * */	
		int opDir;
		if (dir<4){
			opDir=dir+4;
		}
		else{
			opDir=dir-4;
		}
		return opDir;
	}

	public boolean validCoords(int x, int y){

		if ((x>=0) && (x<this.myIsland.x) && (y>=0) && (y<this.myIsland.y)){	//checks to see if intended coords are within the Island.
			if (!this.myIsland.theIsland[x][y].occupied()){ //checks to see if the cell is currently occupied 
				return true;
			}
		}
		return false;
	}

}
