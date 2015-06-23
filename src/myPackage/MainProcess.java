package myPackage;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainProcess {

	public static void
	main(String[] args) throws MalformedURLException, IOException, APIException  {
		
		APIClient client = new APIClient("");
		client.setUser("");
		client.setPassword("");
		
		Scanner scannerFile = new Scanner (new File("textFile.txt"));
		boolean numFound = false;
		String textToBeProcessed = "";
		
		String firstLine = scannerFile.nextLine();
		
		while (scannerFile.hasNextLine()){
			String currentLine = scannerFile.nextLine();
			String[] arrCurrentLine = currentLine.split("\t");
			
			if (!arrCurrentLine[0].equals("")){
				if (numFound == true){
					//file parser stuff
					System.out.println(textToBeProcessed);
					JSONFileParser fileParse = new JSONFileParser(textToBeProcessed);
					String JSONRequest = "update_case/" + fileParse.getCaseID();
					JSONObject c = (JSONObject) client.sendPost("update_case/" + fileParse.getCaseID(), fileParse.getData());
					textToBeProcessed = "";
					
				}
				textToBeProcessed = textToBeProcessed + currentLine + "\n";
				numFound = true;
			}
			else
				textToBeProcessed = textToBeProcessed + currentLine + "\n";
			
				
		}
		
		JSONFileParser fileParse = new JSONFileParser(textToBeProcessed);
		String JSONRequest = "update_case/" + fileParse.getCaseID();
		System.out.println(JSONRequest);
		System.out.println(fileParse.getData());
		
		
		JSONObject c = (JSONObject) client.sendPost("update_case/" + fileParse.getCaseID(), fileParse.getData());
		
		
		System.out.println("PROCESS HAS COMPLETED");
	
	}

}
