package main;

import game.*;
import searchAI.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Visualization extends PApplet
{
	public static final int DFS = 0;
	public static final int BFS = 1;
	public static final int IDS = 2;
	public static final int UCS = 3;
	public static final int GRD = 4;
	public static final int AST = 5;
	public static final int GRD2 = 6;
	public static final int AST2 = 7;
	
	static int gridWidth;
	static int gridHeight;
	static char[] actions;
	static int actIdx = 0;
	static int jonRow;
	static int jonCol;
	static int time;
	static int numDG = 0;
	static SaveWesteros prob;
	static SaveWesterosNode state;

	static boolean alt = false;
	static int kidx = -1;
	static int imgTime;
	static PImage jon1;
	static PImage jon2;
	static PImage jonk1;
	static PImage jonk2;
	static PImage jonk3;
	static PImage dragonstone;
	static PImage ww1;
	static PImage ww2;

	static final float offset = 50.0f;
	static float horizontalOffset;
	static float verticalOffset;
	static float cellLength;

	/**
	 * GUI settings
	 */
	public void settings()
	{
		size(650, 650);
	}
	
	/**
	 * Start the visualisation.
	 */
	public void initScreen()
	{
		PApplet.main("main.Visualization");
	}
	
	/**
	 * Set the problem to be sloved
	 * @param p The problem
	 */
	public void setProblem(GenericSearchProblem p)
	{
		prob = (SaveWesteros) p;
		gridHeight = ((SaveWesterosNode)prob.initialNode).map.length;
		gridWidth = ((SaveWesterosNode)prob.initialNode).map[0].length;
		state = (SaveWesterosNode)prob.initialNode;
		jonRow = gridHeight - 1;
		jonCol = gridWidth - 1;
	}

	/**
	 * Set the desired search function to use.
	 * @param searchFunction Number representing which function to use.
	 */
	public void setSearch(int searchFunction)
	{
		Node n = null;
		switch(searchFunction)
		{
		case DFS:
			n = Search.DFS(prob);
			break;
		case BFS:
			n = Search.BFS(prob);
			break;
		case IDS:
			n = Search.IDS(prob);
			break;
		case UCS:
			n = Search.UCS(prob);
			break;
		case GRD:
			n = Search.Greedy(prob);
			break;
		case AST:
			n = Search.AStar(prob);
			break;
		case GRD2:
			n = Search.Greedy2(prob);
			break;
		case AST2:
			n = Search.AStar2(prob);
			break;
		}
		if(n == null)
		{
			System.exit(0);
		} else {
			actions = n.sequenceOfActions.toCharArray();
		}
	}

	/**
	 * Function called before the first frame is rendered.
	 */
	public void setup()
	{	
		jon1 = loadImage("jon1.png");
		jon2 = loadImage("jon2.png");
		jonk1 = loadImage("jonk1.png");
		jonk2 = loadImage("jonk2.png");
		jonk3 = loadImage("jonk3.png");
		dragonstone = loadImage("dragonstone.png");
		ww1 = loadImage("ww1.png");
		ww2 = loadImage("ww2.png");
		
		if(gridWidth >= gridHeight)
		{
			horizontalOffset = offset;
			cellLength = ((float)width - 2 * horizontalOffset) / gridWidth;
			verticalOffset = ((float)height - cellLength * gridHeight) / 2;
		} else {
			verticalOffset = offset;
			cellLength = ((float)height - 2 * verticalOffset) / gridHeight;
			horizontalOffset = ((float)width - cellLength * gridWidth) / 2;
		}
		jon1.resize((int)cellLength, (int)cellLength);
		jon2.resize((int)cellLength, (int)cellLength);
		jonk1.resize((int)cellLength, (int)cellLength);
		jonk2.resize((int)cellLength, (int)cellLength);
		jonk3.resize((int)cellLength, (int)cellLength);
		dragonstone.resize((int)cellLength, (int)cellLength);
		ww1.resize((int)cellLength, (int)cellLength);
		ww2.resize((int)cellLength, (int)cellLength);

		imgTime = time = millis();
		frame.setLocation(50, 50);
	}

	/**
	 * Gets the next action to perform and sets the right variables to show
	 * it visually.
	 */
	public void performNextAction()
	{
		if(actIdx == actions.length)
		{
			System.out.println("Jon Did It!");
			noLoop();
			return;
		}
		char action = actions[actIdx++];
		switch(action)
		{
		case 'N':
			state = state.north();
			jonRow--;
			break;
		case 'S':
			state = state.south();
			jonRow++;
			break;
		case 'E':
			state = state.east();
			jonCol++;
			break;
		case 'W':
			state = state.west();
			jonCol--;
			break;
		case 'K':
			state = state.kill();
			kidx = 0;
			numDG--;
			break;
		case 'P':
			state = state.pick();
			numDG += state.numDragonglassPieces;
			break;
		}
	}

	/**
	 * Function called every frame.
	 */
	public void draw()
	{
		if(millis() > time + 500)
		{
			performNextAction();
			time = millis();
		}

		if(millis() > imgTime + 200)
		{
			alt = !alt;
			if(kidx > -1)
			{
				kidx++;
			}
			if(kidx == 3)
			{
				kidx = -1;
			}
			imgTime = millis();
		}

		background(100);
		text("Dragonglasses remaining: " + numDG, 10, 10);
		stroke(0);
		for(float i = 0; i < gridHeight; i += 1.0f)
		{
			for (float j = 0; j < gridWidth; j += 1.0f)
			{
				if(!state.isObstacle((int)i, (int)j))
				{
					fill(255);
					rect(j * cellLength + horizontalOffset, i * cellLength + verticalOffset, cellLength, cellLength);
				}
				
				if (state.isDragonstone((int)i, (int)j))
				{
					image(dragonstone, j * cellLength + horizontalOffset, i * cellLength + verticalOffset);
				} else if (state.isWhitewalker((int)i, (int)j)) {
					if(alt)
					{
						image(ww1, j * cellLength + horizontalOffset, i * cellLength + verticalOffset);
					} else {
						image(ww2, j * cellLength + horizontalOffset, i * cellLength + verticalOffset);
					}
				}
			}
		}
		if(kidx < 0)
		{
			if(alt)
			{
				image(jon1, jonCol * cellLength + horizontalOffset + 15.0f, jonRow * cellLength + verticalOffset);
			} else {
				image(jon2, jonCol * cellLength + horizontalOffset + 15.0f, jonRow * cellLength + verticalOffset);
			}
		} else if(kidx == 0) {
			image(jonk1, jonCol * cellLength + horizontalOffset + 15.0f, jonRow * cellLength + verticalOffset);
		} else if(kidx == 1) {
			image(jonk2, jonCol * cellLength + horizontalOffset + 15.0f, jonRow * cellLength + verticalOffset);
		} else if(kidx == 2) {
			image(jonk3, jonCol * cellLength + horizontalOffset + 15.0f, jonRow * cellLength + verticalOffset);
		}
	}
}