//I 12-19: 556 (573 / 728)
//程序清单12-19 是程序的框架类，这个程序展示了进出对话框的数据流。
package dataExchange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataExchangeFrame extends JFrame {
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 40;
    private PasswordChooser dialog = null;
    private JTextArea textArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new DataExchangeFrame();
            frame.setTitle("DataExchangeFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public DataExchangeFrame(){
        //construct a File menu

        JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
        JMenu fileMenu = new JMenu("File");
        mBar.add(fileMenu);

        //add Connect and Exit the program

        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.addActionListener(new ConnectAction());
        fileMenu.add(connectItem);

        //The Exit item exits the program

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);

        textArea = new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
    }

    /**
     * The Connect action pops up the password dialog.
     */
    private class ConnectAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //if first time, construct dialog
            if(dialog==null) dialog = new PasswordChooser();

            //set default values
            dialog.setUser(new User("yourname",null));

            //pop up dialog
            if(dialog.showDialog(DataExchangeFrame.this,"Connect")){
                //if accepted, receive user input
                User u = dialog.getUser();
                textArea.append("user name = "+ u.getName() + ", password = " + (new String(u.getPassword()))+"\n");
            }
        }
    }
}
