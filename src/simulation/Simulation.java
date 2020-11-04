package simulation;

import processing.core.PApplet;

public class Simulation extends PApplet {

    private static final float VISCOSITY = 0f; // Simulation constants
    private static final int SIM_SIZE = 512;
    private static final int SCALE = 2;
    private static final float[] WATER_COLOR = {0, 45, 145};
    private static final float AIR_FRICTION = 0.85f;
    private static final float GRAVITY = 10;

    private Fluid fluid; // Fluid class instance

    public void settings() {            // Initial settings method to set as an example: size of screen
        size(SIM_SIZE * SCALE, SIM_SIZE * SCALE);
    }

    public void setup() {  // Method to go after settings, that allows you to initialize other values as well
        fluid = new Fluid(VISCOSITY, SIM_SIZE, AIR_FRICTION, GRAVITY); // Initializing fluid class instance
        noStroke();
    }

    public void draw() {  // Method that runs the main thread
        fluid.step();
        renderFluid();
    }

    @Override
    public void mouseDragged() {
        int vx = mouseX - pmouseX;
        int vy = mouseY - pmouseY;

        float[][][] fluidGrid = fluid.getFluidGrid(); // Getting the fluid grid from the Fluid class for easy use


        fill(0);
        rect(floor(mouseX / SCALE),  floor(mouseY / SCALE), 10, 10);

        if(floor(mouseX / SCALE) - 1 >= 0 && floor(mouseY / SCALE) - 1 >= 0) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    int curX = floor(mouseX / SCALE) + (j - 1);
                    int curY = floor(mouseY / SCALE) + (i - 1);
                    fluid.setIndexTo(curY, curX, (int) fluidGrid[curY][curX][0] + vx, (int) fluidGrid[curY][curX][1] + vy);
                }
            }
        }
    }

    private void renderFluid() {

        background(255);

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
