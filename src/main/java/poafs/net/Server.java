package poafs.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import poafs.keys.KeyManager;
import poafs.peer.FileTracker;

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
	
	private FileTracker ft;
	
	private KeyManager km;
	
	/**
	 * Create a new server on specified port.
	 * @param port The port the server will listen on.
	 * @throws IOException
	 */
	public Server(int port, FileTracker ft, KeyManager km, boolean ssl) throws IOException {
		if (ssl) {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ss = (SSLServerSocket) factory.createServerSocket(port);
		} else {
			ss = new ServerSocket(port);
		}
		this.ft = ft;
		this.km = km;
	}
	
	/**
	 * The main server loop.
	 */
	public void run() {
		while (!ss.isClosed()) {
			try {
				Socket s = ss.accept();
				
				Thread t = new Thread(new RequestHandler(s, ft, km));
				t.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
