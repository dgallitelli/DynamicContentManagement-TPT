package engine;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionCall{

	private String fooName;									// String for the function name
	private String fooInput;								// String for the input
	private ArrayList<String> fooOutputs;				// Map for the <outputId, outputValue>

	// Expected string
	static private String textStr = "[a-zA-Z0-9_]+";
	static private String inOutStr = "\\[[io]\\]+";
    static private String parameterStr = "\\?"+textStr;
    static private String constantStr = '"'+".+"+'"';
    static private String argumentStr= "("+constantStr+"|"+parameterStr+")";

	/**
	* Default empty FunctionCall constructor
	*/
	public FunctionCall() {
		super();
		this.fooInput = "";
		this.fooOutputs = new ArrayList<>();
	}

	/**
	* Default FunctionCall constructor
	*/
	public FunctionCall(String fooName, String fooInput, ArrayList<String> fooOutputs) {
		super();
		this.fooName = fooName;
		this.fooInput = fooInput;
		this.fooOutputs = fooOutputs;
	}

	/**
	* FunctionCall Constructor
	* @param dirtyQuery a string for a function call to be parsed
	*/
	public FunctionCall(String dirtyQuery){
		// Build a FunctionCall structure starting from a string to be cleaned.
		super();
		this.fooName="";
		this.fooInput = "";
		this.fooOutputs = new ArrayList<>();

		// Handle the projection differently
		if (dirtyQuery.startsWith("P(?")){
			this.fooName = "Projection";
			Pattern parameterPattern = Pattern.compile(parameterStr);
			Matcher m = parameterPattern.matcher(dirtyQuery);
			while (m.find()) this.fooOutputs.add(dirtyQuery.substring(m.start(),m.end()));
			return;
		}

		// At this point in time, I suppose that in the string there is a [iooo] string
		// - It specifies the number of inputs (#i) and outputs (#o)
		// - It is defined inside brackets "[]"
		// String example: getArtistInfoByName[iooo]("Frank Sinatra", ?id, ?b, ?e)

		// 1 - extraction function Name
		this.fooName = dirtyQuery.substring(0, dirtyQuery.indexOf("["));

		// 2 - extract the number of input/output parameters
		String inOutParams = dirtyQuery.substring(dirtyQuery.indexOf("[")+1, dirtyQuery.indexOf("]"));
		int nOut = inOutParams.length() - inOutParams.indexOf("o");
		Pattern argumentPattern = Pattern.compile(argumentStr);
		Matcher m = argumentPattern.matcher(dirtyQuery);
		int i = 0;
		// extract the input
		m.find();
		String toWrite = dirtyQuery.substring(m.start(), m.end());
		if (toWrite.startsWith("\"")) this.fooInput = toWrite.substring(1,toWrite.length()-1);
		else this.fooInput = toWrite;
		// extract outputs
		while (i < nOut && m.find()){
			toWrite = dirtyQuery.substring(m.start(), m.end());
			this.fooOutputs.add(toWrite);
			i++;
		}
	}

	/**
	* Returns value of fooName
	* @return String containing the name of the functionCall
	*/
	public String getFooName() {
		return fooName;
	}

	/**
	* Returns value of fooInputs
	* @return String containing the expected input of the FunctionCall
	*/
	public String getFooInput() {
		return fooInput;
	}

	/**
	* Returns value of fooOutputs
	* @return an array containing the list of the expected outputs of the FunctionCall
	*/
	public ArrayList<String> getFooOutputs() {
		return fooOutputs;
	}
}
