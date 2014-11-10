package terraintest;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PShader;
import terrain.*;

public class DiamondSquareTest extends PApplet {

	public double[][] temp;
	public ArrayList<byte[][]> displayTables;
	public int step = 0;
	public int len = 32;
	
	public PShader shader;
	public PImage[][] textures;
	
	public void setup()
	{
		size(1900,1000,P3D);
		//shader = loadShader("fragtest2", "verttest2");
		shader = loadShader("pixfragtest", "pixverttest");
		temp = DiamondSquare.makeTable(50,50,50,50,len+1);

		/*temp[temp.length/2][temp[0].length/2] = 200;
		temp[0][temp[0].length/2] = 50;
		temp[temp.length-1][temp[0].length/2] = 50;
		temp[temp[0].length/2][0] = 50;
		temp[temp[0].length/2][temp.length-1] = 50;*/

		//temp[temp.length/4][temp[0].length/4] = 175;
		//temp[temp.length/4][temp[0].length*3/4] = 175;
		//temp[temp.length*3/4][temp[0].length/4] = 175;
		//temp[temp.length*3/4][temp[0].length*3/4] = 175;
		DiamondSquare ds = new DiamondSquare(temp);
		//ds.diamond(0, 0, 4);
		//displayTables = ds.dS(0, 0, len, 40, 0.7)
		ds.seed(870);
		ds.generate(new double[]{0, 0, len, 40, 0.6});

		Data data = new Data(ds.t,30);
		data.divIndex(0, 0, len);

		//Data.IslandHelper helper = data.islandHelper();
		//ArrayList<ArrayList<Data.IslandHelper.Location>> islands = helper.listIslands;
		//data.divIndex(islands.get(0));
		//System.out.println(ds.t[1][1]);
		frameRate(40);
		strokeWeight(3);
		//shader.set("fraction", 2);
		
		textures = new PImage[len+1][len+1];
		for (int r = 0; r < textures.length; r++)
		{
			for (int c = 0; c < textures[0].length; c++)
			{
				double[][] temp2 = DiamondSquare.makeTable(0,0,0,0,3);
				ds = new DiamondSquare(temp2);
				ds.seed((long)(System.currentTimeMillis()*Math.random()));
				ds.generate(new double[]{0, 0, 2, 255, 0.6});
				textures[r][c] = getBlock(temp2);
			}
		}
	}

	public int zoom = 1500;
	public int stepSpeed = 1;
	public void draw()
	{
		//perspective((float)Math.PI/4,1.9F,0,1000);
		shader(shader);
		camera(zoom,zoom,zoom,0,400,0,0,-1,0);
		background(0);
		displayTable(temp);
		/*displayTable(displayTables.get(step));
		step += stepSpeed;
		if (step >= displayTables.size())
		{
			step = 0;
		}*/
	}

	public void keyPressed()
	{
		if (key == 'i')
		{
			zoom -= 50;
		}
		else if (key == 'o')
		{
			zoom += 50;
		}
		else if (key == 'u')
		{
			stepSpeed++;
		}
		else if (key == 'j')
		{
			if (stepSpeed > 0)
				stepSpeed--;
		}
	}

	public void line(float a, float b, float c, float d, float e, float f)
	{
		strokeWeight(1);
		super.line(a, b, c, d, e, f);
	}

	public void point(float a, float b, float c)
	{
		strokeWeight(3);
		super.point(a, b, c);
	}

	//Removes zeroes and resizes table
	public double[][] fix(double[][] t)
	{
		//Count terms
		/*double n = 0;
		for (int r = 0; r < t.length; r++)
		{
			for (int c = 0; c < t[0].length; c++)
			{
				if (t[r][c] != 0)
					n++;
			}
		}
		double size = Math.pow(n,0.5);
		double[][] temp = new double[(int)Math.pow(n,0.5)][(int)Math.pow(n,0.5)];
		int step = 0;
		for (int r = 0; r < t.length; r++)
		{
			for (int c = 0; c < t[0].length; c++)
			{
				if (t[r][c] != 0)
				{
					println(step%temp.length + "-" + step/temp[0].length);
					temp[step%temp.length][step/temp[0].length] = t[r][c];
					step++;
				}
			}
		}
		return temp;*/
		double[][] temp = new double[t.length][t[0].length];
		BicubicInterpolator bi = new BicubicInterpolator();
		for (int r = 0; r < t.length; r++)
		{
			for (int c = 0; c < t[0].length; c++)
			{
				if (t[r][c] == 0)
					temp[r][c] = bi.getValue(t, r, c);
			}
		}
		return temp;
	}

	public void displayTable(double[][] t)
	{
		float len = 30; float con = 5;
		fill(150); stroke(0);
		/*for (int r = 0; r < t.length; r++)
		{
			for (int c = 0; c < t.length; c++)
			{
				if (t[r][c] == 0) continue;
				if (r == t.length - 1 && c == t[0].length - 1) return;
				else if (r == t.length - 1)
				{
					if (t[r][c+1] != 0)
						line(r*len, (float)t[r][c]*con, c*len, r*len, (float)t[r][c+1]*con, (c+1)*len);
					else
						point(r*len, (float)t[r][c]*con, c*len);
				}
				else if (c == t[0].length - 1)
				{
					if (t[r+1][c] != 0)
						line(r*len, (float)t[r][c]*con, c*len, (r+1)*len, (float)t[r+1][c]*con, c*len);
					else
						point(r*len, (float)t[r][c]*con, c*len);
				}
				else
				{
					if (t[r+1][c+1] != 0)
					{
						line(r*len, (float)t[r][c]*con, c*len, r*len, (float)t[r][c+1]*con, (c+1)*len);
						line(r*len, (float)t[r][c]*con, c*len, (r+1)*len, (float)t[r+1][c]*con, c*len);
						line(r*len, (float)t[r][c]*con, c*len, (r+1)*len, (float)t[r+1][c+1]*con, (c+1)*len);
					}
					else
						point(r*len, (float)t[r][c]*con, c*len);
				}
			}
		}*/
		noStroke();
		//lights();
		//float dirY = ((float)mouseY / (float)height - 0.5F) * 2F;
		float dirX = ((float)mouseX / (float)width - 0.5F) * 2F;
		directionalLight(200, 200, 200, dirX, -1, 0);
		//pointLight(255,255,255,0,500,0);
		for (int r = 0; r < t.length - 1; r++)
		{
			for (int c = 0; c < t[0].length - 1; c++)
			{
				beginShape(TRIANGLES);
				textureMode(IMAGE);
				texture(textures[r][c]);
				//println(textures[r][c].pixels.length);
				vertex(r*len, (float)t[r][c]*con, c*len, 0, 0);
				vertex(r*len, (float)t[r][c+1]*con, (c+1)*len, 0, 2);
				vertex((r+1)*len, (float)t[r+1][c+1]*con, (c+1)*len, 2, 2);
				endShape();
				beginShape(TRIANGLES);
				texture(textures[r][c]);
				vertex(r*len, (float)t[r][c]*con, c*len, 0, 2);
				vertex((r+1)*len, (float)t[r+1][c]*con, c*len, 2, 0);
				vertex((r+1)*len, (float)t[r+1][c+1]*con, (c+1)*len, 2, 2);
				endShape();
			}
		}
		if (frameCount % 20 == 0)
		{
			//System.out.println(-dirX + " " + -dirY);
		}

		/*strokeWeight(5);
		pushMatrix();
		translate(1000,500,1000);
		fill(255);
		sphere(50);
		popMatrix();*/
	}

	public PImage getBlock(double[][] t)
	{
		PImage temp = createImage(t.length,t[0].length,ARGB);
		float gray;
		pushStyle();
		for (int r = 0; r < t.length; r++)
		{
			for (int c = 0; c < t[0].length; c++)
			{
				gray = (float)t[r][c];
				//println(gray);
				if (gray > 255)
					gray = 255;
				else if (gray < 0)
					gray = (float)Math.abs(gray);
				temp.pixels[r*t[0].length + c] = color(gray,gray,gray,255);
			}
		}
		temp.updatePixels();
		popStyle();
		return temp;
	}

	public static class CubicInterpolator {
		public static double getValue(double[] p, double x) {
			int xi = (int) x;
			x -= xi;
			double p0 = p[Math.max(0, xi - 1)];
			double p1 = p[xi];
			double p2 = p[Math.min(p.length - 1,xi + 1)];
			double p3 = p[Math.min(p.length - 1, xi + 2)];
			return p1 + 0.5 * x * (p2 - p0 + x * (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3 + x * (3.0 * (p1 - p2) + p3 - p0)));
		}
	}

	public static class BicubicInterpolator extends CubicInterpolator {
		private double[] arr = new double[4];

		public double getValue(double[][] p, double x, double y) {
			int xi = (int) x;
			x -= xi;
			arr[0] = getValue(p[Math.max(0, xi - 1)], y);
			arr[1] = getValue(p[xi], y);
			arr[2] = getValue(p[Math.min(p.length - 1,xi + 1)], y);
			arr[3] = getValue(p[Math.min(p.length - 1, xi + 2)], y);
			return getValue(arr, x+ 1);
		}
	}

}
