package engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

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
		ArrayList<GameIdentifier> ide;
	}
	
	public void run(){
		
		try {
			socket = new Socket(host, port);
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			while(true){
				Object o = objectInputStream.readObject();
				if(o.getClass() == DisplayGameState.class){
					System.out.println("got a dispaly object for updating");
					DisplayGameState dgs = (DisplayGameState)objectInputStream.readObject();
					//System.out.println("got displaygamestate for updating");
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	fxd.updateDisplay(dgs);
					    }
					});
				}else if(o.getClass() == ArrayList.class){
					System.out.println("got an arraylist of games");
					ArrayList<GameIdentifier> games = (ArrayList<GameIdentifier>)o;
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	fxd.refreshGamesList(games);
					    }
					});
				}else if(o.getClass() == String.class){
					String s = (String)o;
					if(s.equals("loginFailed")){
						System.out.println("login failed");
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	fxd.loginFailed();
						    }
						});
					}else if(s.equals("loginSuccessful")){
						System.out.println("login Successful");
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	fxd.openTitleScene();
						    }
						});
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}