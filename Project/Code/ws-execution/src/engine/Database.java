package engine;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Database {
	//"Table" name
	private String name;
	//Each duple of the hashmap is a column of the DB
	private HashMap<String,List<String>> database = new HashMap<>();

	/**
	* Default empty Database constructor
	*/
	public Database() {
		super();
	}

	/**
	* Whenever a new result is generated by the engine, write it in a new row
	* @param results represents the list of outputs of the webservice
	*/
	public void writeRow(HashMap<String,String> results){
		for (String col : results.keySet()) {
			if (this.database.get(col) == null) {
				List<String> values = new ArrayList<>();
				values.add(results.get(col));
				this.database.put(col, values);
			} else {
				this.database.get(col).add(results.get(col));
			}
		}
	}

	/**
	 * function to return the name of the columns
	 * @return a list of strings containing the name of the columns
	 */
	public List<String> getColumns(){
		return new ArrayList<>(this.database.keySet());
	}

	/**
	 * Returns the information specified in the *what* parameter, with the condition from the *where* parameter
	 * @param what list of keys for which we want information from the DB
	 * @return the records meeting the condition
	 */
	public HashMap<String,List<String>> select(List<String> what){
		// Structure: SELECT @param what FROM DB
		// There is no need for WHERE since those in DB are already exact match!
		HashMap<String,List<String>> returnStructure = new HashMap<>();
		for (String found_key : this.database.keySet()){
			if (what.contains(found_key)){
				// Add to the final structure the name of the key (col_name) and the value of the same index
				returnStructure.put(found_key, this.database.get(found_key));
			}
		}
		return returnStructure;
	}
}
