import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven, 2119719 on 2/24/2017.
 */
public class Cloth {

    public Cloth(Point2D location, int width, int height, int size, VerletEngine engine)
    {
        Particle[][] parts = new Particle[width][height];
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Particle p = new Particle(new Point2D.Double(
                        location.getX() + size * x,
                        location.getY() + size * y),
                        engine.gravity,
                        engine.force
                );
                engine.particles.add(p);
                parts[x][y] = p;
            }
        }

        for(int x = 0; x < width; x++)
            for(int y = 0; y < height-1; y++)
                engine.constraints.add(new DistanceConstraint(parts[x][y], parts[x][y+1]));

        for(int x = 0; x < width-1; x++)
            for(int y = 0; y < height; y++)
                engine.constraints.add(new DistanceConstraint(parts[x][y], parts[x+1][y]));

        for(int x = 0; x < width; x+=2)
            engine.constraints.add(new FixedConstraint(parts[x][0]));

    }
}
