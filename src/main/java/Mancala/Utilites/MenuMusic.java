package Mancala.Utilites;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MenuMusic {

	private File music;
	private Clip clip;
	private AudioInputStream stream;
	
	public MenuMusic(){
		try {
			music = new File("./src/main/resources/lobby_music.wav");
			clip = AudioSystem.getClip();
			stream = AudioSystem.getAudioInputStream(music);
			clip.open(stream);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void startMusic() {
		try {
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void endMusic() {
		try {
			clip.stop();		
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public boolean isPlaying() {
		if(clip.isRunning()) {
			return true;
		}else {
			return false;
		}
	}

}
