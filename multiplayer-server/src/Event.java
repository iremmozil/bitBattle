import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Event {

    static public final int port = 8000;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(MovePlayer.class);
        kryo.register(FirePlayer.class);
        kryo.register(StartandMoveAliens.class);
        kryo.register(AlienFired.class);
        kryo.register(AlienIsDead.class);
        kryo.register(OpponentDisconnected.class);

    }

    static public class  MovePlayer {
        public double x;
    }

    static public class FirePlayer {
        public double x;
    }

    static public class StartandMoveAliens {

    }

    static public class AlienFired {
        public int alienIndex;
    }

    static public class AlienIsDead {
        public int alienIndex;
    }

    static public class OpponentDisconnected {

    }
}
