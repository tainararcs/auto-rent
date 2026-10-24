package trcs.autorent.util;

public class Utils {

	public static String getHashPassword(String password) {
		return String.valueOf(password.hashCode());
	}
}
