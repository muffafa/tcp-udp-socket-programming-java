import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpServer extends Udp{

	public static void main(String[] args) throws IOException {
		
		Scanner input = new Scanner(System.in);
		
		UdpServer udpServer = new UdpServer();
		udpServer.datagramSocket = new DatagramSocket(5001);
		
		// ctr+c commands
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			public void run() {
				System.out.println("Shutdown Hook is running !");

				input.close();
				
				udpServer.datagramSocket.close();
			}
		});	
		
		udpServer.buffer = new byte[65535];
	
		while (true) {
			
			System.out.println("---------------------------");
			
			udpServer.datagramPacketReceive = new DatagramPacket(udpServer.buffer, udpServer.buffer.length);
			
			udpServer.datagramSocket.receive(udpServer.datagramPacketReceive);
			
			udpServer.messageReceive = new String(udpServer.datagramPacketReceive.getData(), 0, udpServer.datagramPacketReceive.getLength());
			
			System.out.println("Client: " + udpServer.messageReceive);
			
			udpServer.buffer = new byte[65535];
			
			System.out.println("---------------------------");
			
			System.out.println("Enter your message:");
			
			if (input.hasNextLine()) {
				udpServer.message = input.nextLine();
				
				udpServer.ip = InetAddress.getByName("localhost");
				
				udpServer.datagramPacket = new DatagramPacket(udpServer.message.getBytes(), udpServer.message.length(), udpServer.ip, 5000);
				udpServer.datagramSocket.send(udpServer.datagramPacket);
			}
		}
		
	}

}
