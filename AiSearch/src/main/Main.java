package main;

import game.*;
import searchAI.*;
import processing.core.PApplet;

public class Main extends PApplet
{
	int gridWidth = 4;
	int gridHeight = 4;
	char[] actions;
	int actIdx = 0;
	int jonRow;
	int jonCol;
	int time;
	SaveWesterosState state;

	float horizontalOffset = 50.0f;
	float verticalOffset;
	float cellLength;

	public void settings()
	{
		size(650, 650);
	}

	public void setup()
	{
		SaveWesteros g = new SaveWesteros(gridWidth, gridHeight);
		g.print();
		state = (SaveWesterosState)g.initialState;
		Node n = Search.BFS(g);
		jonRow = gridHeight - 1;
		jonCol = gridWidth - 1;
		if(n == null)
		{
			System.exit(0);
		} else {
			actions = n.state.sequenceOfActions.toCharArray();
		}
		
		cellLength = ((float)width - 2 * horizontalOffset) / gridWidth;
		verticalOffset = ((float)height - cellLength * gridHeight) / 2;
		time = millis();
	}
	
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
			break;
		case 'P':
			state = state.pick();
			break;
		}
	}
	
	public void draw()
	{
		if(millis() > time + 1000)
		{
			performNextAction();
			time = millis();
		}
		background(100);
		stroke(255, 0, 0);
		for(float i = 0; i < gridHeight; i += 1.0f)
		{
			for (float j = 0; j < gridWidth; j += 1.0f)
			{
				if(state.isEmpty((int)i, (int)j))
				{
					fill(255);
				} else if (state.isDragonstone((int)i, (int)j)) {
					fill(255, 0, 0);
				} else if (state.isWhitewalker((int)i, (int)j)) {
					fill(0, 255, 0);
				}
				if((int)i == jonRow && (int)j == jonCol)
				{
					fill(0, 0, 255);
				}
				rect(j * cellLength + horizontalOffset, i * cellLength + verticalOffset, cellLength, cellLength);				
			}
		}
	}
	
	public static void main(String[] args)
	{
		PApplet.main("main.Main");
	}
}