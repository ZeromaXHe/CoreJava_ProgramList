//I 12-4: 491(508/728)
//程序清单12-4是一个用于选择字体大小的完整程序，它演示了单选钮的工作过程。
package radioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RadioButtonFrame extends JFrame{
    private JPanel buttonPanel;
    private ButtonGroup group;
    private JLabel label;
    private static final int DEFAULT_SIZE = 36;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new RadioButtonFrame();
            frame.setTitle("RadioButtonFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public RadioButtonFrame(){
        //add the sample text label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        add(label, BorderLayout.CENTER);

        //add the radio buttons

        buttonPanel = new JPanel();
        group = new ButtonGroup();
        addRadioButton("Small", 8);
        addRadioButton("Medium", 12);
        addRadioButton("Large", 18);
        addRadioButton("Extra Large",36);

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Adds a radio button that sets the font size of the sample text.
     * @param name the string to appear on the button
     * @param size the font size that this button sets
     */
    public void addRadioButton(String name, int size){
        boolean selected = size == DEFAULT_SIZE;
        JRadioButton button = new JRadioButton(name, selected);
        //JRadioButton(String text, boolean selected)创建具有指定文本和选择状态的单选按钮。
        group.add(button);
        buttonPanel.add(button);

        //this listener sets the label font size
        ActionListener listener = event -> label.setFont(new Font("Serif", Font.PLAIN, size));

        button.addActionListener(listener);
    }
}
