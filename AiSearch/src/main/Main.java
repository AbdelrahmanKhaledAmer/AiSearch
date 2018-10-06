package main;

import game.*;
import searchAI.*;
import processing.core.PApplet;

public class Main extends PApplet
{
	int gridWidth = 4;
	int gridHeight = 4;

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
		Node n = Search.BFS(g);
		System.out.println(n.state.sequenceOfActions);
		cellLength = ((float)width - 2 * horizontalOffset) / gridWidth;
		verticalOffset = ((float)height - cellLength * gridHeight) / 2;
	}

	public void draw()
	{
		background(0);
		fill(255);
		stroke(255);
		for(float i = 0; i < gridWidth + 1; i++)
		{
			line(i * cellLength + horizontalOffset, verticalOffset, i * cellLength + horizontalOffset, height - verticalOffset);
		}
		for(float i = 0; i < gridHeight + 1; i++)
		{
			line(horizontalOffset, i * cellLength + verticalOffset, width - horizontalOffset, i * cellLength + verticalOffset);
		}
		//noLoop();
	}
	
	public static void main(String[] args)
	{
		PApplet.main("main.Main");
	}
}