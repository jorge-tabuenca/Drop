package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameOverScreen implements Screen {
    final Drop game;

    Texture backTexture;
    Sound gameOverSound;
    OrthographicCamera camera;
    int total;

    public GameOverScreen(Drop game, int dropsGathered) {
        this.game = game;
        this.total = dropsGathered;

        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameOver.wav"));
        backTexture = new Texture(Gdx.files.internal("fondo2.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        game.batch.begin();
        game.batch.draw(backTexture, 0, 0, 800, 480);
        game.batch.end();

        game.batch.begin();
        game.fontBig.draw(game.batch, "GAME OVER", 300, 300);
        game.fontSmall.draw(game.batch, "PUNTUACION TOTAL: " + total, 20, 20);
        game.batch.end();

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

    }
}
