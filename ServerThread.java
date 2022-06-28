import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Lenovo
 */
public class ServerThread extends Thread{
 
    Socket socket;
 
 
    public ServerThread(Socket socket){
        this.client = socket;
    }
 
    @Override
    public void run(){
    
      String request = readIn();
      if(request.equals("Search")){
 
    }
}
