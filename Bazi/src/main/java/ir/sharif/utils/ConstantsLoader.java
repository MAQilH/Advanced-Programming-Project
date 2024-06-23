package ir.sharif.utils;

import java.util.Properties;

public class ConstantsLoader {
	private static ConstantsLoader instance = null;
	private Properties properties;

	private ConstantsLoader() {
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/constants.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ConstantsLoader getInstance() {
		if (instance == null) {
			instance = new ConstantsLoader();
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public int getMenuWidth() {
		return Integer.parseInt(properties.getProperty("app.width"));
	}

	public int getMenuHeight() {
		return Integer.parseInt(properties.getProperty("app.height"));
	}


}
