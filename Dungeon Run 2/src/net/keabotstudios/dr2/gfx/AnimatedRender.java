package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.game.GameInfo;

public class AnimatedRender extends Render {

	private Render[] frames;
	private int currentFrame;
	private long delay;
	
	private boolean hasPlayedOnce = false, playOnce = false;
	
	public AnimatedRender(Render[] frames, long delay) {
		super(frames[0].width, frames[0].height);
		this.frames = frames;
		this.delay = delay;
		this.currentFrame = 0;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public void setFrames(Render[] frames) {
		this.frames = frames;
	}
	
	public void setPlayOnce(boolean playOnce) {
		this.playOnce = playOnce;
	}
	
	public boolean hasPlayedOnce() {
		return hasPlayedOnce;
	}
	
	public void update() {
		if(delay < 0) return;
		if(GameInfo.TIME % delay == 0) {
			currentFrame++;
			if(currentFrame >= frames.length) {
				hasPlayedOnce = true;
				if(playOnce)
					return;
				else
					currentFrame = 0;
			}
			clear(Color.BLACK);
			render(frames[currentFrame], 0, 0);
		}
	}

}
