import javax.swing.*;
import java.awt.Color;

import javax.swing.BoxLayout;
import java.awt.Component;

import java.io.*;  
import java.net.*;

public class SocketClient extends JFrame
{
	private String host = "";
	private int port = 0;

	private final int WIDTH  = 350;
	private final int HEIGHT = 450;

	JTextArea messageBox;
	Socket socket;
	PrintWriter outWriter;
	BufferedReader inReader;
	boolean readReady = false;


	public SocketClient()
	{
		setTitle("Distributed sys client");
		setLayout(null);

		int Xpos = WIDTH / 2 - 50;
		int Ypos = 10;

		String text = "This is the client. Start the server before connecting.";

		JTextArea textArea = new JTextArea(100, 80);
		textArea.setText(text);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(UIManager.getColor("Label.background"));
		textArea.setFont(UIManager.getFont("Label.font"));
		textArea.setBorder(UIManager.getBorder("Label.border"));
		textArea.setBounds(Xpos-30, Ypos, 150, 80);
		add(textArea);
		Ypos += 80;

		JLabel hostLabel = new JLabel("Host :");
		hostLabel.setBounds(Xpos - 40, Ypos, 100, 20);
		add(hostLabel);

		JTextField hostTf = new JTextField(20);
		hostTf.setText("localhost");
		hostTf.setBounds(Xpos, Ypos, 120, 20);
		add(hostTf);
		Ypos += 30;

		JLabel portLabel = new JLabel("Port :");
		portLabel.setBounds(Xpos - 40, Ypos, 100, 20);
		add(portLabel);

		JTextField portTf = new JTextField(50);
		portTf.setBounds(Xpos, Ypos, 120, 20);
		portTf.setText("8001");
		add(portTf);
		Ypos += 30;

		JTextArea serverMessage = new JTextArea(120, 80);

		JButton connectBtn = new JButton("Connect");
		connectBtn.addActionListener(e -> {

			setHost(hostTf.getText());
			setPort(Integer.parseInt(portTf.getText()));

			connectBtn.setText("Connecting...");
			connectBtn.setEnabled(false);
			portTf.setEnabled(false);
			hostTf.setEnabled(false);

			new Thread(()->{
				try
				{
					socket =  new Socket(this.host, this.port);


					outWriter = new PrintWriter(socket.getOutputStream(), true);
					inReader = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

					connectBtn.setText("Connected");

					String fromServer;

					while((fromServer = inReader.readLine()) != null)
					{
						String message = ClientProtocol.processRequest(Integer.parseInt(fromServer));

						serverMessage.setText(message);
						serverMessage.setForeground(Color.red);
					}
				}
				catch(Exception ex)
				{
					connectBtn.setText("Connect");
					connectBtn.setEnabled(true);
					portTf.setEnabled(true);
					hostTf.setEnabled(true);

					JOptionPane.showMessageDialog(
							this,
							ex.getMessage(),
							"Error while connecting to server",
							JOptionPane.WARNING_MESSAGE );

				}
			}).start();
		});

		connectBtn.setBounds(Xpos, Ypos, 120, 20);
		add(connectBtn);
		Ypos += 30;

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBounds(Xpos, Ypos, 120, 20);
		add(separator);
		Ypos += 20;

		// serverMessage.setText("Message from the server will appear here!");
		serverMessage.setText("Enter all(separate with comma):\nadmno,name,faculty,course,\ndegree,code");
		serverMessage.setWrapStyleWord(true);
		serverMessage.setLineWrap(true);
		serverMessage.setOpaque(false);
		serverMessage.setEditable(false);
		serverMessage.setFocusable(false);
		serverMessage.setBackground(UIManager.getColor("Label.background"));
		serverMessage.setFont(UIManager.getFont("Label.font"));
		serverMessage.setBorder(UIManager.getBorder("Label.border"));
		serverMessage.setForeground(Color.red);
		serverMessage.setBounds(Xpos-50, Ypos, 200, 80);
		add(serverMessage);
		Ypos += 50;

		messageBox = new JTextArea("Response goes here", 6, 200);
		messageBox.setBounds(Xpos - 50, Ypos, 200, 80);
		messageBox.setBorder(BorderFactory.createLineBorder(Color.black));
		messageBox.setWrapStyleWord(true);
		messageBox.setLineWrap(true);
		messageBox.setEditable(true);
		messageBox.setFocusable(true);
		add(messageBox);
		Ypos += 90;

		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(e -> {
			new Thread(()->{
				try
				{
					outWriter.println(messageBox.getText());
					messageBox.setText("");
					serverMessage.setText("Response sent to server!");
					serverMessage.setForeground(Color.green);

					String fromServer;

					String message ;
					while((fromServer = inReader.readLine()) != null)
					{
						message = ClientProtocol.processRequest(Integer.parseInt(fromServer));

						serverMessage.setText(message);
						serverMessage.setForeground(Color.red);
					}

				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(
							this,
							ex.getMessage(),
							null,
							JOptionPane.WARNING_MESSAGE );

				}
			}).start();
		});

		sendBtn.setBounds(Xpos, Ypos, 100, 20);
		add(sendBtn);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public boolean connect()
	{
		try {
				Socket socket =  new Socket(this.host, this.port);
				outWriter = new PrintWriter(socket.getOutputStream(), true);
				inReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn =
				new BufferedReader(new InputStreamReader(System.in));
			String fromServer;

			while((fromServer = inReader.readLine()) != null)
			{
				if(readReady)
					outWriter.println(messageBox.getText());
			}

			return true;
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(
					this,
					null,
					e.getMessage(),
					JOptionPane.WARNING_MESSAGE );

		}
		return false;
	}

	public boolean send(String message)
	{
		return true;
	}

	public static void main(String args[])
	{
		new SocketClient();
	}
}
