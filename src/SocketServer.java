import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    
    private final int port = 8081;
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {
        try {
            
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port 8081...");

            while (true) {
                
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected!");

                
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            
            System.out.println("Server error: " + e.getMessage());
        }
    }

}
class tester{
    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start();
    }
}