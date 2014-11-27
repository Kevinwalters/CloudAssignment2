package app;
import java.io.IOException;

<<<<<<< HEAD
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
=======
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
>>>>>>> 5045188f92f727c814a6e93758296ed74b7e5969
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class WebSocketTest {

<<<<<<< HEAD
  private Session session;

@OnMessage
=======
  @OnMessage
>>>>>>> 5045188f92f727c814a6e93758296ed74b7e5969
  public void onMessage(String message, Session session) 
    throws IOException, InterruptedException {
  
    // Print the client message for testing purposes
    System.out.println("Received: " + message);
  
    // Send the first message to the client
    session.getBasicRemote().sendText("This is the first server message");
  
    // Send 3 messages to the client every 5 seconds
    int sentMessages = 0;
    while(sentMessages < 3){
      Thread.sleep(5000);
<<<<<<< HEAD
      session.getBasicRemote().sendText("This is an intermediate server message. Count: " 
=======
      session.getBasicRemote().
        sendText("This is an intermediate server message. Count: " 
>>>>>>> 5045188f92f727c814a6e93758296ed74b7e5969
          + sentMessages);
      sentMessages++;
    }
  
    // Send a final message to the client
    session.getBasicRemote().sendText("This is the last server message");
  }
  
  @OnOpen
<<<<<<< HEAD
  public void onOpen(Session session, EndpointConfig config) {
    System.out.println("Client connected");
    this.session = session;
}


=======
  public void onOpen() {
    System.out.println("Client connected");
  }
>>>>>>> 5045188f92f727c814a6e93758296ed74b7e5969

  @OnClose
  public void onClose() {
    System.out.println("Connection closed");
  }
}