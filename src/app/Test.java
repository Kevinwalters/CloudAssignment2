package app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		TwitterDAO dao = new TwitterDAO();
		List<Object> tweets = new ArrayList<Object>();
		tweets = dao.getAllTweets();
		System.out.println(tweets.size());
	}
	
}
