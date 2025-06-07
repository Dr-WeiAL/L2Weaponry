package dev.xkmc.l2weaponry.compat.cataclysm;

class ToolTicker {

	private int time;
	private final Runnable task;

	ToolTicker(int time, Runnable task) {
		this.time = time;
		this.task = task;
	}

	public boolean tick() {
		time--;
		if (time == 0) task.run();
		return time <= 0;
	}

}
