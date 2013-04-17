package clientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket2 {
	
	private String ip;
	private int port;
	private Socket scaleSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public void connect(String ip, int port) throws IOException
	{
		this.ip = ip;
		this.port = port;
		scaleSocket = new Socket(ip, port);
    	out = new PrintWriter(scaleSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(scaleSocket.getInputStream()));
	}

	public String recieveFromServer() throws IOException
	{
		return in.readLine();
	}
	
	public void sendToServer(String command)
	{
		out.println(command);
	}
	public void flu()
	{
		out.flush();
	}
}
