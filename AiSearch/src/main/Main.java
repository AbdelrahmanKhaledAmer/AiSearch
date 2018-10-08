package main;

import game.*;
import searchAI.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet
{
	int gridWidth = 6;
	int gridHeight = 6;
	char[] actions;
	int actIdx = 0;
	int jonRow;
	int jonCol;
	int time;
	SaveWesterosState state;

	boolean alt = false;
	int kidx = -1;
	int imgTime;
	PImage jon1;
	PImage jon2;
	PImage jonk1;
	PImage jonk2;
	PImage jonk3;

	final float offset = 50.0f;
	float horizontalOffset;
	float verticalOffset;
	float cellLength;
	
	boolean window;

	public void settings()
	{
		size(650, 650);
	}

	public void setup()
	{	
		jon1 = loadImage("jon1.png");
		jon2 = loadImage("jon2.png");
		jonk1 = loadImage("jonk1.png");
		jonk2 = loadImage("jonk2.png");
		jonk3 = loadImage("jonk3.png");

		SaveWesteros g = new SaveWesteros(gridHeight, gridWidth);
		g.print();
		state = (SaveWesterosState)g.initialState;
		Node n = Search.AStar(g);
		jonRow = gridHeight - 1;
		jonCol = gridWidth - 1;
		if(n == null)
		{
			System.exit(0);
		} else {
			actions = n.state.sequenceOfActions.toCharArray();
		}

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

		imgTime = time = millis();
		frame.setLocation(50, 50);
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
			kidx = 0;
			break;
		case 'P':
			state = state.pick();
			break;
		}
	}

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
					fill(200);					
				}
				rect(j * cellLength + horizontalOffset, i * cellLength + verticalOffset, cellLength, cellLength);
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

	public static void main(String[] args)
	{
		PApplet.main("main.Main");
	}
}