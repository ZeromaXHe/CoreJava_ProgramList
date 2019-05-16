//I 11-2: 449(466/728)
//程序清单11-2 是一个完整的程序，它演示了如何切换观感的方式。这个程序与程序清单11-1十分相似。
package plat;

import javax.swing.*;
import java.awt.*;

public class PlatFrame extends JFrame {
    private JPanel buttonPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new PlatFrame();
            frame.setTitle("PlatFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public PlatFrame(){
        buttonPanel = new JPanel();

        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for(UIManager.LookAndFeelInfo info:infos){
            makeButton(info.getName(),info.getClassName());
        }

        add(buttonPanel);
        pack();
    }

    /**
     * Makes a button to change the pluggable look-and-feel.
     * @param name the button name
     * @param className the name of the look-and-feel class
     */
    private void makeButton(String name, String className){
        //add button to panel

        JButton button = new JButton(name);
        buttonPanel.add(button);

        //set button action
        button.addActionListener(event->{
            //button action: switch to the new look-and-feel
            try{
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
                pack();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
