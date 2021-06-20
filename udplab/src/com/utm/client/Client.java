package com.utm.client;

import com.utm.client.service.EncryptionService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Key;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        EncryptionService encryptionService = new EncryptionService();
        Socket socketForKey = new Socket("127.0.0.1", 8088);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketForKey.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socketForKey.getInputStream());
        objectOutputStream.writeObject(encryptionService.getPublicKey());
        Key serverPublicKey = (Key)objectInputStream.readObject();
        encryptionService.setForeignPublicKey(serverPublicKey);
        socketForKey.close();

        DatagramSocket clientSocket = new DatagramSocket();
        Scanner clientScanner = new Scanner(System.in);
        boolean gameOn = true;
        System.out.println("In order to start the game, type <<game on>>, anything you type is sent to the server, follow the clues and try not to answer too many times. Type <<exit>> to end the game");
        while (gameOn) {
            String data = clientScanner.nextLine();
            if (data.equals("exit")) {
                gameOn = false;
                continue;
            }
            byte[] dataToBeSent = encryptionService.encrypt(data);
            InetAddress inetAddress = InetAddress.getLocalHost();
            DatagramPacket clientPacket = new DatagramPacket(dataToBeSent, dataToBeSent.length, inetAddress, 8088);
            clientSocket.send(clientPacket);

            byte[] received = new byte[50];
            DatagramPacket clientReceivePacket = new DatagramPacket(received, received.length);
            clientSocket.receive(clientReceivePacket);

            String result = new String(clientReceivePacket.getData());
            System.out.println("Server responded with: " + String.valueOf(encryptionService.decrypt(result)));
        }

    }
}
