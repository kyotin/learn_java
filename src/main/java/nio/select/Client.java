package nio.select;

import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Client {
    public static void main(String[] args)
            throws IOException, InterruptedException {
        Runnable run1 = () -> {
            try {
                InetSocketAddress hA = new InetSocketAddress("localhost", 8080);
                SocketChannel client = SocketChannel.open(hA);
                System.out.println("The Client is sending messages to server...");
                // Sending messages to the server
                String[] msg = new String[]{"Time goes fast.", "What next?", "Bye Bye"};
                for (int j = 0; j < msg.length; j++) {
                    byte[] message = new String(msg[j]).getBytes();
                    ByteBuffer buffer = ByteBuffer.wrap(message);
                    client.write(buffer);
                    System.out.println(msg[j]);
                    buffer.clear();
                    Thread.sleep(3000);
                }
                client.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        };

        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run1);

        t1.start();
        t2.start();

        Thread.sleep(10000);

    }
}