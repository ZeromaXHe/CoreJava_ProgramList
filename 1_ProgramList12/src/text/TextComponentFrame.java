//I 12-2: 486(503/728)
//程序清单12-2展示了各种文本组件。
//这个程序只是简单地显示了一个文本域，一个密码域和一个带滚动条的文本区。
//文本域和密码域都使用了标签。
//点击“Insert”会将组件中的内容插入到文本区中。
package text;

import javax.swing.*;
import java.awt.*;

/**
 * A frame with sample text components.
 */
public class TextComponentFrame extends JFrame {
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new TextComponentFrame();
            frame.setTitle("TextComponentFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public TextComponentFrame(){
        JTextField textField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2,2));
        northPanel.add(new JLabel("User name: ",SwingConstants.RIGHT));//SwingConstants.RIGHT 将JLabel放在所处网格右边
        northPanel.add(textField);
        northPanel.add(new JLabel("Password: ",SwingConstants.RIGHT));
        northPanel.add(passwordField);

        add(northPanel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(TEXTAREA_ROWS,TEXTAREA_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane,BorderLayout.CENTER);

        //add button to append text into the text area
        JPanel southPanel = new JPanel();

        JButton insertButton = new JButton("Insert");
        southPanel.add(insertButton);
        insertButton.addActionListener(event->
                textArea.append("User name: "+textField.getText()+"Password: "
                + new String(passwordField.getPassword())+"\n"));

        add(southPanel, BorderLayout.SOUTH);
        pack();
    }
}
