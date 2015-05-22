package ro.pub.cs.systems.pdsd.practicaltest02var05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CommunicationThread extends Thread {
	private Socket socket;
	private ServerThread serverThread;

	public CommunicationThread(Socket socket, ServerThread serverThread) {
		this.socket = socket;
		this.serverThread = serverThread;
	}

	public DateTime getCurrentTime() throws JSONException {
		DateTime currentDateTime = new DateTime();

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://www.timeapi.org/utc/now";

		HttpGet httpGet = new HttpGet(url);
		ResponseHandler handler = new BasicResponseHandler();

		String content;
		try {
			content = httpClient.execute(httpGet, handler);
			String[] mydate = content.split("[-T:+]");
			currentDateTime.setYear(Integer.parseInt(mydate[0]));
			currentDateTime.setMonth(Integer.parseInt(mydate[1]));
			currentDateTime.setDay(Integer.parseInt(mydate[2]));
			currentDateTime.setHour(Integer.parseInt(mydate[3]));
			currentDateTime.setMinute(Integer.parseInt(mydate[4]));
			currentDateTime.setSecond(Integer.parseInt(mydate[5]));

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentDateTime;
	}

	public void run() {
		try {
			Log.v("[CommunicationThread]",
					"Connection opened with " + socket.getInetAddress() + ":"
							+ socket.getLocalPort());

			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter printWriter = Utilities.getWriter(socket);

			if (bufferedReader != null && printWriter != null) {
				String infoReq = bufferedReader.readLine();
				Log.i("[CommunicationThread]", infoReq);

				HashMap<String, MyClass> data = serverThread.getData();

				String[] parts = infoReq.split(",");
				String result = "";

				if (parts[0].compareTo("get") == 0) {
					DateTime currentDateTime = getCurrentTime();
					if (data.containsKey(parts[1])) {
						MyClass clasa = data.get(parts[1]);
						if (currentDateTime.toLong() - clasa.time.toLong() > 60) {
							data.remove(parts[1]);
							result = "null";
						} else {
							result = data.get(parts[1]).getStr() + " " + data.get(parts[1]).getTime().toString();
						}
					} else {
						result = "null";
					}
				} else if (parts[0].compareTo("put") == 0) {
					DateTime currentDateTime = getCurrentTime();
					if (data.containsKey(parts[1])) {
						MyClass clasa = data.get(parts[1]);
						clasa.setTime(currentDateTime);
						result = "modified";
					} else {
						data.put(parts[1], new MyClass(parts[2],
								currentDateTime));
						result = "inserted";
					}
				}
				
				printWriter.println(result);
				printWriter.flush();

			}

			socket.close();
			Log.v("[CommunicationThread]", "Connection closed");
		} catch (IOException ioException) {
			Log.e("[CommunicationThread]", "An exception has occurred: "
					+ ioException.getMessage());
			ioException.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
