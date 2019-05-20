//I 12-1: 478(495/728)
//程序清单 12-1是计算器程序的源代码。
//这是一个常规的计算器，而不像Java指南中提到的“逆波兰”那样古怪。
//在这个程序中，将组件添加到框架后，调用了pack方法。
//这个方法使用所有组件的最佳大小来计算框架的高度和宽度。
package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends JPanel{
    private JButton display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;

    CalculatorPanel() {
        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start = true;

        //add the display
        display = new JButton("O");
        display.setEnabled(false);
        add(display,BorderLayout.NORTH);

        ActionListener insert= new InsertAction();
        ActionListener command = new CommandAction();

        //add the buttons in a 4*4 grid
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,4));

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4",insert);
        addButton("5",insert);
        addButton("6",insert);
        addButton("*",command);

        addButton("1",insert);
        addButton("2",insert);
        addButton("3",insert);
        addButton("-",command);

        addButton("0",insert);
        addButton(".",insert);
        addButton("=",command);
        addButton("+",command);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new CalculatorFrame();
            frame.setTitle("CalculatorFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * Adds a button to the center panel.
     * @param label the button label
     * @param listener the button listener
     */
    private void addButton(String label, ActionListener listener){
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * This action inserts the button action string to the end of the display text.
     */
    private class InsertAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if(start){
                display.setText("");
                start = false;
            }
            display.setText(display.getText()+input);
        }
    }

    /**
     * This action executes the command that the button action string denotes.
     */
    private class CommandAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if(start){
                if(command.equals(".")){
                    display.setText(command);
                    start = false;
                }
                else lastCommand = command;
            }
            else{
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }

    public void calculate(double x){
        if(lastCommand.equals("+")) result+=x;
        else if(lastCommand.equals("-")) result-=x;
        else if(lastCommand.equals("*")) result*=x;
        else if(lastCommand.equals("/")) result/=x;
        else if(lastCommand.equals("=")) result=x;
        display.setText(""+result);
    }
}

//自己加的：
class CalculatorFrame extends JFrame{
    CalculatorFrame() {
        CalculatorPanel calculatorPanel = new CalculatorPanel();
        add(calculatorPanel);
        pack();
    }
}