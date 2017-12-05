import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class bai3 {

	private JFrame frame;
	private JTextField textField;
	private JButton btnBrowse;
	private JButton btnUpload;
	private String filePath;
	private ArrayList<Student> students;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bai3 window = new bai3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public bai3() {
		initialize();

		// nút Browse
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(null); // hiển thị Open Dialog
				File f = fc.getSelectedFile();
				filePath = f.getAbsolutePath(); // lấy đường dẫn file
				textField.setText(filePath); // gán đường dẫn file vào textLink
			}
		});

		btnUpload.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				String serverName = "localhost";
				int port = 6066;
				try {
					System.out.println("Connecting to " + serverName + " on port " + port);
					Socket client = new Socket(serverName, port);
					System.out.println("Just connected to " + client.getRemoteSocketAddress());
					
					DataOutputStream output = new DataOutputStream(client.getOutputStream());
					output.writeUTF(filePath);
					
					ObjectInputStream input = new ObjectInputStream(client.getInputStream());

					try {
						students = (ArrayList<Student>) input.readObject();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					
					for (Student student : students) {
						System.out.println(student.toString());
					}
					client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(61, 85, 34, 14);
		frame.getContentPane().add(lblFile);

		textField = new JTextField();
		textField.setBounds(106, 82, 173, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnUpload = new JButton("Send Request");
		btnUpload.setBounds(134, 129, 125, 23);
		frame.getContentPane().add(btnUpload);

		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(298, 81, 89, 23);
		frame.getContentPane().add(btnBrowse);

	}
}
