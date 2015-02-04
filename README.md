(30 previous commits)

This project is for experimenting with terrain in Java. It is rendered with Processing for convenience. I recommend creating a project from source in Eclipse.

TerraGen textures are sampled for 9x9 pixel textures that are rendered in a 3D heightmap with a texture-light shader.

<s>There are two main classes: Main and DiamondSquareTest. In the Main class, simple Perlin noise is made and rendered as a set of islands. For now, the terrain has a constant "seed". There is a sea level and anything below it is rendered as ocean while anyhting above it is land. Use "u" and "j" to raise and lower the sea level, respectively. In the DiamondSquareTest class, terrain made using a iterative (not recursive) diamond-square algorithm based on this article on the subject (http://www.gameprogrammer.com/fractal.html). Use "i" and "o" to zoom in and out.</s>

<s>All terrain classes render from a menu. The main class is now "Game", which launches a new "CivGame" applet with certain parameters.</s>


