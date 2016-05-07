import java.io.* ; 
import java.net.* ;
import java.util.StringTokenizer;
/**
 * 
 * @author Allen Bui, Sarah Hall
 * @version 1
 */


public final class HttpRequest implements Runnable {
  	// Continue STAGE 1 from Webserver.java, this is 1 - 3. Structure of HttpRequest class
	final static String CRLF = "\r\n";
	Socket socket;
  
	// Constructor
	public HttpRequest(Socket socket) throws Exception {
		this.socket = socket;
	}
  
	// Implement the run() method of the Runnable interface.
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
    
	private void processRequest() throws Exception {
	// Stage 1 - 4. Creating Socket streams 
		// Get a reference to the socket's input and output streams.
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
	    
		// Set up input stream filters.
		BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
	// Stage 1 - 5. Reading the Request	  
		//Get the request line of the HTTP request message.
		String requestLine = br.readLine();
		  
		// Display the request line
		System.out.println("\n" + requestLine);
	    
		//Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine);
			
		boolean badRequest = false;
		boolean methodNotImplemented = false;
	//Stage 2 - 1. Parse the request    
		String fileName = null;
		String version = null;
		
		String command = tokens.nextToken();
		  
		if (tokens.hasMoreTokens()) {
			fileName = tokens.nextToken();
		} else {
			badRequest = true;
		}
		  
		if (tokens.hasMoreTokens()) {
			version = tokens.nextToken();
		} else {
			badRequest = true;
		}
	
		if (tokens.hasMoreTokens()) {
			badRequest = true;
		}
	    
		boolean fileExists = true;
		boolean fileOpen = true;
		FileInputStream fis = null;
	    	
		 
		if (!command.equals("GET")) {
			methodNotImplemented = true;
		}
	
		if (!version.matches("HTTP/.[.].")) {
			System.out.println("Version pattern mismatch");      
			badRequest = true;
		}
	    
		// Construct the response message.
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
	// Stage 2 - 2. Checking if the requested file exists  
		if (!(badRequest || methodNotImplemented)) {
			// Open the requested file.
			try {
				fis = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				fileExists = false;
			}
	      		// Construct status line based on ability to open file & file existing
			if (fileExists && fileOpen) {
				statusLine = "HTTP/1.1 200 OK" + CRLF;
				contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
				System.out.println(contentTypeLine);

			} else {
				if (!(fileExists)) {
					statusLine = "\nHTTP/1.1 404 Not Found\n" + CRLF;
					contentTypeLine = "\nContent-type: " + "text/html" + CRLF;
					entityBody = "\n<HTML>" + "\n\t<HEAD>\n\t\t<TITLE>Not Found</TITLE>\n\t</HEAD>\n\t<BODY>File " + fileName + " Not Found</BODY>\n</HTML>" + CRLF;
				} else {
					statusLine = "\nHTTP/1.0 404 Not Found\n" + CRLF;
					contentTypeLine = "Content-type: " + "text/html" + CRLF;
					entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD><BODY>File " + fileName + " could not be written</BODY></HTML>" + CRLF; 
				}
			}
		}
	// Stage 2 - 3 & 5. Creating & Sending the response
		// Send the status line.
		os.writeBytes(statusLine);
		  
		// Send the content-type line.
		os.writeBytes(contentTypeLine);
		  
		// Send a blank line to indicate the end of the header lines.
		os.writeBytes(CRLF);
	
		// Send the entity body.
		if (!(badRequest || methodNotImplemented) && (fileExists && fileOpen)) {
			try {
				sendBytes(fis, os);
			} catch (Exception e) {
				System.out.println("Exception raised");
			}
			fis.close();
		} else {
			 System.out.println(entityBody);
	    	 os.writeBytes(entityBody);
		}
	    
	// Stage 2 - 6. Closing the Connection 
	    os.close();

	    // Get and display the header lines.  
		String headerLine;  
		while ((headerLine = br.readLine()).length() != 0) {
			System.out.println(headerLine);  
		}

		br.close();
	    
		socket.close();
  	}

  
  	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {

  		// Construct a 1K buffer to hold bytes on their way to the socket.
  		byte[] buffer = new byte[1024];
    
  		int bytes = 0;
    
  		// Copy requested file into the socket's output stream.
  		while ((bytes = fis.read(buffer)) != -1) {
  			os.write(buffer, 0, bytes);
  		}
  	}
    // Stage 2 - 4. Determining the MIME type
    private static String contentType(String fileName) {
	  
    	if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
    	
    		return "text/html";
    
    	} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
    
    		return "image/jpeg";

    	} else if (fileName.endsWith(".gif")) {
    
    		return "image/gif";
    
    	} else if (fileName.endsWith(".png")) {
    
    		return "image/png";
    
    	} else if (fileName.endsWith(".java")) {

    		return "source/java";
    
    	} else if (fileName.endsWith(".py")) {
    
    		return "source/python";
    
    	} else if (fileName.endsWith(".c")) {
        
    		return "source/c";
    
    	} else if (fileName.endsWith(".cpp")) {
    
    		return "source/C++";
    
    	} else if (fileName.endsWith(".txt")) {
    
    		return "text/plain";

    	} else if (fileName.endsWith(".pdf")) {
      
    		return "application/pdf";
    
    	}
   
    	return "application/octet-stream";
    }
  
}
