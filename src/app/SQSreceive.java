package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class SQSreceive {

	AWSCredentials credentials = null;
	AmazonSQS sqs=null;
	
public String[] getmessage(){
	
	String[] lst=null;

		credentials = new ProfileCredentialsProvider("default").getCredentials();
		sqs = new AmazonSQSClient(credentials);
	    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	    sqs.setRegion(usWest2);
	
		   try {
    
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
		
		   

     }


     	

    
	

