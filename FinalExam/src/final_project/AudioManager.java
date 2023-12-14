package final_project;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static Clip clip;

    public static void stopAudio() {
        clip.stop();
    }

    public static void loadAudio(String song) {
        try {
            File audioFile = new File(song);
            final AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.addLineListener(new LineListener() {
                public void update(LineEvent e) {
                    if (e.getType() == LineEvent.Type.STOP) {
                        try {
                            audioStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            clip.open(audioStream);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
