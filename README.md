
-----------------------------------
 CS464 Computer Networking Project 
-----------------------------------

Note: All source code has been proven to run on kira.cs.wichita.edu and input/output verified by TA: Abhignan

A) To compile on kira.cs.wichita.edu:

	1) ensure that you are in the directory containing the HttpRequest.java and WebServer.java

	2) type-	javac *.java

			OR

			javac HttpRequest.java WebServer.java

B) To run on kira.cs.wichita.edu:
	
	Note: due to firewall issues, both the server and client must run on the same server (kira)

	1) in the same directory that HttpRequest.java and WebServer.java was compiled (from part A) type-
		
			java WebServer

	2) create another instance of your ssh connection or use screen For more information on screen
	
		https://www.rackaid.com/blog/linux-screen-tutorial-and-how-to/
	
C) Test Cases: There were 3 files that were turned in with the program and documents. those files serve as test cases for our program. change directory to where those files are located and type "pwd" (without the quotation marks). the system will return the current directory name. in the test cases below, insert this directory name in place of <directory name>

	1) Once the second instance of your SSH session has started, you can run the following test cases:

		a)	GET http://kira.cs.wichita.edu:5000/<directory name>/example1.html
	
		b)	GET http://kira.cs.wichita.edu:5000/<directory name>/example2.png

		c)	GET http://kira.cs.wichita.edu:5000/<directory name>/example3.jpeg

		d)	GET http://kira.cs.wichita.edu:5000/<directory name>/example4.java

		
                        - On the server side you will see the type of request (html, png, jpeg, java, etc) as well as the actual request line that was sent.

                        - on the client side you will see that the requested html file has been served.
