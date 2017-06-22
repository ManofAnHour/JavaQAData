package QaTestingObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import QaTestingObjects.ProgramValues.*;

public class QACompleteHelper {
	
	public int StartTesting(RunItems RI, String TestSetName) throws ClientProtocolException, IOException
	{
			int TestRunID = 0;
			
		    BasicUtilHelper BUH = new BasicUtilHelper();

	         NameValuePair[] params = new BasicNameValuePair[] {
	 		        new BasicNameValuePair("TestSetName", TestSetName)
	 		        
	 		};
	 		
	 		HttpPut putRequest = new HttpPut();
	 		try {
	 			putRequest = new HttpPut(BUH.BuildURL("api/TestRun", params));
	 		} catch (ParseException e) {
	 			e.printStackTrace();
	 		}
	 		Gson gson = new Gson();
	 		putRequest.addHeader("RI" , gson.toJson(RI));
	 		
	 		HttpClient httpClient =  HttpClientBuilder.create().build();;
	 		HttpResponse response = httpClient.execute(putRequest);

	 		
	 		BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
	       
	
            TestRunID = gson.fromJson(br.readLine(), int.class);
			
			return TestRunID;
	}

}
