package simulation;

import structures.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

public class CollisionSystem {
    private MinPQ<Event> pq;
    private double t = 0.0; // simulation clock time
    private double hz = 0.5;        // number of redraw events per clock tick
    private Particle[] particles;
    //private int N;

    public CollisionSystem(Particle[] particles) {
        if (particles == null)
            throw new NullPointerException();
        this.particles = particles;
        //this.N = particles.length;
    }

    private void predict(Particle a) {
        if (a == null) return;

        // particle-particle collisions
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            pq.insert(new Event(t + dt, a, particles[i]));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        pq.insert(new Event(t + dtX, a, null));
        pq.insert(new Event(t + dtY, null, a));
    }

    public void simulate(){
     // initialize PQ with collision events and redraw event
        pq = new MinPQ<Event>();
        for (int i = 0; i < particles.length; i++) {
            predict(particles[i]);
        }
        pq.insert(new Event(0, null, null));        // redraw event


        // the main event-driven simulation loop
        while (!pq.isEmpty()) { 

            // get impending event, discard if invalidated
            Event e = pq.delMin();
            if (!e.isValid()) continue;
            Particle a = e.a();
            Particle b = e.b();

            // physical collision, so update positions, and then simulation clock
            for (int i = 0; i < particles.length; i++)
                particles[i].move(e.time() - t);
            t = e.time();

            // process event
            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) redraw();               // redraw event

            // update the priority queue with new collisions involving a or b
            predict(a);
            predict(b);
        }
    }

    private void redraw() {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show(50);
        pq.insert(new Event(t + 1.0 / hz, null, null));
    }

    public static void main(String[] args) {
        int N = 5;
        Particle[] particles = new Particle[N];
        for (int i = 0; i < N; i++)
            particles[i] = new Particle();

        CollisionSystem simulation = new CollisionSystem(particles);
        simulation.simulate();
    }

}
