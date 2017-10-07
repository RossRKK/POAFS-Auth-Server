package poafs.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

import poafs.keys.KeyManager;
import poafs.peer.FileTracker;
import poafs.peer.Peer;

/**
 * Class that handles incoming requests.
 * @author rossrkk
 *
 */
public class RequestHandler implements Runnable {
	
	private Socket s;
	
	private BufferedOutputStream out;
	
	private Scanner in;
	
	private FileTracker ft;
	
	private KeyManager km;
	
	/**
	 * Setup the input and output streams.
	 * @param s The connected socket.
	 * @throws IOException
	 */
	public RequestHandler(Socket s, FileTracker ft, KeyManager km) throws IOException {
		this.s = s;
		out = new BufferedOutputStream(s.getOutputStream());
		in = new Scanner(s.getInputStream());
		
		this.ft = ft;
		this.km = km;
	}

	/**
	 * Handle incoming requests.
	 */
	@Override
	public void run() {
		while (in.hasNextLine()) {
			String[] response = in.nextLine().split(" ");
			
			String command = response[0];
			String argument = response[1];
			
			switch (command) {
				case "private-key":
					getPrivateKey(argument);
					break;
				case "host":
					getHost(argument);
					break;
				case "list-files":
					listFiles(argument);
					break;
				case "find-block":
					findBlock(argument);
					break;
				case "register":
					registerPeer(argument);
					break;
				default:
					unknownCommand();
					break;
			}
		}
	}
	
	/**
	 * Print a line back to the client.
	 * @param str The line.
	 * @throws IOException
	 */
	private void println(String str) {
		try {
			out.write((str + "\r\n").getBytes());
			
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Respond saying that the command is unknown.
	 */
	private void unknownCommand() {
		println("Unknown Command");
	}

	/**
	 * Register a new peer.
	 * @param peerId The id of the new peer.
	 */
	private void registerPeer(String peerId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Find which peers have a block.
	 * @param blockId The unique identifier of the block (i.e. [fileId]:[index])
	 */
	private void findBlock(String blockId) {
		try {
			String[] info = blockId.split(":");
			Collection<Peer> peers = ft.getPeersWithBlock(info[0], Integer.parseInt(info[1]));
			
			println("peers length:" + peers.size());
			
			peers.forEach(peer -> println(peer.getId()));
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			println("Error parsing block id");
			System.err.println("Error parsing block id: " + blockId);
			e.printStackTrace();
		}
		
	}

	/**
	 * List all available files.
	 * @param filter Search query.
	 */
	private void listFiles(String query) {
		// TODO Auto-generated method stub
	}

	/**
	 * Get the host details for a peer.
	 * @param peerId The id of the peer.
	 */
	private void getHost(String peerId) {
		Peer p = Peer.getPeer(peerId);
		
		println(peerId + " " + p.getAddress());
	}

	/**
	 * Get the private key for a peer.
	 * @param peerId The peer's id.
	 */
	private void getPrivateKey(String peerId) {
		byte[] key = km.getPrivateKeyForPeer(peerId).getEncoded();
		
		println("key length:" + key.length);
		
		try {
			out.write(key);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
