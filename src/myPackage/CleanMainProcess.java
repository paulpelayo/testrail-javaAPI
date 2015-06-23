/*
 * 	TestRail API for Java
 * 	
 * 	CleanMainProcess Class
 * 
 * 	Author: 			Paul Pelayo
 * 	Last Updated:		6/23/2015
 * 	
 * 	About: The CleanMainProcess is a version of the MainProcess that allows for
 * 	keyboard input.  The main function reads in a .txt file (w/ tab separated values)
 * 	and breaks apart the file by each line.  Each line is sent as an argument for
 *  the JSONFileParser.  There each line is converted into JSON Objects.  The objects are then
 *  stored and returned to the main process where the main process uses the objects
 *  to make a call to TestRail's API.
 *  
 *  PLEASE READ THE README FILE FOR INFORMATION ON FORMATTING YOUR .TXT FILE
 * 
 */
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

public class CleanMainProcess {

	public static void main(String[] args) throws MalformedURLException, IOException, APIException  {
		
		Scanner myIn = new Scanner (System.in);
		System.out.println("Enter TestRail Website in the form \"https://<accountName>.testrail.com/\"");
		String url = myIn.next();
		System.out.println("Enter TestRail Username");
		String user = myIn.next();
		System.out.println("Enter TestRail Password");
		String password = myIn.next();
		System.out.println("*Note that username and password verification will not complete until a GET or POST request is sent to TestRail");
		System.out.println("\nEnter the name of the file to be entered including the .txt (all entries snould be seperated by tabs)");
		String txtName = myIn.next();
		System.out.println("\nEnter the API Method (curently supports update_case, don't forget the /):");
		String method = myIn.next();
		
		
			
		
		APIClient client = new APIClient(url);
		client.setUser(user);
		client.setPassword(password);
		
		Scanner scannerFile = new Scanner (new File(txtName));
		boolean numFound = false;
		String textToBeProcessed = "";
		
		//Makes sure columnHeadings are not processed
		String columnHeadings = scannerFile.nextLine();
		
		System.out.println("COUNT\tcID\tpID\tEST\tSTEPS");
		int count = 0;
		
		
		//Breaks apart file by line
		while (scannerFile.hasNextLine()){
			String currentLine = scannerFile.nextLine();
			
			//Splits line to check the beginning of eahttch line
			String[] arrCurrentLine = currentLine.split("\t");
			
			if (!arrCurrentLine[0].equals("")){
				if (numFound == true){
					System.out.println(++count + "\t" + textToBeProcessed);
					JSONFileParser fileParse = new JSONFileParser(textToBeProcessed);
					String JSONRequest = method + fileParse.getCaseID();
					
					//Call to TestRail
					JSONObject c = (JSONObject) client.sendPost(JSONRequest, fileParse.getData());
					textToBeProcessed = "";		
				}
				textToBeProcessed = textToBeProcessed + currentLine + "\n";
				numFound = true;
			}
			else
				textToBeProcessed = textToBeProcessed + currentLine + "\n";	
		}
		System.out.println(++count + "\t" + textToBeProcessed);
		JSONFileParser fileParse = new JSONFileParser(textToBeProcessed);
		String JSONRequest = method + fileParse.getCaseID();
		JSONObject c = (JSONObject) client.sendPost(JSONRequest, fileParse.getData());
		
		System.out.println("PROCESS HAS COMPLETED");

	}

}
