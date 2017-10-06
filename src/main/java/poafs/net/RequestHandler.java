package poafs.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Class that handles incoming requests.
 * @author rossrkk
 *
 */
public class RequestHandler implements Runnable {
	
	private Socket s;
	
	private BufferedOutputStream out;
	
	private BufferedInputStream in;
	
	/**
	 * Setup the input and output streams.
	 * @param s The connected socket.
	 * @throws IOException
	 */
	public RequestHandler(Socket s) throws IOException {
		this.s = s;
		out = new BufferedOutputStream(s.getOutputStream());
		in = new BufferedInputStream(s.getInputStream());
	}

	/**
	 * Actually handle the requests.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

}
