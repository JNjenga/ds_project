import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Color;

import java.io.*;  
import java.net.*;

public class SocketServer extends JFrame
{
	private String host = "";
	private int port = 0;

	private final int WIDTH  = 350;
	private final int HEIGHT = 450;

	PrintWriter outWriter;
	BufferedReader inReader;

	boolean readReady;
	boolean running = true;
	ServerSocket serverSocket;
	Socket clientSocket;
	ServerProtocol protocol;

	public SocketServer()
	{
		protocol = new ServerProtocol();

		setTitle("Distributed sys client");
		setLayout(null);

		int Xpos = WIDTH / 2 - 50;
		int Ypos = 10;

		String text = "This is the server. Start it before connecting.";

		JTextArea textArea = new JTextArea(100, 50);
		textArea.setText(text);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(UIManager.getColor("Label.background"));
		textArea.setFont(UIManager.getFont("Label.font"));
		textArea.setBorder(UIManager.getBorder("Label.border"));
		textArea.setBounds(Xpos-30, Ypos, 150, 50);
		add(textArea);
		Ypos += 50;

		JLabel portLabel = new JLabel("Port :");
		portLabel.setBounds(Xpos - 40, Ypos, 100, 20);
		add(portLabel);

		JTextField portTf = new JTextField(50);
		portTf.setBounds(Xpos, Ypos, 100, 20);
		portTf.setText("8001");
		add(portTf);
		Ypos += 30;

		JButton startServerBtn = new JButton("Start");
		startServerBtn.addActionListener(e -> {
			startServerBtn.setText("Starting");
			startServerBtn.setEnabled(false);

			setPort(Integer.parseInt(portTf.getText()));
			portTf.setEnabled(false);

			new Thread(()->{
				try
				{
					serverSocket = new ServerSocket(port);
					clientSocket = serverSocket.accept();
					outWriter =
						new PrintWriter(clientSocket.getOutputStream(), true);
					inReader =
						new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

					startServerBtn.setText("Listening..");

				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(
							null,
							"error",
							ex.getMessage(),
							JOptionPane.WARNING_MESSAGE );
				}
			}).start();
		});

		startServerBtn.setBounds(Xpos, Ypos, 100, 20);
		add(startServerBtn);
		Ypos += 30;

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBounds(Xpos, Ypos, 100, 20);
		add(separator);
		Ypos += 10;

		JLabel actionLabel = new JLabel("Request :");
		actionLabel.setBounds(Xpos - 60, Ypos, 100, 20);
		add(actionLabel);

		JComboBox actions_combo = new JComboBox(new String[] {
			"Number",
			"Name",
			"Faculty",
			"Course",
			"Degree",
			"Code",
			"All",
		});

		actions_combo.setBounds(Xpos, Ypos, 100, 20);
		add(actions_combo);
		Ypos += 30;

		JTable dataTable = new JTable(
				new String[][]{
					{"Number", ""},
					{"Name", ""},
					{"Faculty", ""},
					{"Course", ""},
					{"Degree", ""},
					{"Code", ""}
				},
				new String[]{"Key", "Value"}
				);

		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(e -> {
			new Thread(()->{
				System.out.println("Message sent");
				try
				{
					int selected = actions_combo.getSelectedIndex();
					String action = actions_combo.getItemAt(selected).toString();
					int action_code = protocol.processRequest(action);
					outWriter.println(action_code);

					String fromClient = inReader.readLine();
					protocol.processResponse(dataTable, fromClient);

					System.out.println("Client: " + fromClient);
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(
							this,
							null,
							ex.getMessage(),
							JOptionPane.WARNING_MESSAGE );

				}

					System.out.println("Message sent");

			}).start();
		});

		sendBtn.setBounds(Xpos, Ypos, 100, 20);
		add(sendBtn);
		Ypos += 30;

		JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
		separator1.setBounds(Xpos, Ypos, 100, 20);
		add(separator1);
		Ypos += 10;

		JLabel dataTableLabel = new JLabel("Data table");
		dataTableLabel.setBounds(Xpos, Ypos, 100, 20);
		add(dataTableLabel);
		Ypos += 20;

		dataTable.setEnabled(false);
		dataTable.setBounds(Xpos - 40, Ypos, 150, 100);
		dataTable.setBorder(BorderFactory.createLineBorder(Color.black));
		add(dataTable);
		Ypos += 120;

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

	public boolean send(String message)
	{
		try
		{
			outWriter.println("Hello");
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(
					this,
					null,
					"Error occured while sending message",
					JOptionPane.WARNING_MESSAGE );

		}
		return true;
	}

	public static void main(String args[])
	{
		new SocketServer();
	}
}
