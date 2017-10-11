package poafs.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import poafs.db.entities.FileBlock;
import poafs.db.entities.Peer;
import poafs.db.entities.PoafsFile;
import poafs.db.entities.User;
import poafs.db.repo.Repository;
import poafs.keys.KeyManager;

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
	
	private Repository<PoafsFile> fileRepo;
	
	private KeyManager km;
	
	private Repository<Peer> peerRepo;
	
	private Repository<FileBlock> blockRepo;
	
	private Repository<User> userRepo;
	
	/**
	 * Create a new server on specified port.
	 * @param port The port the server will listen on.
	 * @throws IOException
	 */
	public Server(int port, Repository<PoafsFile> fileRepo, Repository<FileBlock> fileBlockRepo, KeyManager km, Repository<Peer> peerRepo, Repository<User> userRepo, boolean ssl) throws IOException {
		if (ssl) {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ss = (SSLServerSocket) factory.createServerSocket(port);
		} else {
			ss = new ServerSocket(port);
		}
		this.fileRepo = fileRepo;
		this.km = km;
		this.peerRepo = peerRepo;
		this.blockRepo = fileBlockRepo;
		this.userRepo = userRepo;
	}
	
	/**
	 * The main server loop.
	 */
	public void run() {
		while (!ss.isClosed()) {
			try {
				Socket s = ss.accept();
				
				Thread t = new Thread(new RequestHandler(s, fileRepo, blockRepo, km, peerRepo, userRepo));
				t.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
