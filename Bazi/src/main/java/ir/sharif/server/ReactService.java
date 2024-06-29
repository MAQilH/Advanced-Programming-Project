package ir.sharif.server;

import ir.sharif.model.React;

import java.util.ArrayList;
import java.util.List;

public class ReactService {
	private static ReactService instance = null;
	private ArrayList<React> allReacts = new ArrayList<>();

	private ReactService() {}

	public static ReactService getInstance() {
		if (instance == null) instance = new ReactService();
		return instance;
	}

	public void addReact(React react) {
		allReacts.add(react);
	}

	public ArrayList<React> getAllReacts() {
		return allReacts;
	}

	public ArrayList<React> getAllReacts(int bufferSize) {
		List<React> list = allReacts.subList(bufferSize, allReacts.size());
		return new ArrayList<>(list);
	}
}
