package engine;

import java.util.*;
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
		this.fooOutputs = new ArrayList<String>();
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
	* @param read a string for a function call to be parsed
	*/
	public FunctionCall(String dirtyQuery){
		// Build a FunctionCall structure starting from a string to be cleaned.
		super();
		this.fooInput = "";
		this.fooOutputs = new ArrayList<String>();

		// At this point in time, I suppose that in the string there is a [iooo] string
		// - It specifies the number of inputs (#i) and outputs (#o)
		// - It is defined inside brackets "[]"
		// String example: getArtistInfoByName[iooo]("Frank Sinatra", ?id, ?b, ?e)

		// 1 - extraction function Name
		this.fooName = dirtyQuery.substring(0, dirtyQuery.indexOf("["));

		// 2 - extract the number of input/output parameters
		String inOutParams = dirtyQuery.substring(dirtyQuery.indexOf("[")+1, dirtyQuery.indexOf("]"));
		int nIn, nOut = inOutParams.length() - inOutParams.indexOf("o");
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
	* @return
	*/
	public String getFooName() {
		return fooName;
	}

	/**
	* Sets new value of fooName
	* @param
	*/
	public void setFooName(String fooName) {
		this.fooName = fooName;
	}

	/**
	* Returns value of fooInputs
	* @return
	*/
	public String getFooInput() {
		return fooInput;
	}

	/**
	* Sets new value of fooInputs
	* @param
	*/
	public void setFooInput(String fooInput) {
		this.fooInput = fooInput;
	}

	/**
	* Returns value of fooOutputs
	* @return
	*/
	public ArrayList<String> getFooOutputs() {
		return fooOutputs;
	}

	/**
	* Sets new value of fooOutputs
	* @param
	*/
	public void setFooOutputs(ArrayList<String> fooOutputs) {
		this.fooOutputs = fooOutputs;
	}
}
