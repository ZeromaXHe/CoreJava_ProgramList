//I 11-4: 462(479/728)
//程序清单 11-4
package mouse;

import javax.swing.*;
import java.awt.*;

public class MouseFrame extends JFrame {
    public MouseFrame(){
        add(new MouseComponent());
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new MouseFrame();
            frame.setTitle("MouseFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
