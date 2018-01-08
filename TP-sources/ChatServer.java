/*
 * Fichier récupéré sur https://gist.github.com/alex-zykov/b4052e3c1b6891081897
 * Deux lignes sont importantes ici :
 * - Ligne 17 : Le port d'écoute du serveur
 * - Ligne 45 : Dé/Commenter cette ligne permet de renvoyer le message à l'émetteur ou non
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {

    //<editor-fold desc="Constants">
    public static final int PORT = 5217;
    //</editor-fold>

    //<editor-fold desc="Fields">
    private ServerSocket serverSocket;
    private List<ClientThread> clientThreads = new ArrayList<ClientThread>();
    //</editor-fold>

    //<editor-fold desc="Server Functions">
    public void connect(Socket socket) {
        try {
            clientThreads.add(new ClientThread(socket));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void disconnect(ClientThread client) {
        Iterator<ClientThread> itr = clientThreads.iterator();
        while(itr.hasNext()) {
            if (itr.next().equals(client))
                itr.remove();
            break;
        }
    }

    public void broadcast(ClientThread activeClient, String message) {
        for(ClientThread client: clientThreads) {
            //if(!client.equals(activeClient))
                client.sendMessage(message);
        }
    }

    public void process() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on " + PORT);
            while(true) {
                Socket socket = serverSocket.accept();
                connect(socket);
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Main">
    public static void main(String[] args) {
        new ChatServer().process();
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes">
    private class ClientThread extends Thread {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientThread(Socket socket) throws IOException {
            this.clientSocket = socket;
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(),true);
            start();
        }

        public void readMessage(String message) {
            broadcast(this, message);
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        public void close() {
            try {
                if(input != null)
                    input.close();
                if(output != null)
                    output.close();
                if(clientSocket != null)
                    clientSocket.close();
                disconnect(this);
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

        public void run() {
            String message;
            try {
                while(true) {
                    message = input.readLine();
                    if(message == null) {
                        close();
                        break;
                    }
                    readMessage(message);
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            } finally {
                close();
            }
        }

    }
    //</editor-fold>

}
