package com.utm.server;

import com.utm.server.service.EncryptionService;
import com.utm.server.service.ResponseService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.security.Key;

public class Server {
    public static void main(String[] args) throws Exception {

        EncryptionService encryptionService = new EncryptionService();
        ServerSocket serverSocketForKey = new ServerSocket(8088);
        Socket socket = serverSocketForKey.accept();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Key receivedKey = (Key) objectInputStream.readObject();
        objectOutputStream.writeObject(encryptionService.getPublicKey());
        encryptionService.setForeignPublicKey(receivedKey);
        serverSocketForKey.close();

        DatagramSocket serverSocket = new DatagramSocket(8088);
        ResponseService responseService = new ResponseService();

        while (responseService.isGameOn()) {
            byte[] data = new byte[50];
            DatagramPacket serverPacket = new DatagramPacket(data, data.length);
            serverSocket.receive(serverPacket);
            String clientMessage = String.valueOf(serverPacket.getData());
            System.out.println("Client says: " + String.valueOf(encryptionService.decrypt(clientMessage)));
            String result = responseService.getResponse(clientMessage);
            byte[] returnBytes = encryptionService.encrypt(String.valueOf(result));
            InetAddress inetAddress = InetAddress.getLocalHost();
            DatagramPacket serverResponsePacket = new DatagramPacket(returnBytes, returnBytes.length, inetAddress, serverPacket.getPort());
            serverSocket.send(serverResponsePacket);
        }
    }
}
