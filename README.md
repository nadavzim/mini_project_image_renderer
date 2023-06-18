# ISE5783_1997_1609
made by  ynon haiun and nadav Zimmerman 2023

## About The Project
A render engine used to create 3-dimensional graphical scene using ray tracing, supersampling, threadpoll multi-threading. 
based in AGILE development, TDD, RDD and Design Patterns as Compositem Builder, Wrapper pattern and more.
in the project we used graphic techniques such as Backward Ray Tracing, Phong model and more.

## progect structure:
![image](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/9b121bad-5c24-43fd-9ad8-e8df1508ab79)

**Primitivs**: the basic primitivs to create the calculation of all the scene soch as add, subtract, dot and cross product and more
* Point
* Vector
* Ray
* Color
* Material
* Util

**Geometries** -the shapes of the scene,
* Poligon
* Sphere
* Triangle
* Tube
* Plane

**Lighting**:
* directional-light - mimic son-light behave
* point-light - light to all direction from p0 point
* spot-light - light in given vector direction from p0 point
* ambient-light - a permanent default color at the scene 

**rendrer**:
* camera - the position and the angle of the scene
* rayTracer - calc the precise color of each pixel
* imageWriter - write the scene with the color to the finel jpag file


## picture improvements
### Anti-aliasing:
a technique that smooths the edges in our scene. the main point of AA is to cast beam of rays from each pixel instead of only one ray,
and calc the average color of the beam at each pixel.
more info <a href="https://en.wikipedia.org/wiki/Anti-aliasing" title="AA">at this link</a>

### soft-shadow: 
  soft shadows are a type of shadow effect used in computer graphics to create more realistic and natural-looking shadows.
  its also uses beam of race but in this case the beam cast from the shape to the light source in order to get the precise shade at the point.
  more info <a href="[https://en.wikipedia.org/wiki/Anti-aliasing](https://www.cineversity.com/wiki/Soft_shadows/)" title="AA">at this link</a>

## run-time improvements


