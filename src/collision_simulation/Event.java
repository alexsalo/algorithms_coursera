package collision_simulation;

/***************************************************************************
 *  An event during a particle collision simulation. Each event contains
 *  the time at which it will occur (assuming no supervening actions)
 *  and the particles a and b involved.
 *
 *    -  a and b both null:      redraw event
 *    -  a null, b not null:     collision with vertical wall
 *    -  a not null, b null:     collision with horizontal wall
 *    -  a and b both not null:  binary collision between a and b
 *
 ***************************************************************************/
public class Event implements Comparable<Event> {
    private double time;
    private Particle a, b;
    private int countA, countB; // collision counts
    
    public Event(double t, Particle a, Particle b){
        this.time = t;
        this.a    = a;
        this.b    = b;
        if (a != null) countA = a.count();
        else           countA = -1;
        if (b != null) countB = b.count();
        else           countB = -1;
    }
    
    public int compareTo(Event that){
        if (this.time > that.time)
            return 1;
        else if (this.time < that.time)
            return -1;
        else
            return 0;
    }

    // invalid if collision interviened
    public boolean isValid(){
        if (a != null && a.count() != countA) return false;
        if (b != null && b.count() != countB) return false;
        return true;
    }    
    
    public Particle a(){
        return a;
    }
    
    public Particle b(){
        return b;
    }
    
    public double time(){
        return time;
    }
}
