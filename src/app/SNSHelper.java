package app;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ConfirmSubscriptionRequest;
import com.amazonaws.services.sns.model.ConfirmSubscriptionResult;

import app.SNSMessage;

public enum SNSHelper {
	INSTANCE;
	
	private AWSCredentials credentials = new BasicAWSCredentials("AKIAICQ734T7IIRSVW5Q", "c9iA5P0OZzLjst/3V5UrKdN3DsJeVZBSFQjIw1c/");
	private AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);
	
	public void confirmTopicSubmission(SNSMessage message) {
		ConfirmSubscriptionRequest confirmSubscriptionRequest = new ConfirmSubscriptionRequest()
		 							.withTopicArn(message.getTopicArn())
									.withToken(message.getToken());
		ConfirmSubscriptionResult result = amazonSNSClient.confirmSubscription(confirmSubscriptionRequest);
		System.out.println("subscribed to " + result.getSubscriptionArn());
	}
	
}