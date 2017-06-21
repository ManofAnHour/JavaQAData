package QaTestingObjects;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

public class BasicUtilHelper {
	public URI BuildURL(String Path, NameValuePair[] params) throws ParseException
	{		
	URI uri = null;
	
	List<org.apache.http.NameValuePair> ListOFParam = new ArrayList<org.apache.http.NameValuePair>();
	
	if(params.length >= 1)
	{
		 for(org.apache.http.NameValuePair I : params)
		 {
			 ListOFParam.add(new BasicNameValuePair(I.getName(), I.getValue()));
		 }
	}
	
	try {
		uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(conFIG.URL)
		        .setPath(Path)
		        
		        .setParameters(ListOFParam)
		        .build();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return uri;

	}
	
}
