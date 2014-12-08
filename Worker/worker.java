

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
	 
    
	public String[] getmessage(WorkRequest workRequest){
		try {
			  credentials = new PropertiesCredentials(worker.class.getResourceAsStream("AwsCredentials.properties"));
			  //credentials = new BasicAWSCredentials("AKIAIZNDFYQ2YSWZNQUQ", "gQ9z9kBpS4L6PTv8z5oZnMKgZud0HnfCD/X+BGXU");
		  } catch (Exception e) {
		      throw new AmazonClientException(
		              "Cannot load the credentials from the credential profiles file. " +
		              "Please make sure that your credentials file is at the correct " +
		              "location (/Users/daniel/.aws/credentials), and is in valid format.",
		              e);
		  }


			AmazonSQS sqs = new AmazonSQSClient(credentials);
		    Region usWest2 = Region.getRegion(Regions.US_EAST_1);
		    sqs.setRegion(usWest2);
		    String[] lst=null;

/*			//credentials = new PropertiesCredentials(SQSreceive.class.getResourceAsStream("AwsCredentials.properties"));
			credentials = new BasicAWSCredentials("AKIAIZNDFYQ2YSWZNQUQ", "gQ9z9kBpS4L6PTv8z5oZnMKgZud0HnfCD/X+BGXU");
			sqs = new AmazonSQSClient(credentials);
		    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		    sqs.setRegion(usWest2);
		
		*/	   try {
	    
	   // List queues
	              System.out.println("Listing all queues in your account.\n");

	              for (String queueUrl : sqs.listQueues().getQueueUrls()) {
	              
	              System.out.println("  QueueUrl: " + queueUrl);
	              
	              System.out.println();
	              
	              // Receive messages
	              System.out.println("Receiving messages from MyQueue.\n");
	              ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
	              System.out.println("Message size:" );   
			          List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
			          
				    for (Message message : messages) {
		            System.out.println("  Message");
		            
		            
		            lst = message.getBody().split("\\|\\|");
		            
		            System.out.println(Arrays.toString(message.getBody().split("\\|\\|")));
		            System.out.println();} 
	              
	        }
			   }
	        catch (AmazonServiceException ase) {
	        System.out.println("Caught an AmazonServiceException, which means your request made it " +
	                "to Amazon SQS, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	        System.out.println("Caught an AmazonClientException, which means the client encountered " +
	                "a serious internal problem while trying to communicate with SQS, such as not " +
	                "being able to access the network.");
	        System.out.println("Error Message: " + ace.getMessage());
	    }
			return lst;
	              
	   }
	
	
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
			  
			 doc = alchemyObj.TextGetTargetedSentiment(twt,kwd, sentimentParams);
			  
			System.out.print(getStringFromDocument(doc));
			
			

			
			
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
	
//
//    
//    
//    
//    
//    public static void main(String[] args) {
//    	
//    	 String[] str={"hahahahha ldf", "asdfad","askfka sdf","go to hell"};
//    	 Worker wk = new Worker();
//    	 wk.run(str);
//    	 
//		// TODO Auto-generated method stub
//
//	}

    
}


