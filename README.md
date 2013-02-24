Global Optimization AT
======================

GOAT is a framework/sandbox designed for the purpose of testing global optimization algorithms. 
The application is currently split in 2 main components:

The first component has as a main purpose to show how the algorithms work, by displaying in real time the evolution of candidate solutions. It makes use of a fancy 3D function plotter. Unfortunately, only 2D functions can be visually represented, and as a consequence this component only allows for these types of functions to be examined. 

The second component should be considered for more advanced benchmarking duties. It lacks any visualization aids but instead it can examine any function. Moreover, this component allows for massive simulations to run on multiple threads. Furthermore, simulation results obtained here are much more detailed than those got when using the first component.
In the near future these detailed results will be exported in CSV, XML and JSON formats, which means that you will be able to process them in any statistics language, such as R.

You can code your own optimization algorithm in any language that runs on the JVM and can be interfaced with Java, then pack it as a .jar and drop it in the factories/ folder to be loaded into GOAT. 

At the moment, GOAT comes with implementations for Genetic Algorithms, Particle Swarm Optimization and Random Search. GA and PSO have several flavours to accompany them. Here is the complete list of implemented features:

Genetic Algorithms:
-------------------

* Tournament and Roulette wheel selection
* Singlepoint, 2-point and uniform crossover
* Uniform mutation
* Population reduction
* Random immigrants
* Inversion
* Biased crossover (inheriting the significant part of a chromosome from the better parent)
* Non-uniform mutation with dynamic parameters
* Growth (a hillclimbing step each generation)
* Some methods to adjust selection pressure at runtime ("Damping functions")

Particle Swarm Optimization:
----------------------------

* Neighbor networks
* Population reduction
* Random immigrants

Version history:
================

+ **0.1.0**  
Initial launch

+ **0.1.1**  
Optimized rendering using display lists

+ **0.2.0**  
Added the benchmarking module

+ **0.3.0**  
Added the "Random Searcher" algorithm  
Restructured the entire application  
New plug-in system works but is not used  
Various small fixes and changes

+ **0.3.1**  
Made full use of the plugin system