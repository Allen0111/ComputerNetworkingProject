import java.net.* ; 
/**
 * WebServer is a simple networking project used to grasp the concept of TCP/IP programming
 * @author Allen Bui, Sarah Hall
 * @Version 1
 */
public final class WebServer {
	
	/**
	 * main class is the beginning of program execution. 
	 * Establish socket connection and wait for requests.
	 * @param args N/A in this program 
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		// Determine the port number
		int port = 5000;

		// Establish the listen socket.
		@SuppressWarnings("resource")
		ServerSocket listenSocket = new ServerSocket(port);

		// Process HTTP service requests in an infinite loop
		while (true) {
			// Listen for a TCP connection request.
			Socket connectionSocket = listenSocket.accept();
      
			// Construct an object to process the HTTP request message
			HttpRequest request = new HttpRequest(connectionSocket);
      
			//Create a new thread to process the request.
			Thread thread = new Thread(request);
      
			// Start the thread.
			thread.start();
		}
	}
}