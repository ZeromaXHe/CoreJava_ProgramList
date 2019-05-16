//II 4-5: 220/818
//程序清单4-5的程序对比了可中断套接字和阻塞套接字：服务器将连续发送数字，并在每发送十个数字之后停滞一下。
//点击两个按钮中的一个，都会启动一个线程来连接服务器并打印输出。
//第一个线程使用可中断套接字，而第二个线程使用阻塞套接字。
//如果在第一批的十个数字的读取过程中点击"Cancel"按钮，这两个线程都会中断。
//
//但是，在第一批十个数字之后，就只能中断第一个线程了，第二个线程将保持阻塞直到服务器最终关闭连接。

package interruptible;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * This program shows how to interrupt a socket channel.
 *
 * @author Cay Horstmann
 * @version 1.04 2016-04-27
 */
public class InterruptibleSocketTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class InterruptibleSocketFrame extends JFrame {
    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea messages;
    private TestServer server;
    private Thread connectThread;

    public InterruptibleSocketFrame() {
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        final int TEXT_ROWS = 20;
        final int TEXT_COLUMNS = 60;
        messages = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(messages));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");

        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectInterruptibly();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectInterruptibly: " + e);
                }
            });
            connectThread.start();
        });

        blockingButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectBlocking();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectBlocking: " + e);
                }
            });
            connectThread.start();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event->{
            connectThread.interrupt();
            cancelButton.setEnabled(false);
        });
        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    /**
     * Connects to the test server, using interruptible I/O.
     */
    public void connectInterruptibly() throws IOException{
        messages.append("Interruptible:\n");
        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost",8189))){
            in = new Scanner(channel,"UTF-8");
            while(!Thread.currentThread().isInterrupted()){
                messages.append("Reading ");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally{
            EventQueue.invokeLater(()->{
                messages.append("Channel closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    /**
     * Connects to the test server, using blocking I/O.
     */
    public void connectBlocking() throws IOException{
        messages.append("Blocking:\n");
        try(Socket sock = new Socket("localhost",8189)){
            in = new Scanner(sock.getInputStream(),"UTF-8");
            while(!Thread.currentThread().isInterrupted()){
                messages.append("Reading ");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally{
            EventQueue.invokeLater(()->{
                messages.append("Socket closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    /**
     * A multithreaded server that listens to port 8189 and sends numbers to the client,
     * simulating a hanging server after 10 numbers
     */
    class TestServer implements Runnable{

        @Override
        public void run() {
            try(ServerSocket s = new ServerSocket(8189)){
                while (true){
                    Socket incoming = s.accept();
                    Runnable r = new TestServerHandler(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }
            }
            catch(IOException e){
                messages.append("\nTestServer.run: "+e);
            }
        }
    }

    /**
     * This class handles the client input for one server socket connection.
     */
    class TestServerHandler implements Runnable{
        private Socket incoming;
        private int counter;

        /**
         * Constructs a handler.
         * @param i the incoming socket
         */
        public TestServerHandler(Socket i){
            incoming = i;
        }

        @Override
        public void run() {
            try{
                try{
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream,"UTF-8"),true/*autoFlush*/);
                    while(counter<100){
                        counter++;
                        if(counter<=10) out.println(counter);
                        Thread.sleep(100);
                    }
                }
                finally {
                    incoming.close();
                    messages.append("Closing server\n");
                }
            }catch (Exception e){
                messages.append("\nTestServerHandler.run: "+e);
            }
        }
    }
}
