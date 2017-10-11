package poafs;

import java.io.IOException;

import poafs.db.entities.FileBlock;
import poafs.db.entities.Peer;
import poafs.db.entities.PoafsFile;
import poafs.db.entities.User;
import poafs.db.repo.Repository;
import poafs.keys.KeyManager;
import poafs.net.Server;

/**
 * The main class of the auth server.
 * @author rossrkk
 *
 */
public class Application {
	/**
	 * Start the server.
	 * @param args The command line arguments.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//initialise a new server object
		Server s = new Server(6789, new Repository<PoafsFile>(PoafsFile.class), new Repository<FileBlock>(FileBlock.class), 
				new KeyManager(2048), new Repository<Peer>(Peer.class), new Repository<User>(User.class), false);
		s.run();
	}
}
