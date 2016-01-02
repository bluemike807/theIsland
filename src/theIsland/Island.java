package theIsland;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Island {
	
	public double version = 1.0;

	public int x;
	public int y;

	public static ArrayList<organism> menagerie = new ArrayList<organism>();
	/* Menagerie added as a means of tracking all living animals to iterate through each 'tick' - 
	 * saves iterating across the entire Island grid.
	 */

	public IslandCell[][] theIsland; //2D array serving as 'the world' Grid of cells. 

	//CONSTRUCTOR

	//Please note - this is admittedly crude. Ideally/eventually I will make a 'fractal' island 
	Island(int ix, int iy) throws IOException{
		this.x=ix;
		this.y=iy;

		theIsland = new IslandCell[x][y]; 
		initIsland(this.x, this.y);
		this.populateIsland();
		
	}

	void initIsland(int ix, int iy){
		for(int i=0; i<ix; i++){ //for every row
			for (int j=0; j<iy; j++){ //for every cell
				theIsland[i][j] = new IslandCell(i, j);  //Initializes each Island cell, providing it with its unique coordinate.
			}
		}
	}
	
	void addOrganism(String[] profile){
		
		organism newOrganism;
		String[] orgCmds = profile;
		int ox;
		int oy;
		Random rando = new Random();
		
		
		do{ //randomly selects a starting cell that isn't already occupied.
			ox=rando.nextInt(4);  
			oy=rando.nextInt(4);
//			ox=rando.nextInt(this.x);  
//			oy=rando.nextInt(this.y);
		}while(theIsland[ox][oy].occupied());

		/*Detects organism type (0-Plant, 1-Herbivore, 2-Carnivore, instantiates a new one, puts it in the preselected random 
		 * cell on the Island, and adds it to the menagerie. 
		 */

		if(orgCmds[0].equals("0")){
			newOrganism = new plant(orgCmds, theIsland[ox][oy]);
		}
		if(orgCmds[0].equals("1")){
			newOrganism = new herbivore(orgCmds, theIsland[ox][oy]);

		}
		else{  //(orgCmds[0].equals("2"))
			newOrganism = new carnivore(orgCmds, theIsland[ox][oy]);
		}
		menagerie.add(newOrganism);
		theIsland[ox][oy].occupant = newOrganism;
	}

	void populateIsland() throws IOException{
		/*creates each animal from specified file input.
		 * organisms are randomly dropped across the map.
		 */

		String orgLine;

		try (BufferedReader br = new BufferedReader(new FileReader("src/testing.txt"))) {
			while ((orgLine = br.readLine()) != null) { //produces each line
				this.addOrganism(orgLine.split(",")); //Split separates each argument for processing. Is then passed to addOrganism for individual creature insertion.
			}
		}catch (Exception e){
			System.out.println("Caught at populateIsland- " + e);
		}
	}


	void renderIsland(){
		char icon =' '; //individual icon for each cell of a text-represented Island

		for(int i=0; i<this.x; i++){
			for (int j=0; j<this.y; j++){
				if (theIsland[i][j].occupant != null){
					switch(theIsland[i][j].occupant.type){
					case 0: 
						icon = 'T'; //Plant Type
						break;
					case 1: 
						icon = 'H'; //Herbivore Type
						break;
					case 2:
						icon = 'M'; //Carnivore Type 
						break;
					default: 
						icon = '?'; //Unknown. Shouldn't occur.
						break;
					}
				}
				else{
					icon = '.'; //empty cell
				}
				System.out.print(icon);
			}
			System.out.println("\n");
		}

	}

	public static void main(String[] args) {  
		/* Where the magic happens. */
		System.out.println("Hello World.!");
		
		try{
		Island myIsland = new Island(10,10);
		
		System.out.println("Island Created.\nRendering...\n");
		
		myIsland.renderIsland();
		
		}catch (Exception q){
			System.out.println("Caught at Main: " + q);
		}
		
		for(organism s : menagerie){
			s.soundOff();			
		}
		
		System.out.println("Targets Assigned");
		
		menagerie.get(0).target = menagerie.get(1);
		menagerie.get(1).target = menagerie.get(0);
		
		for(organism s : menagerie){
			s.soundOff();			
		}
	}

}
