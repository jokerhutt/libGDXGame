package sound;

import Constants.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicHandler {

    Music music;

    public MusicHandler () {
        music = Gdx.audio.newMusic(Gdx.files.internal("33 - Calm Village.ogg"));
    }

    public void playVillageMusic () {

        if (Constants.MUSIC) {
            music.setLooping(true);
            music.setVolume(.1f);
            music.play();
        }

    }

}
