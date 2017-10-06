package poafs.file;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a file on the network.
 * @author rossrkk
 *
 */
public class PoafsFile {
	/**
	 * This files unique ID.
	 */
	private String id;
	
	public PoafsFile(String id) {
		this.id = id;
	}
	
	/**
	 * A list of all the blocks in this file.
	 */
	private List<FileBlock> blocks = new ArrayList<FileBlock>();
	
	public FileBlock getBlock(int index) {
		return blocks.get(index);
	}

	public String getId() {
		return id;
	}
}
