package com.example.cs112bandroid;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {
	//Testing by Dean
		
	HashMap<String, View> views = new HashMap<String, View>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button hit = (Button) findViewById(R.id.button1);
		Button miss = (Button) findViewById(R.id.button2);
		Button two = (Button) findViewById(R.id.button4);
		Button three1 = (Button) findViewById(R.id.button5);
		Button three2 = (Button) findViewById(R.id.button6);
		Button four = (Button) findViewById(R.id.button7);
		Button five = (Button) findViewById(R.id.button8);
		
		initializeViews();
	}
	
	void initializeViews() {
		views.put("a1", (View) findViewById(R.id.a1));
		views.put("a2", (View) findViewById(R.id.a2));
		views.put("a3", (View) findViewById(R.id.a3));
		views.put("a4", (View) findViewById(R.id.a4));
		views.put("a5", (View) findViewById(R.id.a5));
		views.put("a6", (View) findViewById(R.id.a6));
		views.put("a7", (View) findViewById(R.id.a7));
		views.put("a8", (View) findViewById(R.id.a8));
		views.put("a9", (View) findViewById(R.id.a9));
		views.put("a10", (View) findViewById(R.id.a10));
		
		views.put("b1", (View) findViewById(R.id.b1));
		views.put("b2", (View) findViewById(R.id.b2));
		views.put("b3", (View) findViewById(R.id.b3));
		views.put("b4", (View) findViewById(R.id.b4));
		views.put("b5", (View) findViewById(R.id.b5));
		views.put("b6", (View) findViewById(R.id.b6));
		views.put("b7", (View) findViewById(R.id.b7));
		views.put("b8", (View) findViewById(R.id.b8));
		views.put("b9", (View) findViewById(R.id.b9));
		views.put("b10", (View) findViewById(R.id.b10));
		
		views.put("c1", (View) findViewById(R.id.c1));
		views.put("c2", (View) findViewById(R.id.c2));
		views.put("c3", (View) findViewById(R.id.c3));
		views.put("c4", (View) findViewById(R.id.c4));
		views.put("c5", (View) findViewById(R.id.c5));
		views.put("c6", (View) findViewById(R.id.c6));
		views.put("c7", (View) findViewById(R.id.c7));
		views.put("c8", (View) findViewById(R.id.c8));
		views.put("c9", (View) findViewById(R.id.c9));
		views.put("c10", (View) findViewById(R.id.c10));
		
		views.put("d1", (View) findViewById(R.id.d1));
		views.put("d2", (View) findViewById(R.id.d2));
		views.put("d3", (View) findViewById(R.id.d3));
		views.put("d4", (View) findViewById(R.id.d4));
		views.put("d5", (View) findViewById(R.id.d5));
		views.put("d6", (View) findViewById(R.id.d6));
		views.put("d7", (View) findViewById(R.id.d7));
		views.put("d8", (View) findViewById(R.id.d8));
		views.put("d9", (View) findViewById(R.id.d9));
		views.put("d10", (View) findViewById(R.id.d10));
		
		views.put("e1", (View) findViewById(R.id.e1));
		views.put("e2", (View) findViewById(R.id.e2));
		views.put("e3", (View) findViewById(R.id.e3));
		views.put("e4", (View) findViewById(R.id.e4));
		views.put("e5", (View) findViewById(R.id.e5));
		views.put("e6", (View) findViewById(R.id.e6));
		views.put("e7", (View) findViewById(R.id.e7));
		views.put("e8", (View) findViewById(R.id.e8));
		views.put("e9", (View) findViewById(R.id.e9));
		views.put("e10", (View) findViewById(R.id.e10));
		
		views.put("f1", (View) findViewById(R.id.f1));
		views.put("f2", (View) findViewById(R.id.f2));
		views.put("f3", (View) findViewById(R.id.f3));
		views.put("f4", (View) findViewById(R.id.f4));
		views.put("f5", (View) findViewById(R.id.f5));
		views.put("f6", (View) findViewById(R.id.f6));
		views.put("f7", (View) findViewById(R.id.f7));
		views.put("f8", (View) findViewById(R.id.f8));
		views.put("f9", (View) findViewById(R.id.f9));
		views.put("f10", (View) findViewById(R.id.f10));
		
		views.put("g1", (View) findViewById(R.id.g1));
		views.put("g2", (View) findViewById(R.id.g2));
		views.put("g3", (View) findViewById(R.id.g3));
		views.put("g4", (View) findViewById(R.id.g4));
		views.put("g5", (View) findViewById(R.id.g5));
		views.put("g6", (View) findViewById(R.id.g6));
		views.put("g7", (View) findViewById(R.id.g7));
		views.put("g8", (View) findViewById(R.id.g8));
		views.put("g9", (View) findViewById(R.id.g9));
		views.put("g10", (View) findViewById(R.id.g10));
		
		views.put("h1", (View) findViewById(R.id.h1));
		views.put("h2", (View) findViewById(R.id.h2));
		views.put("h3", (View) findViewById(R.id.h3));
		views.put("h4", (View) findViewById(R.id.h4));
		views.put("h5", (View) findViewById(R.id.h5));
		views.put("h6", (View) findViewById(R.id.h6));
		views.put("h7", (View) findViewById(R.id.h7));
		views.put("h8", (View) findViewById(R.id.h8));
		views.put("h9", (View) findViewById(R.id.h9));
		views.put("h10", (View) findViewById(R.id.h10));
		
		views.put("i1", (View) findViewById(R.id.i1));
		views.put("i2", (View) findViewById(R.id.i2));
		views.put("i3", (View) findViewById(R.id.i3));
		views.put("i4", (View) findViewById(R.id.i4));
		views.put("i5", (View) findViewById(R.id.i5));
		views.put("i6", (View) findViewById(R.id.i6));
		views.put("i7", (View) findViewById(R.id.i7));
		views.put("i8", (View) findViewById(R.id.i8));
		views.put("i9", (View) findViewById(R.id.i9));
		views.put("i10", (View) findViewById(R.id.i10));
		
		views.put("j1", (View) findViewById(R.id.j1));
		views.put("j2", (View) findViewById(R.id.j2));
		views.put("j3", (View) findViewById(R.id.j3));
		views.put("j4", (View) findViewById(R.id.j4));
		views.put("j5", (View) findViewById(R.id.j5));
		views.put("j6", (View) findViewById(R.id.j6));
		views.put("j7", (View) findViewById(R.id.j7));
		views.put("j8", (View) findViewById(R.id.j8));
		views.put("j9", (View) findViewById(R.id.j9));
		views.put("j10", (View) findViewById(R.id.j10));
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
