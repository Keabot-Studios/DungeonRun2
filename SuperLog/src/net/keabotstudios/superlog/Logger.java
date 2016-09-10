package net.keabotstudios.superlog;

public class Logger {

	public enum LogLevel {
		INFO(0), DEBUG(1), ERROR(2), FATAL(3), NONE(Integer.MAX_VALUE);

		int level;

		private LogLevel(int l) {
			level = l;
		}

		public int getLevel() {
			return level;
		}
	}

	private LogLevel logLevel = LogLevel.NONE;

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	private void log(LogLevel l, String s) {
		if (l.level >= logLevel.level)
			System.out.println("[" + l.name() + "]" + s);
	}

	public void info(String s) {
		log(LogLevel.INFO, s);
	}

	public void debug(String s) {
		log(LogLevel.DEBUG, s);
	}

	public void error(String s) {
		log(LogLevel.ERROR, s);
	}

	public void fatal(String s) {
		log(LogLevel.FATAL, s);
	}

}