import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UdpClient extends Udp {

	DatagramSocket datagramSocketClient;

	public static void main(String[] args) throws IOException {

		Scanner input = new Scanner(System.in);

		UdpClient udpClient = new UdpClient();
		udpClient.datagramSocket = new DatagramSocket();
		udpClient.datagramSocketClient = new DatagramSocket(5000);

		// ctr+c commands
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				System.out.println("Shutdown Hook is running !");

				input.close();

				udpClient.datagramSocket.close();
				udpClient.datagramSocketClient.close();

			}
		});

		udpClient.buffer = new byte[65535];

		while (true) {
			
			System.out.println("---------------------------");
			
			System.out.println("Enter your message:");
			
			if (input.hasNextLine()) {
				
				udpClient.message = input.nextLine();
	
				udpClient.ip = InetAddress.getByName("localhost");
	
				udpClient.datagramPacket = new DatagramPacket(udpClient.message.getBytes(), udpClient.message.length(),
						udpClient.ip, 5001);
	
				udpClient.datagramSocket.send(udpClient.datagramPacket);
			}
			
			System.out.println("---------------------------");
			
			udpClient.datagramPacketReceive = new DatagramPacket(udpClient.buffer, udpClient.buffer.length);

			udpClient.datagramSocketClient.receive(udpClient.datagramPacketReceive);

			udpClient.messageReceive = new String(udpClient.datagramPacketReceive.getData(), 0,
					udpClient.datagramPacketReceive.getLength());
			
			System.out.println("Server: " + udpClient.messageReceive);
			
			udpClient.buffer = new byte[65535];
		}

	}

}
