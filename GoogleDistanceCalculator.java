package distancecalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

/**
 *
 * @author Hiranthi
 */
public class GoogleDistanceCalculator {
    private final String url = "https://maps.googleapis.com/maps/api/directions/json?";
    private final String USER_AGENT = "Mozilla/5.0";
    private final String GOOGLE_KEY = "AIzaSyAOwJJ0fQapyT_PqZlsq5cndMTfBFg-WyY";
    
    public int getDistance(String origin, String destination) throws Exception {
        String decodeOrigin = URLEncoder.encode(origin, "UTF-8");
        String decodedestination = URLEncoder.encode(destination, "UTF-8");
        String sendUrl = url + "origin=" + decodeOrigin + "&destination=" + decodedestination + "&key=" + GOOGLE_KEY ;
        
        String jsonObj = sendGet(sendUrl);
        
        ObjectMapper mapper = new ObjectMapper();
        
        JsonNode node = mapper.readTree(jsonObj);
        
        ArrayNode arrayNode = (ArrayNode) node.get("routes");
        
        JsonNode jsonNode = arrayNode.get(0);
        
        ArrayNode legsNodes = (ArrayNode) jsonNode.get("legs");
        
        JsonNode legsNode = legsNodes.get(0);
        
        JsonNode distanceNode = legsNode.get("distance").get("text");
        
        return Integer.parseInt(distanceNode.getValueAsText());
    } 
    
    public String sendGet(String sendUrl) throws Exception {
        URL googleUrl = new URL(sendUrl);
        HttpURLConnection con = (HttpURLConnection) googleUrl.openConnection();
        
        con.setRequestMethod("GET");
	con.setRequestProperty("User-Agent", USER_AGENT);

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
	}
	in.close();

	//print result
	System.out.println(response.toString());
        
        return response.toString();
    }
}
