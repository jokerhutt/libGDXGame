package jokerhut.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override

    public void create() {
        Gdx.graphics.setWindowedMode(1152, 960);
        setScreen(new MainScreen());
    }
}
