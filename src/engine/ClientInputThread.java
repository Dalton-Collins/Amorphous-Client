package engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javafx.application.Platform;

//this object waits for input from the server
//so that input can be used on the display
public class ClientInputThread extends Thread{
	
	fxDisplay fxd;
	
	Client client;
	Socket socket;
	String host;
	int port;
	
	ClientInputThread(fxDisplay fxdd, Client clientt, String hostt, int portt) throws IOException{
		fxd = fxdd;
		client = clientt;
		host = hostt;
		port = portt;
	}
	
	public void run(){
		
		try {
			Socket socket = new Socket(host, port);
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			while(true){
				DisplayGameState dgs = (DisplayGameState)objectInputStream.readObject();
				//System.out.println("got displaygamestate for updating");
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	fxd.updateDisplay(dgs);
				    }
				});
				
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}