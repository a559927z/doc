package net.chinahrd.handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.websocket.Session;

public class TailLogThread extends Thread {

	private BufferedReader br;
	private Session session;

	public TailLogThread(InputStream is, Session session) {
		this.br = new BufferedReader(new InputStreamReader(is));
		this.session = session;
	}

	@Override
	public void run() {
		String line;
		try {
			while ((line = br.readLine()) != null) {
				// 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
				session.getBasicRemote().sendText(line + "<br>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}

}
