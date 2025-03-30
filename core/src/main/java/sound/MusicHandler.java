package sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import constants.Constants;

public class MusicHandler {

    Music music;

    public MusicHandler () {
        music = Gdx.audio.newMusic(Gdx.files.internal("Sprout Valley — Rainy Day No Vocals.wav"));
    }

    public void playRainyDayMusic () {

    if (Constants.MUSIC) {
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    }

    public void setVolume (float volume) {
        music.setVolume(volume);
    }

    public void stopMusic () {
        music.stop();
    }

}
