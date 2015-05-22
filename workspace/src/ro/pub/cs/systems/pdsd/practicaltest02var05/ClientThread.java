package ro.pub.cs.systems.pdsd.practicaltest02var05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread{
	private Socket socket;
	private String address;
	private int port;
	private String infoRequired;
	private TextView infoView;
	
	public ClientThread(int port, String address, String infoReq, TextView infoView) {
		this.port = port;
		this.address = address;
		this.infoRequired = infoReq;
		this.infoView = infoView;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(address, port);
			
			if (socket == null) {
				Log.e("[ClientThread]", "Could not create socket!");
			}
			Log.d("[ClientThread]", "Client is running on address and port: " + address + " " + port);
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter printWriter = Utilities.getWriter(socket);
			
			printWriter.println(infoRequired);
			printWriter.flush();
			
			String value;
			while((value = bufferedReader.readLine()) != null) {
				final String ceva = value;
				infoView.post(new Runnable() {
					
					@Override
					public void run() {
						infoView.setText(ceva);
						
					}
				});
			}
			
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
