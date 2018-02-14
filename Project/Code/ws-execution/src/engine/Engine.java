package engine;

import download.WebService;
import engine.FunctionCall;
import java.util.ArrayList;
import java.util.HashMap;
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
	private ArrayList<FunctionCall> workflow;
	private WebService ws;
	private Database db;
	private String fileWithCallResult;
	private String fileWithTransfResults;
	private ArrayList<String[]> listOfTupleResult;

	public Engine(){
		// Engine constructor
		this.parser = new ParseQueryForWS();
		this.db = new Database();
	}

	public boolean newWebService(String query){
		// Function to execture a new webservice

		// Generate the workflow, if admissible
		this.workflow = this.parser.getWorkflow(query);
		if (this.workflow == null) return false;

		// Execute it
		// Handle join of results - query 2 needs results from query 1
		for (FunctionCall fc : this.workflow){
			this.ws=WebServiceDescription.loadDescription(fc.getFooName());
			// TODO: how to call getCallResult with multiple inputs?
			this.fileWithCallResult = this.ws.getCallResult(fc.getFooInput());
			System.out.println("The call is   **"+this.fileWithCallResult+"**");
			try {
				this.fileWithTransfResults = this.ws.getTransformationResult(this.fileWithCallResult);
				this.listOfTupleResult = ParseResultsForWS.showResults(this.fileWithTransfResults, this.ws);

				// TODO: Fill the database with the results of each iteration
				for (String[] tuple: this.listOfTupleResult){

				}

				System.out.println("The tuple results are ");
				for(String [] tuple:this.listOfTupleResult){
					System.out.print("( ");
					for(String t:tuple){
						System.out.print(t+", ");
					}
					System.out.print(") ");
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}


		// Return the results
		return true;
	}



}
