//I 12-9: 517 (534 / 728)
//程序清单12-9说明了如何将一个Action对象添加到菜单和工具栏中。
//注意，动作名在菜单中就是菜单项名，而在工具栏中就是简短的说明。
package toolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolBarFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new ToolBarFrame();
            frame.setTitle("ToolBarFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public ToolBarFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        //add a panel for color change

        panel = new JPanel();
        add(panel, BorderLayout.CENTER);

        //set up actions

        Action blueAction = new ColorAction("Blue", new ImageIcon("blue-ball.gif"),Color.BLUE);
        Action yellowAction = new ColorAction("Yellow", new ImageIcon("yellow-ball.gif"),Color.YELLOW);
        Action redAction = new ColorAction("Red", new ImageIcon("red-ball.gif"),Color.RED);

        Action exitAction = new AbstractAction("Exit",new ImageIcon("exit.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        exitAction.putValue(Action.SHORT_DESCRIPTION,"Exit");

        // populate toolbar

        JToolBar bar = new JToolBar();
        bar.add(blueAction);
        bar.add(yellowAction);
        bar.add(redAction);
        bar.addSeparator();
        bar.add(exitAction);
        add(bar,BorderLayout.NORTH);

        //populate menu

        JMenu menu = new JMenu("Color");
        menu.add(yellowAction);
        menu.add(blueAction);
        menu.add(redAction);
        menu.add(exitAction);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * The color action sets the background of the frame to a given color.
     */
    class ColorAction extends AbstractAction{
        public ColorAction(String name,Icon icon,Color c){
            putValue(Action.NAME,name);
            putValue(Action.SMALL_ICON,icon);
            putValue(Action.SHORT_DESCRIPTION,name+" background");
            putValue("Color", c);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("Color");
            panel.setBackground(c);
        }
    }
}
