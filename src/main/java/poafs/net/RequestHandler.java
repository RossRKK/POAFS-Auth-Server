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

import poafs.db.BlockKey;
import poafs.db.entities.FileBlock;
import poafs.db.entities.Peer;
import poafs.db.entities.PoafsFile;
import poafs.db.repo.Repository;
import poafs.keys.KeyManager;

/**
 * Class that handles incoming requests.
 * @author rossrkk
 *
 */
public class RequestHandler implements Runnable {
	
	private Socket s;
	
	private BufferedOutputStream out;
	
	private Scanner in;
	
	private Repository<PoafsFile> fileRepo;
	
	private Repository<FileBlock> blockRepo;
	
	private KeyManager km;
	
	private String peerId;
	
	private Repository<Peer> peerRepo;
	
	/**
	 * Setup the input and output streams.
	 * @param s The connected socket.
	 * @throws IOException
	 */
	public RequestHandler(Socket s, Repository<PoafsFile> fileRepo, Repository<FileBlock> fileBlockRepo, KeyManager km, Repository<Peer> peerRepo) throws IOException {
		this.s = s;
		out = new BufferedOutputStream(s.getOutputStream());
		in = new Scanner(s.getInputStream());
		
		this.fileRepo = fileRepo;
		this.km = km;
		this.peerRepo = peerRepo;
		this.blockRepo = fileBlockRepo;
	}

	/**
	 * Handle incoming requests.
	 */
	@Override
	public void run() {
		//TODO authenticate the connection
		println("POAFS Auth 0.1");
		
		peerId = in.nextLine();
		
		while (in.hasNextLine()) {
			try {
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
					case "register-peer":
						registerPeer(argument);
						break;
					case "register-file":
						registerFile(argument);
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
	 * Register a new file on the network.
	 * @param fileId The id of the file.
	 */
	private void registerFile(String fileId) {
		String lengthStr = in.nextLine();
		
		int length = Integer.parseInt(lengthStr.split(":")[1]);
		
		PoafsFile f = new PoafsFile(fileId, in.nextLine());
		
		Peer p = peerRepo.get(peerId);
		
		List<FileBlock> newBlocks = new ArrayList<FileBlock>();
		//record that the registering peer has every block
		for (int i = 0; i < length; i++) {
			FileBlock newBlock = new FileBlock(fileId, i);
			newBlock.addPeer(p);
			
			newBlocks.add(newBlock);
			
			newBlock.setId(new BlockKey(fileId, i));
			
			p.addBlock(newBlock);
		}
		f.setBlocks(newBlocks);
		
		peerRepo.update(p);
		fileRepo.persist(f);
		
		println("Registered file:" + f.getId());
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
		String[] address = addr.split(":");
		String host = address[0];
		int port = Integer.parseInt(address[1]);
		
		peerRepo.persist(new Peer(peerId, new InetSocketAddress(host, port)));
		
		KeyPair keys = km.registerPeer(peerId);
		
		byte[] key = keys.getPublic().getEncoded();
		println("key length:" + key.length);
		try {
			out.write(key);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find which peers have a block.
	 * @param blockId The unique identifier of the block (i.e. [fileId]:[index])
	 */
	private void findBlock(String blockId) {
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
		Peer p = peerRepo.get(peerId);
		
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
