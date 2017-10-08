package poafs.db;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BlockKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8674604801424355356L;

	private int index;
    
    private String parentFile;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParentFile() {
		return parentFile;
	}

	public void setParentFile(String parentFile) {
		this.parentFile = parentFile;
	}
    
}