package chatting.application;
import java.awt.Color;
import java.awt.Frame.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.attribute.standard.OutputDeviceAssigned;
//import javax.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sun.glass.events.MouseEvent;


public class Client  implements ActionListener{
	JTextField text;
static	JPanel a1;
	 static Box  vertical= Box.createVerticalBox();
	static DataOutputStream dout;
	static JFrame f = new JFrame();
	public Client() { 
		f.setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(0,153,0));
		p1.setBounds(0,0,450,70);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2= i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5,20,25,25);
		p1.add(back);
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked (java.awt.event.MouseEvent ae ){
				System.exit(0);
			}
		});
		
		ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image i5= i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40,20,50,50);
		p1.add(profile);
		
		ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8= i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(310,20,30,30);
		p1.add(video);
		
		ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11= i10.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel call = new JLabel(i12);
		call.setBounds(360,20,35,30);
		p1.add(call);
		
		ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14= i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel moreevert = new JLabel(i15);
		moreevert.setBounds(410,20,10,25);
		p1.add(moreevert);
		
		JLabel name = new JLabel("M.S Dhoni");
		name.setBounds(110,15,170,18);
		name.setBackground(Color.white);
		name.setFont(new Font("SAN_SERIF", Font.BOLD,18));
		p1.add(name);
		
		JLabel status = new JLabel("Online");
		status.setBounds(110,35,100,18);
		status.setBackground(Color.white);
		status.setFont(new Font("SAN_SERIF", Font.BOLD,14));
		p1.add(status);
		
		
		
		 a1= new JPanel();
		a1.setBounds(5,75,422,530);
		f.add(a1);
		
		 text = new JTextField();
		text.setBounds(5,610,310,40);
		text.setFont(new Font("SAN_SARIF", Font.PLAIN, 18	));
		f.add(text);
		
		JButton send = new JButton("Send");
		send.setBounds(320,610,105,40);
		send.setFont(new Font("SAN_SARIF", Font.PLAIN, 18	));
		send.addActionListener(this);
		send.setBackground(new Color(0,153,0));
		send.setForeground(Color.WHITE);
		f.add(send);
		
		
		f.setSize(450 ,700);
		f.setVisible(true);
	//	setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		f.setLocation(800,0);
	}
	public void actionPerformed(ActionEvent ae){
		try {
		String out= text.getText();
		System.out.println(out);
		
	//	JLabel output = new JLabel(out);
		JPanel p2 = formatLable(out);
	//	p2.add(output);
		
		a1.setLayout(new BorderLayout()); 
		
		JPanel right = new JPanel(new BorderLayout());
		right.add (p2,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		a1.add(vertical, BorderLayout.PAGE_START);
		text.setText("");
		f.repaint();f.invalidate();f.validate();
		
	
			dout.writeUTF(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static JPanel formatLable (String out){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel//(out);
				("<html><body> <p  width=\"150\" >"+ out +"</p> </body> </html>");
		output.setFont(new Font("Tahoma",Font.PLAIN,16));
		output.setBackground(new Color(37,211,102) );
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		panel.add(output);
		
		Calendar cal=  Calendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("HH:MM");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
		
	}
public static void main(String[] args) {
	new Client();
	try {
		Socket s= new Socket("127.0.0.1",5001);
		while(true){
			
			DataInputStream dinput = new DataInputStream(s.getInputStream());
			 dout= new DataOutputStream(s.getOutputStream());
			
			while(true){
				a1.setLayout(new BorderLayout());
				String msg = dinput.readUTF();
				JPanel panel= formatLable(msg);
				
				JPanel left= new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				vertical.add(left);
				vertical.add(Box.createVerticalStrut(15));
				a1.add(vertical, BorderLayout.PAGE_START);
				f.validate();
				
			}
			
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
}
}
