package poafs.db.entities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String userName;
	
	private byte[] salt;
	
	private byte[] hash;
	
	public User(String userName, String password) {
		this.userName = userName;
		generateSalt();
		this.hash = hash(password);
	}
	
	public User() {}
	
	/**
	 * Determine whether this is the users password.
	 * @param password The prospective password.
	 * @return Whether the password is correct.
	 */
	public boolean correctPassword(String password) {
		byte[] pass = hash(password);
		
		for (int i = 0 ; i < pass.length && i < hash.length; i++) {
			if (pass[i] != hash[i]) {
				return false;
			}
		}
		return true;
	}
	
	private byte[] hash(String password) {
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			
			return f.generateSecret(spec).getEncoded();
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void generateSalt() {
		SecureRandom random = new SecureRandom();
		salt = new byte[20];
		random.nextBytes(salt);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	
	
}
