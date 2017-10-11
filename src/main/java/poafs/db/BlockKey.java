package poafs.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class that represents the primary key of a file block.
 * @author rossrkk
 *
 */
@Embeddable
public class BlockKey implements Serializable {

	public BlockKey() {}
	
	public BlockKey(String fileId, int index) {
		this.index = index;
		this.fileId = fileId;
	}
	
	/**
	 * The blocks index in the file.
	 */
	@Column(name= "block_index")
	private int index;
	
	/**
	 * The id of the parent file.
	 */
    private String fileId;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParentFile() {
		return fileId;
	}

	public void setParentFile(String parentFile) {
		this.fileId = parentFile;
	}
    
}