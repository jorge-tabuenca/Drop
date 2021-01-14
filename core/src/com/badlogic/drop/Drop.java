package com.badlogic.drop;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private Texture endDroplet;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;

	private BitmapFont font;
	private int score = 0;

	// use Array because the ArrayList generates trash
	private Array<Rectangle> raindrops;
	// last waterDrop, its a time value so we use long
	private long lastDropTime;

	private Texture backTexture;
	
	@Override
	public void create () {
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		endDroplet = new Texture(Gdx.files.internal("endDroplet.png"));

		backTexture = new Texture(Gdx.files.internal("fondo.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera, to show always 800x480 area of our game
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create the SpriteBatch
		batch = new SpriteBatch();

		// especs that will have bucket at the beginning
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(2);
	}

	@Override
	public void render () {
		// first make the windows color dark-blue
		// second begin OpenGL to clean the window
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.begin();
		batch.draw(backTexture, 0, 0, 800, 480);
		batch.end();

		// render the bucket
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		batch.end();

		// make the bucket move mouse/touch
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		//make the bucket move rows kwyboard
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// make the bucket always in the limits of the screen
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;

		// verify the time pas from the last waterDrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		// make waterDrop move and in the bot of the screen deleted
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0){
				iter.remove();
			}

		// make noise if the  waterDrop touch the bucket
		// overlaps search if too rectangles are touching
			if(raindrop.overlaps(bucket)) {
				dropSound.play();

				score ++;
				Gdx.app.log("Score", String.valueOf(score));

				iter.remove();
			}
		}

		batch.begin();
		font.draw(batch,"Score: " + String.valueOf(score), 5, 460);
		batch.end();

		// render waterDrops
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

	}

	// good practice help the system to close all the elements
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	// we make a method to make a new rectangle and put a new waterDrop
	// on the top of the screen random and add to raindrops
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
