import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swt.SWTFXUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven, 2119719 on 2/22/2017.
 */
public class Physics extends JPanel implements ActionListener {

    static JFrame frame = new JFrame("Physics");

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 800));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(new Physics());
        frame.setVisible(true);
    }

    VerletEngine engine = new VerletEngine();
    Particle draggedParticle = null;
    Point2D dragPosition;
    boolean cutting = false;

    public Physics() {
        new Timer(2, this).start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    draggedParticle = engine.getParticleAt(e.getPoint());
                    dragPosition = e.getPoint();

                    if (e.getClickCount() == 2) {
                        for (Constraint c : engine.getConstraints()) {
                            if (c instanceof FixedConstraint) {
                                if (((FixedConstraint) c).getParticle().equals(draggedParticle)) {
                                    engine.removeContraint(c);
                                }
                            }
                        }
                    }

                } else if (SwingUtilities.isRightMouseButton(e)){
                    cutting = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedParticle = null;
                if(SwingUtilities.isRightMouseButton(e)){
                    cutting = false;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dragPosition = e.getPoint();

                if(cutting){
                    engine.removeContraint(engine.getDistanceConstrain(e.getPoint()));
                }
            }
        });

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (draggedParticle != null) {
                    if (e.getKeyCode() == e.VK_CONTROL) {
                        engine.addConstraint(new FixedConstraint(draggedParticle));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        engine.draw(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (draggedParticle != null)
            draggedParticle.setLocation(dragPosition);

        engine.update();
        repaint();
    }
}
