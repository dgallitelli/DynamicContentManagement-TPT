package parsers;

import engine.FunctionCall;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ParseQueryForWS{

	static private String projSeparatorStr = "<-";
	static private String functionCallSeparatorStr = "#";
	private ArrayList<FunctionCall> workflow;

	public ParseQueryForWS(){
		this.workflow = new ArrayList<FunctionCall>();
	}

	public ArrayList<FunctionCall> getWorkflow(String myquery){
		/**
		* Given the input query, obtain the multiple function calls
		*/

		// Obtain the projection for the final result
		String[] firstSplit = myquery.split(projSeparatorStr);
		String proj = firstSplit[0]; // To be handled later

		// Generate the list of functionCalls
		String[] functionCalls = firstSplit[1].split(functionCallSeparatorStr);
		ArrayList<String> foos = new ArrayList<String>();
		for (String foo: functionCalls)
			foos.add(foo);

		// Clean each FunctionCall
		for (String foo:foos)
			this.workflow.add(new FunctionCall(foo));

		// TODO: Handle the projection somehow

		// Before launching, check if it's admissible
		// For a workflow to be admissible, the inputs of the 2+ queries should be outputs of the first one
		for (int i = 1; i < this.workflow.size(); i++){
			HashMap<String,String> prevOuts = new HashMap<String,String>(this.workflow.get(i-1).getFooOutputs());
			if (!prevOuts.containsKey(this.workflow.get(i).getFooInput())) return null;
		}

		// Return the workflow if admissible, otherwise it has already returned null
		return workflow;
	}

}
