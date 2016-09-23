package net.keabotstudios.dr2.game.net;

import java.util.regex.Pattern;

public class NetUtil {

	private static final String IP_PORT_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5]):" + "([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
	private static final Pattern PATTERN;
	private static final int DEFAULT_PORT = 25510;

	static {
		PATTERN = Pattern.compile(IP_PORT_PATTERN);
	}

	public static boolean isIpStringValid(String ip) {
		return PATTERN.matcher(ip).matches();
	}

	public static int getPortFromIpString(String ip) {
		if (NetUtil.isIpStringValid(ip) && ip.indexOf(':') > -1) {
			String[] arr = ip.split(":");
			try {
				return Integer.parseInt(arr[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return DEFAULT_PORT;
	}
	
	public static String getIpFromIpString(String ip) {
		if (NetUtil.isIpStringValid(ip)) {
			String[] arr = ip.split(":");
			return arr[0];
		}
		return null;
	}

}
