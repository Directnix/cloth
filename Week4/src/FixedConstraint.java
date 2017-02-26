import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven, 2119719 on 2/22/2017.
 */
public class FixedConstraint extends Constraint {
    private Particle particle;
    private Point2D location;

    public FixedConstraint(Particle particle) {
        super();
        this.particle = particle;
        location = particle.getLocation();
    }

    @Override
    public void satisfy() {
        particle.setLocation(location);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    public Particle getParticle() {
        return particle;
    }
}
