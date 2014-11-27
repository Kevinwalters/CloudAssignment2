package app;

/*
 * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.Arrays;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web
 * Services developer account, and be signed up to use Amazon SQS. For more
 * information on Amazon SQS, see http://aws.amazon.com/sqs.
 * <p>
 * WANRNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
 * the credentials file in your source directory.
 */
public class receiveSQS {

    public static void main(String[] args) throws Exception {

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * ().
         */
        
    	BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAI2ZCFS3NVEENXW5A", "tI/GgpSWDF/QrNVhtCRu1G+PX/10A2nJQH+yTOiv");
    	try {
  //          credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/daniel/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonSQS sqs = new AmazonSQSClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        sqs.setRegion(usWest2);

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SQS");
        System.out.println("===========================================\n");

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
            
            //*****************************************************
            
            //For some reason, we only get one message at a time and it does not loop over.
            
            //*****************************************************
            
            
            
            //ReceiveMessageResponse receiveMessageResponse = amazonSQSClient.ReceiveMessage(receiveMessageRequest);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
            for (Message message : messages) {
                System.out.println("  Message");
                //System.out.println("    MessageId:     " + message.getMessageId());
                //System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                //System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                //System.out.println("    Body:          " + message.getBody());
                String[] tweetdata = message.getBody().split("\\|\\|");
                System.out.println(Arrays.toString(tweetdata));
                System.out.println("Deleting a message.\n");
                String messageRecieptHandle = messages.get(0).getReceiptHandle();
                sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));

                }
            }
            
            System.out.println();
/*
            // Delete a message

            // Delete a queue
            System.out.println("Deleting the test queue.\n");
            sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));   
  */
        } catch (AmazonServiceException ase) {
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
    }
}
