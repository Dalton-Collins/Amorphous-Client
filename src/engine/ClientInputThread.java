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
	
	ClientInputThread(fxDisplay fxdd, Socket sockett, Client clientt) throws IOException{
		fxd = fxdd;
		client = clientt;
		socket = sockett;
	}
	
	public void run(){
		
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			while(true){
				DisplayGameState dgs = (DisplayGameState)objectInputStream.readObject();
				System.out.println("got displaygamestate for updating");
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