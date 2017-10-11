package poafs.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import poafs.db.entities.FileBlock;
import poafs.db.entities.Peer;
import poafs.db.entities.PoafsFile;
import poafs.db.entities.User;
import poafs.db.repo.Repository;
import poafs.keys.KeyManager;

/**
 * Class that handles incoming requests.
 * @author rossrkk
 *
 */
public class RequestHandler implements Runnable {
	
	/**
	 * The socket that connects to the client.
	 */
	private Socket s;
	
	/**
	 * The output stream to the client.
	 */
	private BufferedOutputStream out;
	
	/**
	 * The input stream from the client.
	 */
	private Scanner in;
	
	/**
	 * The repository that handles files.
	 */
	private Repository<PoafsFile> fileRepo;
	
	/**
	 * The repository that handles blocks.
	 */
	private Repository<FileBlock> blockRepo;
	
	/**
	 * The repository that handles peers.
	 */
	private Repository<Peer> peerRepo;
	
	/**
	 * The repository that handles users.
	 */
	private Repository<User> userRepo;
	
	/**
	 * The local key manager.
	 */
	private KeyManager km;
	
	/**
	 * The id of the current peer.
	 */
	private String peerId;

	/**
	 * Whether the user has successfully authenticated since connecting.
	 */
	private boolean authenticated = false;
	
	/**
	 * Setup the input and output streams.
	 * @param s The connected socket.
	 * @throws IOException
	 */
	public RequestHandler(Socket s, Repository<PoafsFile> fileRepo, Repository<FileBlock> fileBlockRepo, 
			KeyManager km, Repository<Peer> peerRepo, Repository<User> userRepo) throws IOException {
		this.s = s;
		out = new BufferedOutputStream(s.getOutputStream());
		in = new Scanner(s.getInputStream());
		
		this.fileRepo = fileRepo;
		this.km = km;
		this.peerRepo = peerRepo;
		this.blockRepo = fileBlockRepo;
		this.userRepo = userRepo;
	}

	/**
	 * Handle incoming requests.
	 */
	@Override
	public void run() {
		println("POAFS Auth 0.1");
		
		peerId = in.nextLine();
		
		while (in.hasNextLine()) {
			try {
				String[] response = in.nextLine().split(" ");
				
				String command = response[0];
				String argument = response[1];
				
				//switch on the command
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
					case "register-peer":
						registerPeer(argument);
						break;
					case "register-file":
						registerFile(argument);
						break;
					case "login":
						login(argument);
						break;
					case "register-user":
						registerUser(argument);
						break;
					default:
						unknownCommand();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				unknownCommand();
			}
		}
	}
	
	/**
	 * Register a new user.
	 * @param userName The users user name.
	 */
	private void registerUser(String userName) {
		String pass = in.nextLine();
		
		User u = new User(userName, pass);
		userRepo.persist(u);
	}

	/**
	 * Log a user in to the auth server.
	 * @param userName The users user name.
	 */
	private void login(String userName) {
		User u = userRepo.get(userName);
		String pass = in.nextLine();
		
		authenticated = u.correctPassword(pass);
		
		println("" + authenticated);
	}

	/**
	 * Register a new file on the network.
	 * @param fileId The id of the file.
	 */
	private void registerFile(String fileId) {
		if (authenticated) {
			String lengthStr = in.nextLine();
			
			int length = Integer.parseInt(lengthStr.split(":")[1]);
			
			PoafsFile f = new PoafsFile(fileId, in.nextLine());
			
			Peer p = peerRepo.get(peerId);
			
			List<FileBlock> newBlocks = new ArrayList<FileBlock>();
			//record that the registering peer has every block
			for (int i = 0; i < length; i++) {
				FileBlock newBlock = new FileBlock(f, i);
				newBlock.addPeer(p);
				
				newBlocks.add(newBlock);
				
				blockRepo.persist(newBlock);
			}
			f.setBlocks(newBlocks);
			
			fileRepo.persist(f);
			
			println("Registered file:" + f.getId());
		} else {
			unauthrorised();
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
	 * Register this peer with an address.
	 * @param input [host]:[port]
	 */
	private void registerPeer(String addr) {
		if (authenticated) {
			String[] address = addr.split(":");
			String host = address[0];
			int port = Integer.parseInt(address[1]);
			
			Peer p = new Peer(peerId, new InetSocketAddress(host, port));
			
			KeyPair keys = km.buildRSAKeyPair();
			
			p.setKeys(keys);
			
			peerRepo.persist(p);

			byte[] key = keys.getPublic().getEncoded();
			println("key length:" + key.length);
			try {
				out.write(key);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			unauthrorised();
		}
	}

	/**
	 * Find which peers have a block.
	 * @param blockId The unique identifier of the block (i.e. [fileId]:[index])
	 */
	private void findBlock(String blockId) {
		if (authenticated) {
			try {
				String[] info = blockId.split(":");
				
				Collection<Peer> peers = fileRepo.get(info[0]).getBlock(Integer.parseInt(info[1])).getPeers();
				
				println("peers length:" + peers.size());
				
				peers.forEach(peer -> println(peer.getId()));
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				println("Error parsing block id");
				System.err.println("Error parsing block id: " + blockId);
				e.printStackTrace();
			}
		} else {
			unauthrorised();
		}
	}

	/**
	 * List all available files.
	 * @param filter Search query (Currently ignored).
	 */
	private void listFiles(String query) {
		if (authenticated) {
			List<PoafsFile> files = fileRepo.getAll();
			
			println("length:" + files.size());
			
			for (PoafsFile f:files) {
				println(f.getId() + " " + f.getName());
			}
		} else {
			unauthrorised();
		}
	}

	/**
	 * Get the host details for a peer.
	 * @param peerId The id of the peer.
	 */
	private void getHost(String peerId) {
		if (authenticated) {
			Peer p = peerRepo.get(peerId);
			
			println(peerId + " " + p.getAddress());
		} else {
			unauthrorised();
		}
	}

	/**
	 * Get the private key for a peer.
	 * @param peerId The peer's id.
	 */
	private void getPrivateKey(String peerId) {
		if (authenticated) {
			byte[] key = peerRepo.get(peerId).getPrivateKey().getEncoded();
			
			println("key length:" + key.length);
			
			try {
				out.write(key);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			unauthrorised();
		}
	}

	/**
	 * Tell the client that it is unauthorised and close the connection.
	 */
	private void unauthrorised() {
		println("Unauthorised");
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
