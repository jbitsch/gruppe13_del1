package Weight;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Data {

	private static Socket sock;

	private static BufferedReader instream;
	private static DataOutputStream outstream;
	private static ServerSocket listener;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	public void awaitConnection(int portdst) throws IOException
	{
		
		listener = new ServerSocket(portdst);
		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());
	}
	public String getInput() throws IOException
	{
		String input = instream.readLine();
		return input;
	}
	
	public void writeTo(String text) throws IOException
	{
		
		outstream.writeBytes(text);
	}
	
	public void closeConnection() throws IOException
	{
		instream.close();
		outstream.close();
	}
	
	public String getIp()
	{
		return sock.getInetAddress().toString();
	}
	public void flushOut()
	{
		try {
			outstream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
