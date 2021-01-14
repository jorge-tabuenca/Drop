package com.badlogic.drop;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.drop.Drop;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//Ahorrar bateria descativando el acelerometro y la brujula
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new Drop(), config);
	}
}
