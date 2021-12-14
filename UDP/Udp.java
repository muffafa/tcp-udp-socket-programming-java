import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Udp {
	DatagramSocket datagramSocket;
	InetAddress ip;
	DatagramPacket datagramPacket;
	DatagramPacket datagramPacketReceive;
	
	String message;
	String messageReceive;
	byte[] buffer;
}
