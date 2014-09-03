package render;

import java.util.ArrayList;

import processing.core.PApplet;
import terrain.*;
import terrain.RecursiveBlock.Entity;

public class CivGame extends PApplet {

	public Game game;
	public BaseTerrain map;
	public String terrainType;
	public double[][] terrain;

	public CivGame(Game game, String terrainType)
	{
		this.game = game;
		this.terrainType = terrainType;
	}

	public void setup()
	{
		size(1500,900,P3D); //TODO: Processing will not take variables for size(); use a JFrame/PFrame w/ embedded applet to work around this
		generate(terrainType);
		for (int r = 0; r < terrain.length; r++)
		{
			for (int c = 0; c < terrain[0].length; c++)
			{
				print((int)terrain[r][c] + " ");
			}
			println();
		}
	}

	public void draw()
	{		
		background(150,225,255);
		smooth(4);
		//background(background);
		lights();
		noStroke();
		//stroke(0);
		fill(135, 206, 235);
		perspective(3.14F/2,15F/9F,1,10000);
		camera(500,500,500,0,0,0,0,-1,0);
		int widthBlock = 10;
		int dist = 500;
		for (int r = 0; r < terrain.length; r++)
		{
			for (int c = 0; c < terrain[0].length; c++)
			{
				double height = terrain[r][c];
				//float dist = dist(player.posX,player.posZ,r*widthBlock,c*widthBlock);
				float con = (3F/10F)*widthBlock;
				noStroke();
				//println(con);
				if (dist > widthBlock*50)
				{
					int sampleSize = 2;
					if (dist > widthBlock*150) sampleSize = 4;
					if (height > 1 && r % sampleSize == 0 && c % sampleSize == 0)
					{
						pushMatrix();
						translate(r*widthBlock, (float)height/2F*con, c*widthBlock);
						box(widthBlock*sampleSize, (float)height*con, widthBlock*sampleSize);
						//println((int)height);
						popMatrix();
					}
				}
				else
				{
					if (dist <= width*15)
					{
						stroke(0);
					}
					else
					{
						noStroke();
					}
					if (height >= 1)
					{
						pushMatrix();
						translate(r*widthBlock, ((float)height/2F*con), c*widthBlock);
						box(widthBlock, ((float)height*con), widthBlock);
						//println((int)height);
						popMatrix();
					}
				}
			}
		}
	}

	public void stop()
	{
		println("hi");
		game.exit();
		//super.stop();
	}

	public void generate(String terrainType)
	{
		if (terrainType.equals("terrain1"))
		{
			map = new PerlinNoise(870L);
			terrain = map.generate(new double[]{64,64,150,8,1,0.8,6,256});
		}
		else if (terrainType.equals("terrain2"))
		{
			map = new RecursiveBlock(87069200L);
			terrain = map.generate(new double[]{10});
		}
		else if (terrainType.equals("terrain3"))
		{
			int len = 128;
			double[][] temp = DiamondSquare.makeTable(50,50,50,50,len+1);
			map = new DiamondSquare(temp);
			//ds.diamond(0, 0, 4);
			//displayTables = ds.dS(0, 0, len, 40, 0.7)
			map.seed(870);
			terrain = map.generate(new double[]{0, 0, len, 40, 0.7});
			print(terrain);
		}
		else
		{
			println("No map!");
		}
	}

}
