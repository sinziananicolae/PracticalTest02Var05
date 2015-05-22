package ro.pub.cs.systems.pdsd.practicaltest02var05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CommunicationThread extends Thread{
	private Socket socket;
	private ServerThread serverThread;

	public CommunicationThread(Socket socket, ServerThread serverThread) {
		this.socket = socket;
		this.serverThread = serverThread;
	}

	public void run() {
		try {
			Log.v("[CommunicationThread]", "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());

			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter printWriter = Utilities.getWriter(socket);
			
			if (bufferedReader != null && printWriter != null) {
				String infoReq = bufferedReader.readLine();
				
				
			}
			
			socket.close();
			Log.v("[CommunicationThread]", "Connection closed");
		} catch (IOException ioException) {
			Log.e("[CommunicationThread]", "An exception has occurred: " + ioException.getMessage());
			ioException.printStackTrace();
		} 
	}
}
