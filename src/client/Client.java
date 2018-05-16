/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class Client extends JFrame implements Runnable,ActionListener{
    public Box boxV1,boxV2,boxV3,boxV4,boxBase;
    Panel panel;
    //PoliceText sendListener;
    JScrollPane scroll;
    JButton contectButton,sendButton;
    JLabel biaoqian1,biaoqian2,biaoqian3,say;
    JTextField ipAddress,portAddress,send;
    JTextArea receive;
    Socket socket=null;
    DataInputStream in=null;
    DataOutputStream out=null;
    Thread threadListen,threadSend;
    public Client(){
        super("客户端");
        setVisible(true);
       // setLookAndFeel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        init();
        FlowLayout flo = new java.awt.FlowLayout();
        setLayout(flo);
       // flo.setAlignment(FlowLayout.LEFT);
    }
    void init(){
       // setLayout(new FlowLayout());
       
        boxV1 = Box.createHorizontalBox();
        biaoqian1=new JLabel("客户端设置:");
        panel=new Panel();
        //BorderLayout bord=new BorderLayout();
        //bord.add
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(biaoqian1);
        boxV1.add(panel);
        
        boxV2 = Box.createHorizontalBox();
        biaoqian3=new JLabel("Server IP:");
        boxV2.add(biaoqian3);
        ipAddress=new JTextField(10);
        ipAddress.setText("input the IP");
        boxV2. add(ipAddress);
        biaoqian2=new JLabel("Server Port:");
        boxV2.add(biaoqian2);
        boxV2.add(Box.createHorizontalStrut(8));
        portAddress=new JTextField(10);
        portAddress.setText("input the port");
        boxV2. add(portAddress);
        boxV2.add(Box.createHorizontalStrut(8));
        contectButton=new JButton("Contect");
        boxV2.add(contectButton);
        boxV3 = Box.createHorizontalBox();
        receive= new JTextArea(24,63);
        scroll=new JScrollPane(receive);
        boxV3.add(scroll);
        boxV4 = Box.createHorizontalBox();
        say=new JLabel("Say:");
        boxV4.add(say);
        boxV4.add(Box.createHorizontalStrut(8));
        send=new JTextField(20);
        boxV4.add(send);
        boxV4.add(Box.createHorizontalStrut(8));
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        //sendListener=new PoliceText();
        //sendButton.addActionListener(sendListener);
        //sendListener.setJTextField(send);
        //sendListener.setJTextAreaField(receive);
        boxV4.add(sendButton);
        boxBase = Box.createVerticalBox();
        boxBase.add(boxV1);
        boxBase.add(Box.createVerticalStrut(10));
        boxBase.add(boxV2);
        boxBase.add(Box.createVerticalStrut(10));
        boxBase.add(boxV3);
        boxBase.add(Box.createVerticalStrut(10));
        boxBase.add(boxV4);
        add(boxBase);
        
        sendButton.addActionListener(this);
        contectButton.addActionListener(this);
        threadListen = new Thread(this);
        socket = new Socket();
       // threadSend = new Thread(this);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==contectButton){
            try{
                if(socket.isConnected()){
                    receive.append('\n' +"Contected");
                }
                {
                    receive.append('\n' + "Server starting...");
                    String ip=(ipAddress.getText());
                    int port=Integer.parseInt(portAddress.getText());
                    InetSocketAddress socketAddress=new InetSocketAddress(ip,port);
                    socket.connect(socketAddress);
                    sendButton.setEnabled(true);
                    if(!(threadListen.isAlive())){
                        threadListen = new Thread(this);
                        
                    }
                    threadListen.start();
                }                          
            }
            catch(Exception ee){
                receive.append('\n'+ "808 Error");
                socket = new Socket();
            }
        }
        if(e.getSource()==sendButton){
            String message = send.getText();
            try{
                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(message);
                receive.append("\n");
                receive.append('\n' + "帅的人(我): " +  message);
                send.setText(null);
            }
            catch(IOException e1){
                receive.append('\n' + "Send Error");
            }
        }
    }
    public void sender(){
        
    }
    public void listener(){
        receive.append('\n'+ "Contected");
        String message = null;
        while(true){
            try{
                in = new DataInputStream(socket.getInputStream());
                
                message = in.readUTF();
                receive.append("\n");
                receive.append('\n' + "丑的人: " + message);
            }
            catch(IOException e){
                receive.append('\n'+ "Contection Break");
                return;
            }
        }
    }
    @Override
    public void run(){
        if(Thread.currentThread()==threadListen){
            listener();
        }
        /*if(Thread.currentThread()==threadSend){
            sender();
        }*/
    }
    public static void main(String[] args){
        Client mf1=new Client();
        mf1.setBounds(260,100,800,580);
    }
}

