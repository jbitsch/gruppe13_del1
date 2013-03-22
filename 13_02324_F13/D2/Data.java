import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * This class' function is to listen for a connection request, and connect to the client if a 
 * request is received
 *
 */
public class Data {

	private static Socket sock;

	private static BufferedReader instream;
	private static DataOutputStream outstream;
	static ServerSocket listener;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * A class which establishes the connection to the requesting client.
	 * @param portdst Port number which is listened to
	 * @throws IOException
	 */
	public void getCon(int portdst) throws IOException
	{
		
		listener = new ServerSocket(portdst);
		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());
	}
	
	/**
	 * A method which reads an input line received from the connection
	 * @return The String received from the connection
	 * @throws IOException
	 */
	public String getInput() throws IOException
	{
		String input = instream.readLine();
		return input;
	}
	
	/**
	 * A method which returns a message to the connected client
	 * @param text The text to be sent to client
	 * @throws IOException
	 */
	public void writeTo(String text) throws IOException
	{
		
		outstream.writeBytes(text);
	}
	
	/**
	 * A method called to close the connection to the client.
	 * @throws IOException
	 */
	public void closeCon() throws IOException
	{
		instream.close();
		outstream.close();
	}
	
	/**
	 * A method which is used to get the IP address of a connected client
	 * @return The IP address of the client
	 */
	public String getIp()
	{
		return sock.getInetAddress().toString();
	}
	
}
