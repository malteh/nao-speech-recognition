package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	public static int extractStatus(String in) {
		int ret = 0;

		try {
			ret = Integer.parseInt(extract(in, ".*status\":(\\d+),\".*"));
		} catch (NumberFormatException e) {
		}

		return ret;
	}

	public static Double extractConfidence(String in) {
		Double ret = 0.0;

		try {
			ret = Double
					.parseDouble(extract(in, ".*confidence\":(\\d.\\d+).*"));
		} catch (NumberFormatException e) {
		}

		return ret;
	}

	public static String extractUtterance(String in) {
		return extract(in, ".*utterance\":\"([a-zA-Z ]+)\",\"confidence.*");
	}

	public static String extract(String in, String regex) {
		String ret = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(in);

		if (m.find()) {
			ret = m.group(1);
		}

		return ret;
	}
}
