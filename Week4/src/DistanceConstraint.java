import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven, 2119719 on 2/22/2017.
 */
public class DistanceConstraint extends Constraint {
    private Particle p1,p2;
    private double distance;
    private double rigidity = 1;

    public DistanceConstraint(Particle p1, Particle p2){
        super();
        this.p1 = p1;
        this.p2 = p2;
        distance = p1.getLocation().distance(p2.getLocation());
    }

    public double getDistance() {
        return distance;
    }

    public Particle getP1() {
        return p1;
    }

    public Particle getP2() {
        return p2;
    }

    @Override
    public void satisfy() {
        double currentDistance = p1.getLocation().distance(p2.getLocation());
        double toMove = (currentDistance - distance) / 2.0;

        toMove *= rigidity;

        Point2D difference = new Point2D.Double(
                (p1.getLocation().getX() - p2.getLocation().getX()) / currentDistance,
                (p1.getLocation().getY() - p2.getLocation().getY()) / currentDistance
        );

        p1.setLocation(new Point2D.Double(
                p1.getLocation().getX() - toMove * difference.getX(),
                p1.getLocation().getY() - toMove * difference.getY()));

        p2.setLocation(new Point2D.Double(
                p2.getLocation().getX() + toMove * difference.getX(),
                p2.getLocation().getY() + toMove * difference.getY()));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(new Line2D.Double(p1.getLocation(),p2.getLocation()));
    }
}
