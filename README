Global Optimization AT
======================

GOAT is a framework/sandbox designed for the purpose of testing global optimization algorithms. 
The application is currently split in 2 main components:

The first component comes with a plotter that displays the solutions in the search space as algorithms do their job. This component is mainly used for providing a visual representation of the candidate solutions in the search space.
Unfortunately only 2D functions can be plotted, thus only allowing for 2D functions to be used as benchmarks.
This component also offers some basic benchmarking functionality.

The second component lacks any visualization aids but makes up for it by having richer benchmarking functionality.
First, massive simulations run on multiple threads, unlike in the first component. Thus, this component offers much more detailed simulation results.
In the near future these detailed results will be exported in CSV, XML and JSON formats, which means that you will be able to process them in any statistics language, such as R.

At the moment implementations for Genetic Algorithms and Particle Swarm Optimization are included. They also come with several variations and options. Here is a complete list of the features implemented:

For Genetic Algorithms:
-----------------------

. Tournament and Roulette wheel selection
. Singlepoint, 2-point and uniform crossover
. Uniform mutation
. Population reduction
. Random immigrants
. Inversion
. Biased crossover (inheriting the significant part of a chromosome from the better parent)
. Non-uniform mutation with dynamic parameters
. Growth (a hillclimbing step each generation)
. Some methods to adjust selection pressure at runtime ("Damping functions")

For Particle Swarm Optimization:
--------------------------------

. Neighbor networks
. Population reduction
. Random immigrants