import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import parsers.ParseResultsForWS;
import parsers.WebServiceDescription;
import download.WebService;


public class Main {

	public static final void main(String[] args) throws Exception{

		List<String> params = Arrays.asList("http://musicbrainz.org/ws/1/artist/?name=", null);


	    //Testing without loading the description of the WS
	    /** WebService ws=new WebService("mb_getArtistInfoByName",params);
	    String fileWithCallResult = ws.getCallResult("Frank Sinatra");
		System.out.println("The call is   **"+fileWithCallResult+"**");
		ws.getTransformationResult(fileWithCallResult);**/


		//Testing with loading the description WS
		WebService ws=WebServiceDescription.loadDescription("mb_getArtistInfoByName");
		WebService ws2=WebServiceDescription.loadDescription("mb_getAlbumsArtistId");
	    WebService ws3=WebServiceDescription.loadDescription("mb_getSongByAlbumId");

		String fileWithCallResult = ws.getCallResult("Leonard Cohen");
		String fileWithCallResult2 = ws2.getCallResult("65314b12-0e08-43fa-ba33-baaa7b874c15");
		String fileWithCallResult3 = ws3.getCallResult("376a0fc5-d3cf-47bb-96cb-92077eb00cfa");

		System.out.println("The call is   **"+fileWithCallResult+"**");
		String fileWithTransfResults=ws.getTransformationResult(fileWithCallResult);
		ArrayList<String[]>  listOfTupleResult= ParseResultsForWS.showResults(fileWithTransfResults, ws);


		System.out.println("The tuple results are ");
		for(String [] tuple:listOfTupleResult){
			System.out.print("( ");
		 	for(String t:tuple){
		 		System.out.print(t+", ");
		 	}
		 	System.out.print(") ");
		 	System.out.println();
	 	}


		System.out.println("The call is   **"+fileWithCallResult2+"**");
		String fileWithTransfResults2=ws2.getTransformationResult(fileWithCallResult2);
		ArrayList<String[]>  listOfTupleResult2= ParseResultsForWS.showResults(fileWithTransfResults2, ws2);


		System.out.println("The tuple results are ");
		for(String [] tuple:listOfTupleResult2){
			System.out.print("( ");
		 	for(String t:tuple){
		 		System.out.print(t+", ");
		 	}
		 	System.out.print(") ");
		 	System.out.println();
	 	}


		System.out.println("The call is   **"+fileWithCallResult3+"**");
		String fileWithTransfResults3=ws3.getTransformationResult(fileWithCallResult3);
		ArrayList<String[]>  listOfTupleResult3= ParseResultsForWS.showResults(fileWithTransfResults3, ws3);


		System.out.println("The tuple results are ");
		for(String [] tuple:listOfTupleResult3){
			System.out.print("( ");
		 	for(String t:tuple){
		 		System.out.print(t+", ");
		 	}
		 	System.out.print(") ");
		 	System.out.println();
	 	}

	}

}
