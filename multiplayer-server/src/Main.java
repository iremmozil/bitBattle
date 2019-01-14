import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static int mpServerPort = 8000;

    private static int triangleAlienHealth = 1;
    private static int circleAlienHealth = 1;
    private static int paralellogramAlienHealth = 2;

    private static int noTriangleAlien = 10;
    private static int noCircleAlien = 10;
    private static int noParalellogramAlien = 10;

    public static void main(String[] args) {

        ArrayList<Socket> playerList = new ArrayList<>();
        new Thread( () -> {
            try {
                // Handle connections with clients over a port
                ServerSocket mpServer = new ServerSocket(mpServerPort);

                while (true) {
                    // If a client connects to the server, create a thread for the client and add it to playerList
                    Socket player = mpServer.accept();
                    playerList.add(player);

                    // When playList has 2 entities, pop them and start the threads with player sockets
                    if (playerList.size() > 1) {
                        Socket player1 = playerList.get(0);

                        // Check whether player1 is still connected or not
                        if (player1.getInputStream().read() == -1) {
                            playerList.remove(0);
                        }

                        else {
                            Socket player2 = playerList.get(1);

                            // Check whether player2 is still connected or not
                            if (player2.getInputStream().read() == -1) {
                                playerList.remove(1);

                            } else {
                                // Pop 2 player from the list and create a thread for level 4 between these players
                                playerList.remove(0);
                                playerList.remove(0);

                                new Thread(new playGameTask(player1, player2)).start();
                            }
                        }
                    }
                }
            }
          catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    // A thread for client to play a level 4 game
    public static class playGameTask implements Runnable {
        private Socket player1;
        private Socket player2;

        private static AlienList triangleAliens = new AlienList(noTriangleAlien, triangleAlienHealth);
        private static AlienList circleAliens = new AlienList(noCircleAlien, circleAlienHealth);
        private static AlienList paralellogramAliens = new AlienList(noParalellogramAlien, paralellogramAlienHealth);

        private DataInputStream fromPlayer1;
        private DataOutputStream toPlayer1;
        private DataInputStream fromPlayer2;
        private DataOutputStream toPlayer2;

        playGameTask(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;

        }

        public void run() {
            // Receive client and his/her opponent

            // Listen the actions and events from client and send to opponent

            // If the action is about alien shooting, call AlienList.update()
        }
    }

    // An inner class for aliens
    public static class AlienList {

        private static List<Integer> aliens;

        AlienList(int size, int health) {
            aliens = new ArrayList<>(Collections.nCopies(size, health));
        }

        public synchronized boolean update(int index) {
            if (aliens.get(index) > 0) {
                aliens.set(index, aliens.get(index) - 1);
                return true;
            } else
                return false;
        }
    }
}
