//I 12-3: 489(506/728)
//程序清单12-3给出了复选框例子的全部代码。
package checkBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A frame with a sample text label and check boxes for selecting font attributes.
 */
public class CheckBoxFrame extends JFrame{
    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;
    private static final int FONTSIZE = 24;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new CheckBoxFrame();
            frame.setTitle("CheckBoxFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public CheckBoxFrame(){
        //add the sample text label

        label = new JLabel("The quick brown fox jumps the lazy dog.");
        label.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        add(label, BorderLayout.CENTER);

        //this listener sets the font attribute of the label to the check box state
        ActionListener listener = event ->{
            int mode = 0;
            if(bold.isSelected()) mode += Font.BOLD;//这里+=的用法值得注意。学习一下
            if(italic.isSelected()) mode += Font.ITALIC;//这里+=的用法值得注意。学习一下
            label.setFont(new Font("Serif", mode, FONTSIZE));
        };

        //add the check boxes
        JPanel buttonPanel = new JPanel();

        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        bold.setSelected(true);
        buttonPanel.add(bold);

        italic = new JCheckBox("italic");
        italic.addActionListener(listener);
        buttonPanel.add(italic);

        add(buttonPanel,BorderLayout.SOUTH);
        pack();
    }
}
