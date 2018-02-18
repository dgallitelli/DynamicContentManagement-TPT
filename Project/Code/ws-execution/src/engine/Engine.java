package engine;

import download.WebService;

import java.util.*;

import parsers.ParseQueryForWS;
import parsers.ParseResultsForWS;
import parsers.WebServiceDescription;

/**
getArtistInfoByName("Frank Sinatra", ?id, ?b, ?e)
# getAlbumByArtistId(?id, ?b, ?aid, ?albumName)
# getSongByAlbumId(?aid, "Frank Sinatra", ?title, ?year)

The engine should be able to
1. parse the expression ! get the list of functions to be called (for simplicity, you can
use # to separate calls. It’s will be easier to parse the expression)
2. execute the calls in the order described by the composition
3. output the results
**/

public class Engine {

	private ParseQueryForWS parser;
	private Database db;

	public Engine(){
		// Engine constructor
		this.parser = new ParseQueryForWS();
		this.db = new Database();
	}

	/**
	 * Function to execute a new webservice
	 * @param query String for complex query of multiple levels
	 * @return true on success, false otherwise
	 */
	public boolean newWebService(String query){

		ArrayList<FunctionCall> workflow;
		WebService ws;
		String fileWithCallResult;
		String fileWithTransfResults;
		ArrayList<String[]> listOfTupleResult;
		FunctionCall projFoo = new FunctionCall();

		// Generate the workflow, if admissible
		workflow = this.parser.getWorkflow(query);
		if (workflow == null) return false;

		// Execute it
		for (FunctionCall fc : workflow){

			if (fc.getFooName().startsWith("Proj")){
				projFoo = fc;
				continue;
			}

			ws=WebServiceDescription.loadDescription(fc.getFooName());
			String myInput;

			if (fc.getFooInput().startsWith("?")){
				System.out.println("[ENGINE-DEBUG] L2 Query!");
				// It is a second-level query! Check for existing values from DB
				System.out.println("[ENGINE] I'm looking for "+fc.getFooInput()+" in the DB.");
				System.out.println("[ENGINE] In the DB I see: "+this.db.getColumns().toString());
				if (this.db.getColumns().contains(fc.getFooInput())) {
					// The input key is already in the table.
					HashMap<String,List<String>> inputs = this.db.select(Collections.singletonList(fc.getFooInput()));
					System.out.println("[ENGINE] Obtained inputs.");
					// TODO: handle multiple inputs -> multiple calls
					// TODO: why L3 queries do not write on DB? -> JOIN
					// Now, I suppose only one input, for test purpose
					myInput = inputs.get(fc.getFooInput()).get(0);
				} else {
					// The query is not admissible. How did it get here?
					System.out.println("[ENGINE] Non admissible query. Returning.");
					return false;
				}
			} else {
				myInput = fc.getFooInput();
			}

			fileWithCallResult = ws.getCallResult(myInput);
			System.out.println("The call is   **"+fileWithCallResult+"**");

			try {
				fileWithTransfResults = ws.getTransformationResult(fileWithCallResult);
				listOfTupleResult = ParseResultsForWS.showResults(fileWithTransfResults, ws);


				System.out.println("The tuple results are ");
				for(String [] tuple: listOfTupleResult){
					System.out.println("Received: "+Arrays.asList(tuple).toString());

					// This hashmap contains a tuple <key(column_name),value>
					HashMap<String,String> result = new HashMap<>();
					if (Arrays.asList(tuple).get(0).equals(fc.getFooInput()) || Arrays.asList(tuple).get(0).equals("NODEF")) {
						result.put(fc.getFooInput(), myInput);
						for (int i = 1; i < tuple.length; i++) {
							result.put(fc.getFooOutputs().get(i - 1), tuple[i]);
						}
						System.out.println("[ENGINE] Writing to DB");
						this.db.writeRow(result);
					}
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}


		// Reach here when there is only the projection left to do
		// No projection specified. Exit.
		if (projFoo == new FunctionCall()) return true;
		final HashMap<String, List<String>> results = this.db.select(projFoo.getFooOutputs());
		System.out.println("[ENGINE] Final Result: ");
		for (String key : results.keySet()){
			System.out.println("["+key+"] "+results.toString());
		}

		// Return the results
		return true;
	}



}
