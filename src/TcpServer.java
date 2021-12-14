import java.io.*;
import java.net.*;
import java.util.Scanner;


public class TcpServer extends Tcp{
	
	public ServerSocket serverSocket;
	
	public static void main(String[] args) throws IOException {
		
		Scanner input = new Scanner(System.in);
		
		TcpServer tcpServer = new TcpServer(); 
		tcpServer.serverSocket = new ServerSocket(4999);

		tcpServer.socket = tcpServer.serverSocket.accept(); // accept() Listens for a connection to be made to this socket and accepts it.
		
		// ctr+c commands
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			public void run() {
				System.out.println("Shutdown Hook is running !");

				input.close();

				try {
					tcpServer.serverSocket.close();
					tcpServer.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		System.out.println("---------------------------");
		System.out.println("Client connected");
		
		System.out.println("---------------------------");
		
		// read message from client
		tcpServer.inputStreamReader = new InputStreamReader(tcpServer.socket.getInputStream()); // getInputStream() Returns an input
																			// stream for this socket.
		tcpServer.bufferedReader = new BufferedReader(tcpServer.inputStreamReader);

		System.out.println("Client: " + tcpServer.bufferedReader.readLine());
		
		System.out.println("---------------------------");
		
		// send message to client
		tcpServer.printWriter = new PrintWriter(tcpServer.socket.getOutputStream()); // getOutputStream() Returns an output stream for
																	// this socket.
		tcpServer.printWriter.println("Hi from server");
		tcpServer.printWriter.flush(); // Flushes the stream.
								// PrintWriter(OutputStream out)

		String inputLine;

		while ((inputLine = tcpServer.bufferedReader.readLine()) != null) {
			System.out.println("Client: " + inputLine);
			System.out.println("---------------------------");
			
			System.out.println("Enter your message:");
			
			if (input.hasNextLine()) {
				String message = input.nextLine();
				
				tcpServer.printWriter.println(message);
				tcpServer.printWriter.flush();
			}
			
			System.out.println("---------------------------");
		}

	}

}
