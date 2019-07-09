package net.chinahrd.handle;

import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@ServerEndpoint("/tail/tomcat")
public class LogWebSocketHandle {

    private Process process;
    private InputStream inputStream;

    private String common = "tail -f /usr/local/tomcat/logs/catalina.out";
    // private String common = "tail -f D:/tomcat7.49/logs/catalina.2018-01-04.log";

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            process = Runtime.getRuntime().exec(common);
            inputStream = process.getInputStream();

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebSocket请求关闭
     */
    public void onClone() {
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}
