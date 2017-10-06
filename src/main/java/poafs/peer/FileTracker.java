package poafs.peer;

import java.util.Collection;
import java.util.HashMap;

import poafs.file.PoafsFile;

/**
 * A class that keeps track of all files on the network.
 * @author rossrkk
 *
 */
public class FileTracker {
	/**
	 * A register containing all tracked files.
	 */
	private HashMap<String, PoafsFile> files = new HashMap<String, PoafsFile>();
	
	/**
	 * Get a collection of peers who have a specific file block.
	 * @param fileId The id of the file.
	 * @param index the index of the block.
	 * @return A collection of peers with a copy of the block.
	 */
	public Collection<Peer> getPeersWithBlock(String fileId, int index) {
		return files.get(fileId).getBlock(index).getPeers();
	}
}
