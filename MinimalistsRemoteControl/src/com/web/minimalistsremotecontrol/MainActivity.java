package com.web.minimalistsremotecontrol;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class MainActivity extends Activity {
		
	LinearLayout llvert;
	int _id = 1;
	java.net.Socket _s;
	int _AddressId = 0;
	int _PortId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		llvert = new LinearLayout(this);
		llvert.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		llvert.setId(_id++);
		llvert.setBackgroundColor(Color.BLACK);
		MakeConnectPanel();
		
		this.setContentView(llvert);
	}
	
	private void MakeConnection(String ip, String port){
		java.net.InetSocketAddress address = new java.net.InetSocketAddress(ip, Integer.parseInt(port));
		try{				
			_s = new java.net.Socket();
			_s.connect(address,3000);
		}catch(Exception ex){
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void MakeConnectPanel(){
		EditText etPort = new EditText(this);
		EditText etAddress = new EditText(this);
		Button bConnect = new Button(this);
		RelativeLayout rl = new RelativeLayout(this);
		rl.setId(_id++);
		rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		etAddress.setWidth(150);
		etAddress.setText("Address");
		etAddress.setId(_id++);
		_AddressId = etAddress.getId();

		rl.addView(etAddress);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);		
		lp.addRule(RelativeLayout.RIGHT_OF, (_id-1));
		etPort.setWidth(80);
		etPort.setText("Port");
		etPort.setId(_id++);
		_PortId = etPort.getId();
		
		rl.addView(etPort,lp);
		lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);		
		lp.addRule(RelativeLayout.BELOW, (_id-2));
		bConnect.setId(_id++);
		bConnect.setText("Connect");
		rl.addView(bConnect,lp);
		
		bConnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MakeConnection(((EditText)((View)v.getParent()).findViewById(_AddressId)).getText().toString(),((EditText)((View)v.getParent()).findViewById(_PortId)).getText().toString());
				llvert.removeAllViews();
				MakeButtonPanel();
				setContentView(llvert);
			}
		});
		llvert.addView(rl);
	}
	
	private void MakeButtonPanel(){
		RelativeLayout rl = new RelativeLayout(this);
		rl.setId(_id++);
		rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		
		rl.addView(MakeButton("<--"));
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);		
		lp.addRule(RelativeLayout.RIGHT_OF, (_id-1));
		rl.addView(MakeButton("-->"), lp);
		lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);		
		lp.addRule(RelativeLayout.RIGHT_OF, (_id-1));
		rl.addView(MakeButton("ESC"), lp);
		lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);		
		lp.addRule(RelativeLayout.RIGHT_OF, (_id-1));
		rl.addView(MakeButton("EXIT"), lp);
		
		llvert.addView(rl);
	}
	
	private Button MakeButton(String sText){
		Button b = new Button(this);
		b.setId(_id++);
		b.setText(sText);
		
		int width = this.getWindowManager().getDefaultDisplay().getWidth();
		int height = this.getWindowManager().getDefaultDisplay().getHeight();
		
		b.setWidth(width>>2);
		b.setHeight(height>>2);
		
		b.setTextSize(b.getWidth()>>1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button thisButton = (Button)v;
				int iCode = Protocol(thisButton.getText().toString());
				try{
					byte[] buffer = new byte[5];
					buffer[0] = 3;
					buffer[1] = (byte)(iCode>>24);
					buffer[2] = (byte)(iCode>>16);
					buffer[3] = (byte)(iCode>>8);
					buffer[4] = (byte)(iCode);
					
					_s.getOutputStream().write(buffer);
					_s.getOutputStream().flush();
				}
				catch(Exception ex){					
				}				
			}
		});
		return b;
	}
	
	private int Protocol(String s){
		int iReturn = 0;
		
		if(s.equalsIgnoreCase("EXIT")) iReturn = -1;
		if(s.equalsIgnoreCase("0")) iReturn = 0;
		if(s.equalsIgnoreCase("1")) iReturn = 1;
		if(s.equalsIgnoreCase("2")) iReturn = 2;
		if(s.equalsIgnoreCase("3")) iReturn = 3;
		if(s.equalsIgnoreCase("4")) iReturn = 4;
		if(s.equalsIgnoreCase("5")) iReturn = 5;
		if(s.equalsIgnoreCase("6")) iReturn = 6;
		if(s.equalsIgnoreCase("7")) iReturn = 7;
		if(s.equalsIgnoreCase("8")) iReturn = 8;
		if(s.equalsIgnoreCase("9")) iReturn = 9;
		if(s.equalsIgnoreCase("ESC")) iReturn = 10;
		if(s.equalsIgnoreCase("F1")) iReturn = 11;
		if(s.equalsIgnoreCase("F2")) iReturn = 12;
		if(s.equalsIgnoreCase("F3")) iReturn = 13;
		if(s.equalsIgnoreCase("F4")) iReturn = 14;
		if(s.equalsIgnoreCase("F5")) iReturn = 15;
		if(s.equalsIgnoreCase("F6")) iReturn = 16;
		if(s.equalsIgnoreCase("F7")) iReturn = 17;
		if(s.equalsIgnoreCase("F8")) iReturn = 18;
		if(s.equalsIgnoreCase("F9")) iReturn = 19;
		if(s.equalsIgnoreCase("F10")) iReturn = 20;
		if(s.equalsIgnoreCase("F11")) iReturn = 21;
		if(s.equalsIgnoreCase("F12")) iReturn = 22;
		if(s.equalsIgnoreCase("F13")) iReturn = 23;
		if(s.equalsIgnoreCase("F14")) iReturn = 24;
		if(s.equalsIgnoreCase("F15")) iReturn = 25;
		if(s.equalsIgnoreCase("F16")) iReturn = 26;
		if(s.equalsIgnoreCase("F17")) iReturn = 27;
		if(s.equalsIgnoreCase("F18")) iReturn = 28;
		if(s.equalsIgnoreCase("F19")) iReturn = 29;
		if(s.equalsIgnoreCase("F20")) iReturn = 30;
		if(s.equalsIgnoreCase("F21")) iReturn = 31;
		if(s.equalsIgnoreCase("F22")) iReturn = 32;
		if(s.equalsIgnoreCase("F23")) iReturn = 33;
		if(s.equalsIgnoreCase("F24")) iReturn = 34;
		if(s.equalsIgnoreCase("<--")) iReturn = 35;
		if(s.equalsIgnoreCase("-->")) iReturn = 36;
		
		return iReturn;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
