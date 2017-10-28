# POAFS-Auth-Server

POAFS is a system to transfer files between peers on a network, much like a torrent.However, the difference is that in order to gain access to a file you must fisrt authenticate with the authroisation server.

This is the code for the POAFS authorisation server. It handles file tracking, user logins and key management for the associated p2p network.
If any peer want a file it must authenticate with this server to access the tracking information and the keys used to decrypt the file when it is recieved.
