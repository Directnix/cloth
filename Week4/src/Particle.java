import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven, 2119719 on 2/22/2017.
 */
public class Particle {

    Point2D location;
    Point2D prevLocation;
    int size;
    double gravity;
    double force;

    Particle(Point2D location, double gravity, double force) {
        this.location = location;
        this.prevLocation = location;
        this.size = 20;
        this.gravity = gravity;
        this.force = force;
    }

    void draw(Graphics2D g2d) {
        Shape s = new Ellipse2D.Double(
                location.getX() - size / 2,
                location.getY() - size / 2,
                size,
                size
        );

        g2d.setPaint(Color.BLUE);
        g2d.fill(s);
        g2d.setPaint(Color.BLACK);
        g2d.draw(s);
    }

    void update() {
        Point2D tmpLocation = location;
        location = new Point2D.Double(
                location.getX() + (location.getX() - prevLocation.getX()),
                location.getY() + (location.getY() - prevLocation.getY()) + gravity
                );
        prevLocation = tmpLocation;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
