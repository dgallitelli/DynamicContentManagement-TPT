package engine;

import java.util.HashMap;
import java.util.List;

public class Database {
	private String name;
	private HashMap<String,List<String>> database;

	/**
	* Default empty Database constructor
	*/
	public Database() {
		super();
	}

	/**
	* Default Database constructor
	*/
	public Database(String name, HashMap<String,List<String>> database) {
		super();
		this.name = name;
		this.database = database;
	}

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of database
	* @return
	*/
	public HashMap<String,List<String>> getDatabase() {
		return database;
	}

	/**
	* Sets new value of database
	* @param
	*/
	public void setDatabase(HashMap<String,List<String>> database) {
		this.database = database;
	}
}
