//I 12-14: 540 (557 / 728)
//程序清单12-14
package circleLayout;

import javax.swing.*;
import java.awt.*;

public class CircleLayoutFrame extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new CircleLayoutFrame();
            frame.setTitle("CircleLayoutFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    public CircleLayoutFrame(){
        setLayout(new CircleLayout());
        add(new JButton("Yellow"));
        add(new JButton("Blue"));
        add(new JButton("Red"));
        add(new JButton("Green"));
        add(new JButton("Orange"));
        add(new JButton("Fuchsia"));
        add(new JButton("Indigo"));
        pack();
    }
}
