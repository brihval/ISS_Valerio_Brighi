package conway.caller;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class MyCallerWS {
	private Session session; 
	private boolean firstTime; 
	
	public MyCallerWS() {
		firstTime = false;
		try {
			System.out.println("Working for connection with the server...");
			connect(new URI("ws://localhost:7110/wsupdate"));
			System.out.println("Connection ok!");
		}catch(Exception e) {
			System.err.println("Error occured while connecting to the server: " + e.getMessage());
		}
	}
	
	private void connect(URI serverEndpoint) throws Exception{
		this.session.getContainer().connectToServer(this, serverEndpoint);
	}
	
	public void play() {
		
		if(firstTime)
			firstTime = false;
		else
			send("clear");
			
		/* primo sleep di tot secondi */
		send("cell-1-2");
		send("cell-2-3");
		send("cell-3-2");
		
		send("start");
		/* secondo sleep di tot secondi */
		send("stop");
	}
	
	public void send(String message) {
		try {
			System.out.println("Sending message: " + message);
			this.session.getBasicRemote().getSendWriter().write(message);
		} catch (IOException e) {
			System.err.println("Error occured while sending message: " + e.getMessage());
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session; 
		System.out.println("WebSocket connection activated. Session: " + session.getId());
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received message: " + message);
	}
	
	@OnClose 
	public void onClose() {
		System.out.println("WebSocket connection was closed");
	}
	
	@OnError
	public void errorHandler(Exception e) {
		System.err.println("Error occured on WS connection: " + e.getMessage());
	}
	
	public void closeSession() {
		try {
			this.session.close();
		} catch (IOException e) {
			System.err.println("Error occured while closing WebSocket session: " + e.getMessage());
		}
	}
}

