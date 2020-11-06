package simulation;

import processing.core.PApplet;

public class Simulation extends PApplet {

    private static final float VISCOSITY = 0f; // Simulation constants
    private static final int SIM_SIZE = 500;
    private static final int SCALE = 2;
    private static final float[] WATER_COLOR = {0, 45, 145};
    private static final float AIR_FRICTION = 0.5f;
    private static final float GRAVITY = 1;

    private Fluid fluid; // Fluid class instance

    private boolean paused = true;

    public void settings() {            // Initial settings method to set as an example: size of screen
        size(SIM_SIZE * SCALE, SIM_SIZE * SCALE);
    }

    public void setup() {  // Method to go after settings, that allows you to initialize other values as well
        fluid = new Fluid(VISCOSITY, SIM_SIZE, AIR_FRICTION, GRAVITY); // Initializing fluid class instance
        noStroke();
    }

    public void draw() {  // Method that runs the main thread
        renderFluid();

        if(!paused) {
            fluid.step();
        }
    }

    @Override
    public void mouseDragged() {
        float newVelX = pmouseX - mouseX;
        float newVelY = pmouseY - mouseY;

        fluid.addXModifier(constrain(newVelX, -4, 4));
        fluid.addYModifier(constrain(newVelY, -4, 4));
    }

    @Override
    public void keyPressed() {
        if(keyCode == 32) {
            paused = !paused;
        }
        if(keyCode == RIGHT && paused) {
            fluid.step();
        }
    }

    private void renderFluid() {

        background(100, 120, 200);

        float[][][] fluidGrid = fluid.getFluidGrid(); // Getting the fluid grid from the Fluid class for easy use

        for(int i = 0; i < SIM_SIZE; i++) {           // Iterating over every grid space
            for(int j = 0; j < SIM_SIZE; j++) {

                if(fluidGrid[i][j][0] != fluid.getEmptyValue()) { // If the current space is not empty, render a square representing fluid
                    fill(WATER_COLOR[0], WATER_COLOR[1], WATER_COLOR[2]);
                    rect(j * SCALE, i * SCALE, SCALE, SCALE);
                }

            }
        }
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[] {Simulation.class.getName()};
        PApplet.main(appletArgs);
    }
}
