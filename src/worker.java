

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;
import com.alchemyapi.api.AlchemyAPI_TargetedSentimentParams;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import org.json.*;


public class worker {
	
	AWSCredentials credentials=null;

	public Document run(String twt, String kwd){
		
		String AlchemyAPI_Key = "29eabcd7930f6cb16111e46485e4c407d93b5672";
		Document doc=null;

		
		try {
			AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(AlchemyAPI_Key);
//			
//			Document doc = alchemyObj.TextGetTextSentiment(twt);
//			
//			System.out.println(getStringFromDocument(doc));

			AlchemyAPI_TargetedSentimentParams sentimentParams = new AlchemyAPI_TargetedSentimentParams();
			  
			sentimentParams.setShowSourceText(true);
			
			doc = alchemyObj.TextGetTargetedSentiment(twt,kwd,sentimentParams);
			  
//			System.out.print(getStringFromDocument(doc));
			
			

			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
	
	
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	
		public Double getSentiment(Document doc) {
		
    	 String c=getStringFromDocument(doc);
    	 Double result=null;
    	 
    	 try {
			JSONObject JO=XML.toJSONObject(c);
			System.out.println(JO);
			result = JO.getJSONObject("results").getJSONObject("docSentiment").getDouble("score");
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    	 
    	 

    }


    
}


