package poafs.keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

/**
 * A class that manages all of the keys on the network.
 * @author rossrkk
 *
 */
public class KeyManager {
	
	/**
	 * The key generator object.
	 */
	KeyPairGenerator keyGen;
	
	public KeyManager(int keySize) {
		try {
	        keyGen = KeyPairGenerator.getInstance("RSA");
	        keyGen.initialize(keySize); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate a key pair.
	 * @return A key pair.
	 */
	private KeyPair buildRSAKeyPair() {  
        return keyGen.genKeyPair();
    }
	
	/**
	 * A map of every peer and their associated keys.
	 */
	private HashMap<String, KeyPair> keyRegister = new HashMap<String, KeyPair>();
	
	/**
	 * Register a peer and return its new keys.
	 * @param peerId The id of the peer.
	 * @return The peer's new keys.
	 */
	public KeyPair registerPeer(String peerId) {
		KeyPair keys = buildRSAKeyPair();
		keyRegister.put(peerId, keys);
		
		return keys;
	}

	/**
	 * Get a peer's private key.
	 * @param peerId The id of the peer.
	 * @return The relevant private key.
	 */
	public PrivateKey getPrivateKeyForPeer(String peerId) {
		return keyRegister.get(peerId).getPrivate();
	}
	
	/**
	 * Get a peers public key.
	 * @param peerId The id of the peer.
	 * @return The peers public key.
	 */
	public PublicKey getPublicKeyForPeer(String peerId) {
		return keyRegister.get(peerId).getPublic();
	}
}
