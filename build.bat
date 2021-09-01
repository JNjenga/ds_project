mkdir build
pushd .
cd build
javac ..\ClientProtocol.java ..\ServerProtocol.java ..\SocketServer.java ..\SocketClient.java -d .
REM Uncomment the following lines to run the program
REM call java SocketServer
REM call java SocketClient
popd
