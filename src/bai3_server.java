import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class bai3_server extends Thread {

	private JFrame frame;
	private ServerSocket serverSocket;
	private ArrayList<Student> students;
	String line;

	public bai3_server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(100000);
	}

	public void run() {
		while (true) {
			try {
				students = new ArrayList<Student>();

				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				
				DataInputStream input = new DataInputStream(server.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());

				FileInputStream fis = new FileInputStream(input.readUTF()); //đọc dữ liệu theo định dạng byte
				InputStreamReader isr = new InputStreamReader(fis); //chuyển byte sang kí tự
				BufferedReader br = new BufferedReader(isr); //đọc văn bản dựa trên kí tự
				
				//bỏ dòng đầu tiên - header
				line = br.readLine();//đọc theo dòng
				String [] st;
				
				while ((line = br.readLine()) != null) {
					st = line.split(",");//cắt chuỗi
					students.add(new Student(st[0], st[1], st[2], st[3]));
				}
				
				//đóng luồng dữ liệu
				br.close();
				isr.close();
				fis.close();

				output.writeObject(students);
				server.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bai3_server window = new bai3_server();
					window.frame.setVisible(true);

					int port = 6066;
					Thread t = new bai3_server(port);
					t.start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public bai3_server() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
