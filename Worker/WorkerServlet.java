
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.util.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 * An example Amazon Elastic Beanstalk Worker Tier application. This example
 * requires a Java 7 (or higher) compiler.
 */
public class WorkerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    
    /**
     * A client to use to access Amazon S3. Pulls credentials from the
     * {@code AwsCredentials.properties} file if found on the classpath,
     * otherwise will attempt to obtain credentials based on the IAM
     * Instance Profile associated with the EC2 instance on which it is
     * run.
     */
    private final AmazonS3Client s3 = new AmazonS3Client(
        new AWSCredentialsProviderChain(
            new InstanceProfileCredentialsProvider(),
            new ClasspathPropertiesFileCredentialsProvider()));
    
    /**
     * This method is invoked to handle POST requests from the local
     * SQS daemon when a work item is pulled off of the queue. The
     * body of the request contains the message pulled off the queue.
     */
    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        try {
        	AmazonSNSClient sns= new AmazonSNSClient(new BasicAWSCredentials("key","pass"));
            // Parse the work to be done from the POST request body.
            
            WorkRequest workRequest = WorkRequest.fromJson(request.getInputStream());
            workRequest.getMessage();
            
            worker myworker = new worker();
           // workRequest.get
           
            //s is a json string got from sqs, parse it into lat, lon, timestamp, tid, keyword, text
            //use text to do sentiment
            //use lat, lon, timestamp, tid, keyword pass to SNS.
            String s = workRequest.getMessage();
            
            JSONObject jsonObj = new JSONObject(s);
 
            
            /*
             * 1. json from sqs (lat, lon, timestamp, tid, keyword)
             * 2. sentiment
             * 3. SNS: 
             * 		   subscribe (http://docs.aws.amazon.com/sns/latest/APIReference/API_Subscribe.html),
             *  	   publish (http://docs.aws.amazon.com/sns/latest/APIReference/API_Publish.html)
             */
            
            
            
            
			// lst, message from sqs
            //doc???????
            Document doc = myworker.run(jsonObj.get("text").toString(), jsonObj.getString("kwd").toString());
            String re = doc.toString();
        
            // Simulate doing some work.
            
            Thread.sleep(10 * 1000);
            
            // Write the "result" of the work into Amazon S3.
            
/*            byte[] message = workRequest.getMessage().getBytes(UTF_8);
            
            s3.putObject(workRequest.getBucket(),
                         workRequest.getKey(),
                         new ByteArrayInputStream(message),
                         new ObjectMetadata());
*/
           
            PublishRequest publishRequest;
    		
    		String topicArn = "";//set sns topic url here
			publishRequest = new PublishRequest(topicArn , (String) jsonObj.get("text"));
    		
    		if (re.equals(""))
    			re = "error";
    			//res = money.format(new Random().nextDouble() * 2 - 1) + "";
    		publishRequest.addMessageAttributesEntry("senti",
    				new MessageAttributeValue().withDataType("String")
    						.withStringValue(re));

    		/*
    		 * publishRequest.addMessageAttributesEntry( "text", new
    		 * MessageAttributeValue().withDataType("String")
    		 * .withStringValue( (String)jo.get("text")));
    		 */
    		publishRequest.addMessageAttributesEntry("lon",
    				new MessageAttributeValue().withDataType("String")
    						.withStringValue((String) jsonObj.get("lon")));
    		publishRequest.addMessageAttributesEntry("lat",
    				new MessageAttributeValue().withDataType("String")
    						.withStringValue((String) jsonObj.get("lat")));
    		publishRequest.addMessageAttributesEntry("time",
    				new MessageAttributeValue().withDataType("String")
    						.withStringValue((String) jsonObj.get("time")));
    		publishRequest.addMessageAttributesEntry("id",
    				new MessageAttributeValue().withDataType("String")
    						.withStringValue((String) jsonObj.get("id")));

    		sns.publish(publishRequest);
    		
    		 // Signal to beanstalk that processing was successful so this work
            // item should not be retried.
            response.setStatus(200);

        } catch (RuntimeException | InterruptedException exception) {
            
            // Signal to beanstalk that something went wrong while processing
            // the request. The work request will be retried several times in
            // case the failure was transient (eg a temporary network issue
            // when writing to Amazon S3).
            
            response.setStatus(200);
            try (PrintWriter writer =
                 new PrintWriter(response.getOutputStream())) {
                exception.printStackTrace(writer);
            }
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
