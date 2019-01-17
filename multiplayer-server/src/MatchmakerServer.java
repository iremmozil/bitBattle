import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MatchmakerServer {
    static Server server;

    private MatchmakerServer() {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };

        Event.register(server);

        ArrayList<PlayerConnection> playerList = new ArrayList<>();
        //Lock lock = new ReentrantLock(); // Create a lock
        server.addListener(new Listener.ThreadedListener(new Listener() {
            public void connected (Connection conn) {
                // When a player connects, put player into list
                PlayerConnection connection = (PlayerConnection) conn;
                // TODO: Acquire lock here for playerList
                //  lock.lock();
                playerList.add(connection);
                if (playerList.size()>0) {
                    System.out.println("Player is put to playList!\n");
                    System.out.println("PlayerList size: " + playerList.size() + "\n");
                }
                //  lock.unlock();
            }

            public void received (Connection conn, Object object) {
                PlayerConnection connection = (PlayerConnection) conn;
                if (object instanceof Event.MovePlayer) {
                    server.sendToTCP(connection.opponent.getID(), object);
                    return;
                }

                if (object instanceof Event.FirePlayer) {
                    server.sendToTCP(connection.opponent.getID(), object);
                    return;
                }

                if (object instanceof Event.AlienIsDead) {
                    // TODO: Mark alien as dead
                    connection.aliens.remove(((Event.AlienIsDead) object).alienIndex);
                    connection.opponent.aliens.remove(((Event.AlienIsDead) object).alienIndex);
                    return;
                }

                if (object instanceof Event.OpponentDead) {
                    server.sendToTCP(connection.opponent.getID(), object);
                }
            }

            public void disconnected (Connection conn) {
                PlayerConnection connection = (PlayerConnection) conn;
                // TODO: Acquire lock here for playerList
                //  lock.lock();
                playerList.remove(connection);
                //  lock.unlock();
            }
        }));
        try {
            server.bind(Event.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();

        System.out.println("Listener thread created\n");
        while(true) {
            System.out.println("Waiting for players...\n");
            if (playerList.size() > 1) {
                System.out.println("Players came!\n");
                new Thread( () -> {
                    PlayerConnection player1;
                    PlayerConnection player2;

                    // When playList has 2 entities, pop them and start the threads with player sockets
                    // TODO: Acquire lock here for playerList
                    //  lock.lock();

                    System.out.println("LET'S GET THIS STARTED E HA\n");
                    player1 = playerList.remove(0);
                    player2 = playerList.remove(0);

                    player1.opponent = player2;
                    player2.opponent = player1;

                    Event.StartandMoveAliens startandMoveAliens = new Event.StartandMoveAliens();
                    startandMoveAliens.spaceshipx = 105; //TODO: MAGIC NUMBER
                    startandMoveAliens.opponentx = 315;
                    server.sendToTCP(player1.getID(), startandMoveAliens);
                    startandMoveAliens.spaceshipx = 315;
                    startandMoveAliens.opponentx = 105;
                    server.sendToTCP(player2.getID(), startandMoveAliens);

                    new Thread(new syncAlienTask(player1, player2)).start();

                    //  lock.unlock();

                }).start();
            }
        }

    }

    static class PlayerConnection extends Connection {
        PlayerConnection opponent;
        int noOfAliens = 23;
        ArrayList<Integer> aliens = new ArrayList<>(Collections.nCopies(noOfAliens, 1));
    }

    public static class syncAlienTask implements Runnable {
        private PlayerConnection player1;
        private PlayerConnection player2;

        syncAlienTask(PlayerConnection player1, PlayerConnection player2) {
            this.player1 = player1;
            this.player2 = player2;
            System.out.println("syncAlienTask is created by the second thread\n");
        }

        public void run() {
            Event.AlienFired alienFired = new Event.AlienFired();

            while (player1.isConnected() && player2.isConnected() && player1.aliens.size() != 0) {
                Random rand = new Random();
                int ind = rand.nextInt(player1.aliens.size());
                if (player1.aliens.get(ind) == 0 || player2.aliens.get(ind) == 0) {
                    continue;
                }
                alienFired.alienIndex = ind;
                server.sendToTCP(player1.getID(),alienFired);
                server.sendToTCP(player2.getID(),alienFired);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!player1.isConnected()) {
                server.sendToTCP(player2.getID(), new Event.OpponentDisconnected());
                player1.close();
            }

            else if (!player2.isConnected()) {
                server.sendToTCP(player1.getID(), new Event.OpponentDisconnected());
                player2.close();
            }

        }
    }

    public static void main (String[] args) {
        new MatchmakerServer();
    }
}
