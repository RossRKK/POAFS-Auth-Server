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
	public KeyPair buildRSAKeyPair() {  
        return keyGen.genKeyPair();
    }
}
