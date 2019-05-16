//I 11-3: 457(474/728)
//程序清单 11-3 给出了将按钮和按键映射到动作对象的完整程序代码。
//试试看，点击按钮或按下CTRL + Y、CTRL + B或CTRL + R来改变面板颜色
package action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A frame with a panel that demonstrates color change actions.
 */
public class ActionFrame extends JFrame {
    private JPanel buttonPanel;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new ActionFrame();
            frame.setTitle("PlatFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public ActionFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        buttonPanel = new JPanel();

        //define actions
        Action yellowAction = new ColorAction("Yellow",new ImageIcon("yellow-ball.gif"), Color.YELLOW);
        Action blueAction = new ColorAction("Blue",new ImageIcon("blue-ball.gif"), Color.BLUE);
        Action redAction = new ColorAction("Red",new ImageIcon("red-ball.gif"), Color.RED);

        //add buttons for these actions
        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(redAction));

        //add panel to frame
        add(buttonPanel);

        //associate the Y, B, and R keys with names
        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        //associate the names with actions
        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow",yellowAction);
        amap.put("panel.blue",blueAction);
        amap.put("panel.red",redAction);
    }

    public class ColorAction extends AbstractAction{
        /**
         * Constructs a color action.
         * @param name the name to show on the button
         * @param icon the icon to display on the button
         * @param c the background color
         */
        public ColorAction(String name,Icon icon,Color c){
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to "+ name.toLowerCase());
            putValue("color", c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color)getValue("color");
            buttonPanel.setBackground(c);
        }
    }
}
