import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Nick van Endhoven, 2119719 on 2/22/2017.
 */
public class VerletEngine {

    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Constraint> constraints = new ArrayList<>();

    double gravity, force;

    VerletEngine() {
        this.gravity = 0.1;
        this.force = 0.2;

        addCloth(new Point2D.Double(50, 50), 31, 15, 50);
    }

    public void addCloth(Point2D location, int width, int height, int size) {
        new Cloth(location, width, height, size, this);
    }

    public Particle getParticleAt(Point2D point) {
        for (Particle p : particles)
            if (p.getLocation().distance(point) < p.getSize() / 2)
                return p;
        return null;
    }

    public DistanceConstraint getDistanceConstrain(Point2D point) {
        for (Constraint c : constraints) {
            if (c instanceof DistanceConstraint) {
                DistanceConstraint dc = (DistanceConstraint) c;

                Point2D p1 = dc.getP1().getLocation();
                Point2D p2 = dc.getP2().getLocation();

                double highestX, lowestX;
                if (p1.getX() >= p2.getX()) {
                    highestX = p1.getX();
                    lowestX = p2.getX();
                } else {
                    highestX = p2.getX();
                    lowestX = p1.getX();
                }

                double highestY, lowestY;
                if (p1.getY() >= p2.getY()) {
                    highestY = p1.getY();
                    lowestY = p2.getY();
                } else {
                    highestY = p2.getY();
                    lowestY = p1.getY();
                }

                if (point.getX() >= lowestX && point.getX() <= highestX) {
                    if (point.getY() >= lowestY && point.getY() <= highestY) {
                        return dc;
                    }
                }
            }
        }
        return null;
    }

    public void addConstraint(Constraint c) {
        constraints.add(c);
    }

    void update() {
        for (Particle p : particles)
            p.update();

        for (Constraint c : constraints)
            c.satisfy();
    }

    void draw(Graphics2D g2d) {
        for (Particle p : particles)
            p.draw(g2d);

        for (Constraint c : constraints)
            c.draw(g2d);
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void removeContraint(Constraint rc) {
        ArrayList<Constraint> cs = new ArrayList<>();
        for (Constraint c : constraints) {
            if (!c.equals(rc)) {
                cs.add(c);
            }
        }
        constraints = cs;
    }
}
