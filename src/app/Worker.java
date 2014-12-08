package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

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

public class Worker {
	
	
	public void run(String[] lst){
		
		String AlchemyAPI_Key = "29eabcd7930f6cb16111e46485e4c407d93b5672";
		String twt=lst[3];
		
		try {
			AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(AlchemyAPI_Key);
			
			Document doc = alchemyObj.TextGetTextSentiment(twt);
			
			System.out.println(getStringFromDocument(doc));

//			AlchemyAPI_TargetedSentimentParams sentimentParams = new AlchemyAPI_TargetedSentimentParams();
//			  
//			sentimentParams.setShowSourceText(true);
//			  
//			doc = alchemyObj.TextGetTargetedSentiment(twt, "car", sentimentParams);
//			  
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


