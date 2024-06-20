import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;

import java.net.URI;

public class SkySocket {
    Socket socket;

    public SkySocket() {
        IO.Options options = IO.Options.builder()
                .setTransports(new String[]{WebSocket.NAME})
                .setForceNew(true)
                .setUpgrade(true)
                .build();
        this.socket = IO.socket(URI.create("wss://ws.postman-echo.com/socketio"),options);
        socket.connect();
    }
}
