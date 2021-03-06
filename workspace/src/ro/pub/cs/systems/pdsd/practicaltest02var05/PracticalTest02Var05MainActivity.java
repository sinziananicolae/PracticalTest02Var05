package ro.pub.cs.systems.pdsd.practicaltest02var05;

import org.w3c.dom.Text;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02Var05MainActivity extends Activity {
	
	Button startButton, serverConnectButton;
	EditText address, port, info, serverPortEditText;
	TextView infoTextView;
	
	ServerThread serverThread;
	ClientThread clientThread;
	
	private ServerConnectButtonListener serverConnectButtonListener = new ServerConnectButtonListener();

	private class ServerConnectButtonListener implements Button.OnClickListener {

		@Override
		public void onClick(View view) {
			String serverPort = serverPortEditText.getText().toString();
			Log.d("TAG", "Am apasat buton server " + serverPort);
			if (serverPort == null || serverPort.isEmpty()) {
				Toast.makeText(getApplicationContext(),
						"Server port should be filled!", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			
			serverThread = new ServerThread();
			serverThread.startServer(Integer.parseInt(serverPort));
		}
	}
	
	private GetInformationButtonClickListener getInformationButtonClickListener = new GetInformationButtonClickListener();
	private class GetInformationButtonClickListener implements
			Button.OnClickListener {

		@Override
		public void onClick(View view) {
			String clientAddress = address.getText().toString();
			String clientPort = port.getText().toString();

			if (clientAddress == null || clientAddress.isEmpty()
					|| clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(getApplicationContext(),
						"Client port and address should be filled!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			String information = info.getText().toString();

			if (information == null || information.isEmpty()) {
				Toast.makeText(
						getApplicationContext(),
						"Parameters from client (city / information type) should be filled!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e("[MAIN Activity]", "There is no server to connect to!");
			      return;
			}
			
			
			clientThread = new ClientThread(Integer.parseInt(clientPort), clientAddress, information, infoTextView);
			clientThread.start();
		}

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_var05_main);
		
		startButton = (Button)findViewById(R.id.connect_button);
		startButton.setOnClickListener(getInformationButtonClickListener);
		
		address = (EditText)findViewById(R.id.client_address_edit_text);
		port = (EditText)findViewById(R.id.client_port_edit_text);
		info = (EditText)findViewById(R.id.info);
		infoTextView = (TextView)findViewById(R.id.infoTextView);
		serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
		serverConnectButton = (Button)findViewById(R.id.connect_button_server);
		serverConnectButton.setOnClickListener(serverConnectButtonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_var05_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
	      if (serverThread != null) {
	        serverThread.stopServer();
	      }
	      super.onDestroy();
	    }
}
