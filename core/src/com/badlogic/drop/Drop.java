package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Drop extends Game {

	public SpriteBatch batch;
	public BitmapFont fontBig;
	public BitmapFont fontSmall;

	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		fontBig = new BitmapFont();
		fontBig.getData().setScale(2);
		fontSmall = new BitmapFont();
		fontSmall.getData().setScale(1);
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		batch.dispose();
		fontBig.dispose();
		fontSmall.dispose();
	}
}