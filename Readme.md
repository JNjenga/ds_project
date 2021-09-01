## Assignment – Inter-process Communications in Distributed Environment

Done by : 
```
ICS4A 111643 James Njenga K
ICS4A 110605 Alphonce Mutebi
ICS4A 112447 Bernard Muchiri
ICS4A 111157 Mark Kerosi
```

## Build instructions

### Windows
- Run the `build.bat` script

### Other

- Compile the program using `javac` command

	`javac ClientProtocol.java ServerProtocol.java SocketServer.java SocketClient.java`

- Run the program using `java` command
	- Server
		`java SocketServer`
	- Client
		`java SocketClient`

## Report

The application uses sockets with a custom protocol to achieve communication.
The protocol in the server and client are represented using an enum defined in `ClientProtocol.java` and `ServerProtocol.java`. Each value in the enum represents a code that is sent to the recipent and decoded to represent and action.

For example, if the server wants to request the client to send thier admission number, the code `RequestCode.NUMBER` which is 0, is sent to the client.
The client then decodes the code(`ClientProtocol::processRequest(int)`) and a human freindly message is sent displayed in the GUI.
The client then inputs the data in the textarea and sends back to the server.
