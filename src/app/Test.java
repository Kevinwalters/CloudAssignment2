package app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		TwitterDAO dao = new TwitterDAO();
		List<Tweet> tweets = new ArrayList<Tweet>();
		tweets = dao.getAllTweets();
		System.out.println(tweets.size());
	}
	
}
