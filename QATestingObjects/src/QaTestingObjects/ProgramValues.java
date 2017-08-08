package QaTestingObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//import org.json.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;



public class ProgramValues {
	
	public class RunItems
	{
        public String Client_Name = "";
        public String Org_Name = "";
        public String Env_Short = "";
        public String UserName = "";
        public String OrgsEnvShortName = "";
        public String OrgsEnv_LongName = "";
        public String Release = "";
        public String Build = "";
        public String Application = "";
        public String LWVersion = "";
        public String LWBuild = "";
        public UUID LWVersion_IDNUM = new UUID(0L, 0L);
        public UUID LWBuild_IDNUM =  new UUID(0L, 0L);
        
	}
    public class urlRoot
    {
		public url urls;
    }
    public class url
    {
        public int Status;

        public String application = "";
        public UUID application_idnumber = new UUID(0L, 0L);
        public String ap_srtname = "";
        public UUID client_idnumber = new UUID(0L, 0L);
        public String description = "";
        public UUID env_idnumber = new UUID(0L, 0L);
        public String fullurl = "";
        public String name = "";
        public UUID program_idnumber = new UUID(0L, 0L);
        public UUID row_idnumber = new UUID(0L, 0L);
    }
	
    public class runnerdetailsRoot
    {
		public ScriptRunner runnerdetails;
    }
    
    public class ScriptRunner
    {
        public String TestSetTitle = "";
        public String UserName = "" ;
    }
    
	
	public class URLForUse{
		public List<org.apache.http.NameValuePair> listofparam;
	}
	public RunItems GetTestingPreferences(String username, String Application) throws IllegalStateException, IOException
	{
		ProgramValues PV = new ProgramValues();
		RunItems RI = PV.new RunItems(); 
		
		BasicUtilHelper BUH = new BasicUtilHelper();
		
		NameValuePair[] params = new BasicNameValuePair[] {
		        new BasicNameValuePair("loggedInas", username),
		        new BasicNameValuePair("application", Application),
		};
		
		HttpGet getRequest = new HttpGet();
		try {
			getRequest = new HttpGet(BUH.BuildURL("/api/UserTestingPrefForRunItem", params));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		HttpClient httpClient =  HttpClientBuilder.create().build();;
		HttpResponse response = httpClient.execute(getRequest);


		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));
		
		
		Gson gson = new Gson();

		RI = gson.fromJson(br.readLine(), RunItems.class);
		RI.Application = Application;

		return RI;
	}
	public String getURL(RunItems RI) throws IllegalStateException, ClientProtocolException, IOException
	{
		String URL = "";
		String eenv = "";
		
		BasicUtilHelper BUH = new BasicUtilHelper();

		if(RI.OrgsEnvShortName == null || RI.OrgsEnvShortName == "")
		{
			eenv = RI.Env_Short;
		}
		else
		{
            if (RI.OrgsEnvShortName == RI.Env_Short) eenv = RI.Env_Short;
            else if (RI.OrgsEnvShortName != RI.Env_Short) eenv = RI.OrgsEnvShortName;
		}
         
         ProgramValues PV = new ProgramValues();
         url uRL = PV.new url();

         NameValuePair[] params = new BasicNameValuePair[] {
 		        new BasicNameValuePair("client", RI.Org_Name),
 		        new BasicNameValuePair("program", "All"),
 		        new BasicNameValuePair("environment", RI.OrgsEnvShortName),
 		        new BasicNameValuePair("application", RI.Application),
 		};
 
 		HttpGet getRequest = new HttpGet();
 		try {
 			getRequest = new HttpGet(BUH.BuildURL("api/URL", params));
 		} catch (ParseException e) {
 			e.printStackTrace();
 		}
 	
 		
 		HttpClient httpClient =  HttpClientBuilder.create().build();;
 		HttpResponse response = httpClient.execute(getRequest);

 		
 		BufferedReader br = new BufferedReader(
                          new InputStreamReader((response.getEntity().getContent())));
		//System.out.println("Output from Server ...."+br.readLine()+" \n");
 		Gson gson = new Gson();

 		urlRoot urlroot = PV.new urlRoot();
 		
 		urlroot = gson.fromJson(br.readLine(), urlRoot.class);
 		
 		uRL = urlroot.urls;
         
        URL = uRL.fullurl;
         
		return URL;
	}
	public static void TryRest() throws IllegalStateException, IOException
	{
		HttpClient httpClient =  HttpClientBuilder.create().build();;
		URI uri = null;
		
			try {
				uri = new URIBuilder()
				        .setScheme("http")
				        .setHost(conFIG.URL)
				        .setPath("/api/QAComplete/LastUpdateDate")
				        .setParameter("Client_IDNUM", "4a8f16cd-3874-4237-a601-50468615949a")
				        .setParameter("Type", "Link")
				        .build();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		HttpGet getRequest = new HttpGet(uri);

		HttpResponse response = httpClient.execute(getRequest);



		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));

		String output;
		//System.out.println("Output from Server .... \n");
		System.out.println("Output from Server ...."+br.readLine()+" \n");
		//while ((output = br.readLine()) != null) {System.out.println(output);		}

		httpClient.getConnectionManager().shutdown();
		
	}
	public ScriptRunner GetScriptRunnerInfo(String ClientName, String ScriptName) throws ClientProtocolException, IOException
	{
		  	 ProgramValues PV = new ProgramValues();
	         ScriptRunner sr = PV.new ScriptRunner();
		     BasicUtilHelper BUH = new BasicUtilHelper();

	         NameValuePair[] params = new BasicNameValuePair[] {
	 		        new BasicNameValuePair("Client", ClientName),
	 		        new BasicNameValuePair("ScriptName", ScriptName)
	 		        
	 		};
	 		
	 		HttpGet getRequest = new HttpGet();
	 		try {
	 			getRequest = new HttpGet(BUH.BuildURL("api/ScriptRunner", params));
	 		} catch (ParseException e) {
	 			e.printStackTrace();
	 		}
	 	
	 		
	 		HttpClient httpClient =  HttpClientBuilder.create().build();;
	 		HttpResponse response = httpClient.execute(getRequest);

	 		
	 		BufferedReader br = new BufferedReader(
	                          new InputStreamReader((response.getEntity().getContent())));
			//System.out.println("Output from Server ...."+br.readLine()+" \n");
	 		Gson gson = new Gson();

	 		runnerdetailsRoot runnerdetroot = PV.new runnerdetailsRoot();
	 		
	 		runnerdetroot = gson.fromJson(br.readLine(), runnerdetailsRoot.class);
	 		
	 		sr = runnerdetroot.runnerdetails;
	         
	         return sr;
	}

	
	
	public static void main(String[] args) throws IllegalStateException, IOException {
		
		ProgramValues PV = new ProgramValues();
		RunItems RI = PV.new RunItems(); 
		ScriptRunner SR = PV.new ScriptRunner();
		  
		SR = PV.GetScriptRunnerInfo("LW","DoNotUse");
		RI = PV.GetTestingPreferences(SR.UserName, "API");
		
		String URL = PV.getURL(RI);
		System.out.println("Output from Server ...."+URL+" ggg\n");
		
		QACompleteHelper QACH = new QACompleteHelper();
		int RunID = QACH.StartTesting(RI, SR.TestSetTitle);
		System.out.println("Output from Server ...."+RunID+" ggg\n");
		//boolean FirstMethod = QACH.CheckSoftFail(RunID, "A00001_ValidLogin", "Passed");
		//System.out.println("This is the softfail as Passed Status ...."+FirstMethod+" \n");
		
		//boolean HasPassed = QACH.CheckSoftFail(RunID, "A00002_NonExistingLogin", "Passed");
		//System.out.println("This is the softfail as Passed Status ...."+HasPassed+" \n");
	}
}
