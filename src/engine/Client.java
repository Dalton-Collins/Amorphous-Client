package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	fxDisplay fxd;
	
	String userName;
	String host;
	int port;
	
	ObjectOutputStream oos;
	
	ClientInputThread clientInputThread;
	
	
	/*
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		String name = args[0];
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost", 9082);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(new Event("testEvent"));
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		while(true){
			String readerInput = br.readLine();
			
			pw.println(name + ": " + readerInput);
		}
		
	}
	*/
	
	public Client(fxDisplay fxdd, String namee, String hostt, int portt){
		fxd = fxdd;
		userName = namee;
		host = hostt;
		port = portt;
	}
	
	public void connect() throws UnknownHostException, IOException{
		Socket socket = new Socket(host, port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		
		clientInputThread = new ClientInputThread(fxd, this, host, port);
		clientInputThread.start();
	}
	
	public void write(Object o){
		try {
			oos.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}