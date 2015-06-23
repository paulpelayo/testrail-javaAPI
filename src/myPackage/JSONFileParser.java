/*
 * 	TestRail API for Java
 * 	
 * 	JSONFileParse Class
 * 
 * 	Author: 			Paul Pelayo
 * 	Last Updated:		6/23/2015
 * 	
 * 	About: The JSONFileParser class takes a single line from a .tsv file and converts
 *	each line into a JSON object.  The object can be accessed by the main process
 *	in order to create a JSON request.  The FileParser only works with the 
 * 
 */
package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONFileParser {
	
	private ArrayList<String> lines;
	private String caseID;
	private ArrayList<String> estimates;
	private JSONObject data;
	private JSONArray steps;

	private static final String PRIORITY_ID = "priority_id";
	private static final String ESTIMATE = "estimate";
	private static final String STEPS = "custom_steps_separated";

	/*
	 *	@constructor			builds the JSONFileParser
	 *	@param					string from a .tsv file containing TestRail fields
	 */
	public JSONFileParser(String testCase) throws FileNotFoundException
	{
		lines = new ArrayList<String>();
		data = new JSONObject();
		steps = new JSONArray();
		caseID = "";
	
		Scanner scanTestCase = new Scanner(testCase);
		convertJSON(scanTestCase);
		
	}
	

	/*
	 * 	
	 * 	@param scanTestCase		scanner version of the test case string
	 */
	private void convertJSON(Scanner scanTestCase){		
		while (scanTestCase.hasNextLine()){
			String line = scanTestCase.nextLine();
			String[] fieldData = line.split("\t");
			String firstEntry = fieldData[0];
			if (!firstEntry.equals("")){
				caseID = fieldData[0];
				data.put(PRIORITY_ID, fieldData[1]);
				data.put(ESTIMATE, fieldData[2]);	
			}
			buildJSONArray(fieldData[3], fieldData[4]);
			
			
		}
		
		System.out.println("converted to JSON\n");
	}
	
	/*
	 * 	buildJSONArray()		builds a JSONArray for the steps of a test case
	 * 	
	 * 	@param	step			test step of a test case
	 * 	@param	results			expected results of test step
	 * 
	 */
	private void buildJSONArray(String step, String result){
		JSONObject tempObj = new JSONObject();
		tempObj.put("content", step);
		tempObj.put("expected", result);
		steps.add(tempObj);
	}
	
	/*
	 * 	getData()				returns the JSON Data
	 */
	public JSONObject getData(){
		data.put(STEPS, steps);
		return data;
	}
	
	/*
	 * 	getData()				returns the caseID
	 */
	public String getCaseID(){
		return caseID;
	}

	/*
	 * 	arrayPrettyPrint		helper method for testing
	 */
	private void arrayPrettyPrint(String[] array){
		for (int x = 0; x < array.length; x++)
			System.out.print(array[x] + "\t");
		System.out.print("\tThe size of the array is: " + array.length);
		System.out.println();
	}
	
}


	
	


