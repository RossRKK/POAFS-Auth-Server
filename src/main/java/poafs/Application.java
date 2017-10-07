package poafs;

import java.io.IOException;

import poafs.keys.KeyManager;
import poafs.net.Server;
import poafs.peer.FileTracker;

public class Application {
	public static void main(String[] args) throws IOException {
		Server s = new Server(6789, new FileTracker(), new KeyManager(2048), false);
		s.run();
	}
}
