//I 12-17: 552 (569 / 728)
//程序清单12-17 是测试程序框架类的代码。
package dialog;

import javax.swing.*;
import java.awt.*;

public class DialogFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private AboutDialog dialog;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new DialogFrame();
            frame.setTitle("DialogFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public DialogFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        //construct a File menu.

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        //add About and Exit menu items.

        //the About item shows the About Dialog.

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(event -> {
            if(dialog == null)//first time
                dialog = new AboutDialog(DialogFrame.this);
            dialog.setVisible(true);//pop up dialog
        });
        fileMenu.add(aboutItem);

        //The Exit item exits the program.

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);
    }
}
