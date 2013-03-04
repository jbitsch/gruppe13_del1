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
	static ServerSocket listener;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCon(int portdst) throws IOException
	{
		
		listener = new ServerSocket(portdst);
		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		return instream.readLine();
	}

	
	public void writeTo(String text) throws IOException
	{
		outstream = new DataOutputStream(sock.getOutputStream());
		outstream.writeBytes(text);
	}
	public void closeCon() throws IOException
	{
		instream.close();
		outstream.close();
	}
	public String getIp()
	{
		return sock.getInetAddress().toString();
	}
	
}
