//I 12-18: 553 (570 / 728)
//程序清单12-18 显示了对话框类。
package dialog;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owener){
        super(owener,"About DialogTest", true);

        //add HTML label to center

        add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann</html>"), BorderLayout.CENTER);

        //OK button closes the dialog

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> setVisible(false));

        //add OK button to southern border

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        pack();
    }
}
