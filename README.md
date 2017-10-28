# POAFS-Auth-Server

POAFS is a system by which files can be transferred between peers on a network, much like a torrent. However, on a POAFS network files are encrypted using asymmetric encryption, this allows all of the files to be controlled by a single server that keeps track of the keys. The aim of this project was to be able to take a service like Netflix and Spotify and reduce their server load by having the actual file transfers happen between users. To that end it is possible, using the application to stream media files to VLC media player over http (from your locally installed peer). Ideally however the media player would be embedded in the application as this also means that the decrypted form of the file can be removed from the network and thereby circumvents the authorization server.

The whole project is written in Java, JPA is used by the authorization server to persist keys, tracking and user data. Gradle is used to build the project.

This is the code for the POAFS authorisation server. It handles file tracking, user logins and key management for the associated p2p network.
If any peer want a file it must authenticate with this server to access the tracking information and the keys used to decrypt the file when it is recieved.
