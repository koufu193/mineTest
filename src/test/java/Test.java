import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.Sys;

import java.util.Random;

public class Test extends LwjglApplication{
    listener listener;
    public Test() {
        super(new listener(), "Game", 500, 500);
        listener = (listener) getApplicationListener();
    }
    public static void main(String[] args) {
        new Test();
    }

}
class listener implements ApplicationListener {
    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
