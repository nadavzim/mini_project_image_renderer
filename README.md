# ISE5783_1997_1609
made by  ynon haiun and nadav Zimmerman 2023

## final product:
![final scene soft+aa](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/5c36cbb3-c128-443a-9111-1c479d1f8ec9)
![balls picture](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/9565c98a-c978-4268-9c46-d21d7ac9aceb)
![teapot](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/ab514555-1c43-4734-850d-b5b03b5b08f8)
![softShadow](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/75c693ca-3e87-4caf-a92e-bde0a2bda0ab)
![AA without soft shadow ](https://github.com/ynon123/ISE5783_1997_1609/assets/94478672/fd38ef87-bf15-482d-af39-c7da771b3dca)


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
#### Anti-aliasing:
a technique that smooths the edges in our scene. the main point of AA is to cast beam of rays from each pixel instead of only one ray,
and calc the average color of the beam at each pixel.
more info <a href="https://en.wikipedia.org/wiki/Anti-aliasing" title="AA">at this link</a>
#### soft-shadow: 
  soft shadows are a type of shadow effect used in computer graphics to create more realistic and natural-looking shadows.
  its also uses beam of race but in this case the beam cast from the shape to the light source in order to get the precise shade at the point.
  more info <a href="https://www.cineversity.com/wiki/Soft_shadows/" title="AA">at this link</a>

## run-time improvements:
#### bounding volume hierarchy (BVH):
BVH is a tree structure on a set of geometric shapes that dramatically decrease the number of the intesections between the bodies in the scene.
in our pictures it reduced the run-time of the scene in a ratio start from  1:17 ,until  1:20 , acorrding to the specific scene.
<https://en.wikipedia.org/wiki/Bounding_volume_hierarchy>
#### Multithreading:
Multithreading is the ability of a central processing unit (CPU) (or a single core in a multi-core processor) to provide multiple threads of execution concurrently.
in our pictures it shortened the run-time in ratio of 1:2.5





