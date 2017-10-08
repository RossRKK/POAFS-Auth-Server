package poafs.db.entities;

import java.net.InetSocketAddress;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A class that represents a peer on the network.
 * @author rossrkk
 *
 */
@Entity
public class Peer {
	@Id
	/**
	 * The id of this peer.
	 */
	private String id;

	/**
	 * The inet address that this peer can be reached at.
	 */
	private InetSocketAddress address;

	/**
	 * Create a new peer.
	 * @param id The id of the peer.
	 * @param addr The address of the peer.
	 */
	public Peer(String id, InetSocketAddress addr) {
		this.address = addr;
		this.id = id;
	}
	
	public Peer() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InetSocketAddress getAddress() {
		return address;
	}
	
	public void setAddress(InetSocketAddress addr) {
		this.address = addr;
	}
}
