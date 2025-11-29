package com.uavguard.wrj12620;

import com.uavguard.sdk.Video;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class WRJ12620_Video implements Video {

    private Consumer<byte[]> callback;
    private int port = 8080;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private boolean writing = false;

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setup(DatagramSocket socket, InetAddress ip) throws Exception {
        byte[] resumeBytes = "Bv".getBytes(StandardCharsets.UTF_8);

        DatagramPacket resume = new DatagramPacket(
            resumeBytes,
            resumeBytes.length,
            ip,
            port
        );

        socket.send(resume);
    }

    @Override
    public void loop(DatagramSocket socket, InetAddress ip, byte[] data)
        throws Exception {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == (byte) 0xFF && i + 1 < data.length) {
                byte next = data[i + 1];

                if (next == (byte) 0xD8) {
                    writing = true;
                    buffer.reset();
                    buffer.write((byte) 0xFF);
                    buffer.write((byte) 0xD8);
                    i++;
                    continue;
                }

                if (writing && next == (byte) 0xD9) {
                    buffer.write((byte) 0xFF);
                    buffer.write((byte) 0xD9);
                    i++;

                    if (callback != null) {
                        callback.accept(buffer.toByteArray());
                    }

                    writing = false;
                    buffer.reset();
                    continue;
                }
            }

            if (writing) {
                buffer.write(data[i]);
            }
        }
    }

    @Override
    public void setCallback(Consumer<byte[]> callback) {
        this.callback = callback;
    }
}
