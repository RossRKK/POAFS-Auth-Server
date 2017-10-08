package poafs.db.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A class representing a file on the network.
 * @author rossrkk
 *
 */
@Entity
public class PoafsFile {
	/**
	 * This files unique ID.
	 */
	@Id
	private String id;
	
	/**
	 * The name of the file.
	 */
	private String name;
	
	public PoafsFile(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public PoafsFile() {}
	
	/**
	 * A list of all the blocks in this file.
	 */
	private List<FileBlock> blocks = new ArrayList<FileBlock>();
	
	public FileBlock getBlock(int index) {
		return blocks.get(index);
	}
	
	public int getLength() {
		return blocks.size();
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<FileBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<FileBlock> blocks) {
		this.blocks = blocks;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
