package simulation;

import math.Vector2f;

import static java.lang.Math.round;

public class Fluid {
    private int simulationSize;     // Size of simulation

    private float[][][] fluidGrid;  // Grid to store fluid values

    private float viscosity;        // Viscosity of the fluid

    private float airFriction;      // Friction that the air enacts on all fluid particles

    private float gravity;          // gravity that enacts on all fluid particles

    private static final float EMPTY_VALUE = -59.253f;  // Value that signifies that a grid space is empty


    public Fluid(float viscosity, int simulationSize, float airFriction, float gravity) { // Fluid Constructor

        this.simulationSize = simulationSize;           // Getting the simulation size

        this.viscosity = viscosity;                     // Initialization of values

        this.gravity = gravity;

        this.fluidGrid = new float[simulationSize][simulationSize][]; // Setting the size of the fluidGrid array

        for(int i = 0; i < simulationSize; i++) {       // Populate fluidGrid array with water, or empty space
            for(int j = 0; j < simulationSize; j++) {

                // Top quarter of the simulation filled with an alternating pattern of water
                if(i < round(simulationSize / 4f)) {
                    this.fluidGrid[i][j] = new float[] {0, 0};      // Starting off the fluid with no velocity
                    continue;
                }

                // Populating the position with velocity values that signify that the space is empty
                this.fluidGrid[i][j] = new float[] {EMPTY_VALUE, EMPTY_VALUE};
            }
        }
    }


    public void step() { // Method to run one step iterating through every fluid particle
        moveFluid();
        for(int i = 0; i < simulationSize; i++) {          // Iterating through every grid space
            for(int j = 0; j < simulationSize; j++) {
                if(fluidGrid[i][j][0] == EMPTY_VALUE)      // If the current space is empty don't use it
                    continue;

                fluidGrid[i][j][1] += gravity;
            }
        }
    }


    public float getEmptyValue() { // Method to give the value that signifies an empty space
        return EMPTY_VALUE;
    }


    public float[][][] getFluidGrid() { // Method to give the grid where each fluid particle is stored
        return fluidGrid;
    }


    public void setIndexTo(int i, int j, int xVelocity, int yVelocity) {
        fluidGrid[i][j][0] = xVelocity;
        fluidGrid[i][j][1] = yVelocity;
    }


    public void moveFluid() {
        for(int i = 0; i < simulationSize; i++) {          // Iterating through every grid space
            for(int j = 0; j < simulationSize; j++) {

                if(fluidGrid[i][j][0] == EMPTY_VALUE || fluidGrid[i][j][1] == EMPTY_VALUE)      // If the current space is empty don't use it
                    continue;

                if(fluidGrid[i][j][0] > 0.01) { // If the current particle is going in the right direction
                    if(j > 0) {
                        if(fluidGrid[i][j - 1][0] == EMPTY_VALUE) { // If the space to the right is empty, move to it
                            float vx = fluidGrid[i][j][0], vy = fluidGrid[i][j][1];

                            fluidGrid[i][j - 1][0] = vx;
                            fluidGrid[i][j - 1][1] = vy;
                            fluidGrid[i][j - 1][0] *= airFriction;  // Slow the particle down due to friction

                            fluidGrid[i][j][0] = EMPTY_VALUE;
                            fluidGrid[i][j][1] = EMPTY_VALUE;
                        }else {                                     // If the space to the right is full, exchange velocities
                            fluidGrid[i][j][0] = fluidGrid[i][j - 1][0];
                            fluidGrid[i][j - 1][0] = fluidGrid[i][j][0];
                        }
                    }
                }else if(fluidGrid[i][j][0] < 0.01) { // If the current particle is going in the left direction
                    if(j < simulationSize - 1) {
                        if(fluidGrid[i][j + 1][0] == EMPTY_VALUE) { // If the space to the right is empty, move to it

                            float vx = fluidGrid[i][j][0], vy = fluidGrid[i][j][1];

                            fluidGrid[i][j + 1][0] = vx;
                            fluidGrid[i][j + 1][1] = vy;
                            fluidGrid[i][j + 1][0] *= airFriction;  // Slow the particle down due to friction

                            fluidGrid[i][j][0] = EMPTY_VALUE; // Making the old space empty
                            fluidGrid[i][j][1] = EMPTY_VALUE;
                        }else {                                     // If the space to the left is full, exchange velocities
                            fluidGrid[i][j][0] = fluidGrid[i][j + 1][0];
                            fluidGrid[i][j + 1][0] = fluidGrid[i][j][0];
                        }
                    }
                }

                if(fluidGrid[i][j][1] > 0.01) { // If the current particle is going in the down direction

                    if(i < simulationSize - 1) {

                        if(fluidGrid[i + 1][j][0] == EMPTY_VALUE) { // If the space beneath is empty, move to it
                            float vx = fluidGrid[i][j][0], vy = fluidGrid[i][j][1];

                            fluidGrid[i + 1][j][0] = vx;
                            fluidGrid[i + 1][j][1] = vy;
                            fluidGrid[i + 1][j][0] *= airFriction;  // Slow the particle down due to friction

                            fluidGrid[i][j][0] = EMPTY_VALUE;
                            fluidGrid[i][j][1] = EMPTY_VALUE;
                        }else {                                     // If the space below is full, exchange velocities
                            fluidGrid[i][j][0] = fluidGrid[i + 1][j][0];
                            fluidGrid[i + 1][j][0] = fluidGrid[i][j][0];
                        }
                    }
                }else if(fluidGrid[i][j][1] < 0.01) { // If the current particle is going in the up direction
                    if(i > 0) {
                        if(fluidGrid[i - 1][j][0] == EMPTY_VALUE) { // If the space above is empty, move to it
                            float vx = fluidGrid[i][j][0], vy = fluidGrid[i][j][1];

                            fluidGrid[i - 1][j][0] = vx;
                            fluidGrid[i - 1][j][1] = vy;
                            fluidGrid[i - 1][j][0] *= airFriction;  // Slow the particle down due to friction

                            fluidGrid[i][j][0] = EMPTY_VALUE; // Making the old space empty
                            fluidGrid[i][j][1] = EMPTY_VALUE;
                        }else {                                     // If the space above is full, exchange velocities
                            fluidGrid[i][j][0] = fluidGrid[i - 1][j][0];
                            fluidGrid[i - 1][j][0] = fluidGrid[i][j][0];
                        }
                    }
                }
            }
        }
    }
}
