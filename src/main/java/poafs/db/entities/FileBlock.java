package poafs.db.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import poafs.db.BlockKey;


/**
 * A class representing a single block of a file on the network.
 * @author rossrkk
 *
 */
@Entity
public class FileBlock {	
	/**
	 * The index of this file block.
	 */
	@EmbeddedId
	private BlockKey id;
	
	/**
	 * A hashmap containing the peers that have a copy of this file block.
	 */
	@ManyToMany
	private Collection<Peer> peers = new ArrayList<Peer>();
	
	@MapsId("parentFile")
    @ManyToOne
    private PoafsFile parentFile;

	/* Getters and Setters */
	
	public BlockKey getId() {
		return id;
	}

	public void setId(BlockKey id) {
		this.id = id;
	}

	public Collection<Peer> getPeers() {
		return peers;
	}

	public void setPeers(Collection<Peer> peers) {
		this.peers = peers;
	}
}
