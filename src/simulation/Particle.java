package simulation;

import java.awt.Color;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;

public class Particle {
    private final double INFINITY = Double.POSITIVE_INFINITY;

    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double mass;
    private final double radius;
    private Color color; // color
    private int count; // number of collisions

    public Particle() {
        Random r = new Random();
        radius = 0.01 + 0.05 * r.nextDouble();
        mass = radius;
        count = 0;
        color = Color.BLUE;
        rx = radius + r.nextDouble() * (1 - 2 * radius);
        ry = radius + r.nextDouble() * (1 - 2 * radius);
        vx = 0.05 * r.nextDouble();
        vy = 0.05 * r.nextDouble();
    }

    // Prediction
    public double timeToHit(Particle that) {
        if (this == that)
            return INFINITY;
        double dx = that.rx - this.rx;
        double dy = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        if (dvdr > 0)
            return INFINITY;
        double dvdv = dvx * dvx + dvy * dvy;
        double drdr = dx * dx + dy * dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
        if (d < 0)
            return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double timeToHitVerticalWall() {
        return vx > 0 ? (1.0 - radius - rx) / vx : (radius - rx) / vx;
    }

    public double timeToHitHorizontalWall() {
        return vy > 0 ? (1.0 - radius - ry) / vy : (radius - ry) / vy;
    }

    // Collisions Resolution
    public void bounceOff(Particle that) {
        double dx = that.rx - this.rx;
        double dy = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy; // dv dot dr
        double dist = this.radius + that.radius; // distance between particle
                                                 // centers at collison

        // normal force F, and in x and y directions
        double F = 2 * this.mass * that.mass * dvdr
                / ((this.mass + that.mass) * dist);
        double fx = F * dx / dist;
        double fy = F * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    public double kineticEnergy() {
        return 0.5 * mass * (vx * vx + vy * vy);
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }

    public int count() {
        return count;
    }

    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }
}
