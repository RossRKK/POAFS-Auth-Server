package poafs;

import java.io.IOException;

import poafs.db.Properties;
import poafs.db.entities.Peer;
import poafs.db.entities.PoafsFile;
import poafs.db.repo.Repository;
import poafs.keys.KeyManager;
import poafs.net.Server;

public class Application {
	public static void main(String[] args) throws IOException {
		Properties.setup();
		
		Server s = new Server(6789, new Repository<PoafsFile>(PoafsFile.class), new KeyManager(2048), new Repository<Peer>(Peer.class), false);
		s.run();
	}
}
