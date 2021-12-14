import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient extends Tcp{

	public static void main(String[] args) throws IOException {
		
		Scanner input = new Scanner(System.in);
		
		TcpClient tcpClient = new TcpClient();
		tcpClient.socket = new Socket("localhost", 4999);
		

		// ctr+c commands
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			public void run() {
				System.out.println("Shutdown Hook is running !");

				input.close();

				try {
					tcpClient.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		// send message to server
		// PrintWriter(OutputStream out)
		tcpClient.printWriter = new PrintWriter(tcpClient.socket.getOutputStream()); // getOutputStream() Returns an output stream for
																	// this socket.
		tcpClient.printWriter.println("Hello from client");

		tcpClient.printWriter.flush(); // Flushes the stream.
		
		// read message from server
		tcpClient.inputStreamReader = new InputStreamReader(tcpClient.socket.getInputStream()); // getInputStream() Returns an input
																			// stream for this socket.
		tcpClient.bufferedReader = new BufferedReader(tcpClient.inputStreamReader);
		
		System.out.println("---------------------------");
		
		String inputLine;
		
		while (((inputLine = tcpClient.bufferedReader.readLine()) != null)) {
			System.out.println("Server: " + inputLine);
			System.out.println("---------------------------");
			
			System.out.println("Enter your message:");
			
			if (input.hasNextLine()) {
				String message = input.nextLine();
				
				tcpClient.printWriter.println(message);
				tcpClient.printWriter.flush();
			}
			
			System.out.println("---------------------------");
		}
		

	}

}
