package poafs.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main server class.
 * @author rossrkk
 *
 */
public class Server implements Runnable {
	
	/**
	 * The server's listening socket.
	 */
	private ServerSocket ss;
	
	/**
	 * Create a new server on specified port.
	 * @param port The port the server will listen on.
	 * @throws IOException
	 */
	public Server(int port) throws IOException {
		ss = new ServerSocket(port);
	}
	
	/**
	 * The main server loop.
	 */
	public void run() {
		while (!ss.isClosed()) {
			try {
				Socket s = ss.accept();
				
				Thread t = new Thread(new RequestHandler(s));
				t.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
