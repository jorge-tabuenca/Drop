package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    final Drop game;
    Texture backTexture;
    Music mainMenuMusic;

    OrthographicCamera camera;

    public MainMenuScreen(final Drop game) {
        this.game = game;

        backTexture = new Texture(Gdx.files.internal("mainMenuBack.png"));

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {
        mainMenuMusic.play();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backTexture, 0, 0, 800, 480);
        game.batch.end();

        game.batch.begin();
        game.fontBig.draw(game.batch, "Welcome to Drop!!! ", 300, 400);
        game.fontSmall.draw(game.batch, "Tap anywhere to begin!", 350, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mainMenuMusic.dispose();
    }
}
