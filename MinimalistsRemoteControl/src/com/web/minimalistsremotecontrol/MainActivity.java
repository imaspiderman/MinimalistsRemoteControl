package com.web.minimalistsremotecontrol;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
public class MainActivity extends Activity {

	java.net.Socket s = new java.net.Socket();
	ScrollView svButtons;
	ListView lvButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		svButtons = new ScrollView(this);
		svButtons.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
		lvButtons = new ListView(this);
		lvButtons.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
		svButtons.addView(lvButtons);
		if(!s.isConnected()){
			java.net.InetSocketAddress address = new java.net.InetSocketAddress("10.5.0.9", 9876);
			try{
				s.connect(address);
			}catch(Exception ex){
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			}		
		}
		MakeButtonPanel();
		
		this.setContentView(svButtons);
	}
	
	private void MakeButtonPanel(){
		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));				
		lvButtons.addView(ll);
		for(int i=0; i<10; i++){
			if(i%8 == 0 && i > 0){
				ll = new LinearLayout(this);
				ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));				
				lvButtons.addView(ll);
			}
			ll.addView(MakeButton(String.valueOf(i)));
		}
	}
	
	private Button MakeButton(String sText){
		Button b = new Button(this);
		b.setText(sText);
		
		int width = this.getWindowManager().getDefaultDisplay().getWidth();
		int height = this.getWindowManager().getDefaultDisplay().getHeight();
		
		b.setWidth(width>>3);
		b.setHeight(height>>3);
		
		b.setTextColor(Color.WHITE);
		b.setBackgroundColor(Color.BLACK);
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
					
					s.getOutputStream().write(buffer);
					s.getOutputStream().flush();
				}
				catch(Exception ex){					
				}				
			}
		});
		return b;
	}
	
	private int Protocol(String s){
		int iReturn = 0;
		
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
		
		return iReturn;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
