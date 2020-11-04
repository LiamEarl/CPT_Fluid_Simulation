package math;

public class Vector2f {
    private float x, y; //                x and y values for the 2 dimensional vector


    //                                    Constructors

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        this.x = 0;
        this.y = 0;
    }


    //                                     Addition And Subtraction

    public Vector2f add(float amount) { return new Vector2f(this.x + amount, this.y + amount); }

    public Vector2f subtract(float amount) { return new Vector2f(this.x - amount, this.y - amount); }

    public Vector2f addVector(Vector2f toAdd) { return new Vector2f(this.x + toAdd.getX(), this.y + toAdd.getY()); }

    public Vector2f subtractVector(Vector2f toSubtract) { return new Vector2f(this.x - toSubtract.getX(), this.y - toSubtract.getY()); }


    //                                     Multiplication And Division

    public Vector2f multiplyBy(float amount) {
        return new Vector2f(this.x * amount, this.y * amount);
    }

    public Vector2f divideBy(float amount) {
        return new Vector2f(this.x / amount, this.y / amount);
    }


    //                                     Magnitude And Normalisation

    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2f normalise() {
        float vectorLength = this.magnitude();
        return new Vector2f(this.x / vectorLength, this.y / vectorLength);
    }


    //                                     Dot Product

    public float dotProduct(Vector2f toCompare) {
        return this.x * toCompare.getX() + this.y * toCompare.getY();
    }


    //                                     Getters And Setters

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
