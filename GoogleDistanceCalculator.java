package distancecalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    
    public Map<String, Float> getSourtedListAsDistance(String origin, Map <String, String> uniList) throws Exception{   
        
        Map<String, Float> unshortedMap = new HashMap<>();
        
        for(Map.Entry<String, String> uni : uniList.entrySet()) {
            float distance = getDistance(origin, uni.getValue());
            unshortedMap.put(uni.getKey(), distance);
        }
        
        Map<String, Float> sortedMap = sortByValue(unshortedMap);
        
        return sortedMap;
    }
    
    public float getDistance(String origin, String destination) throws Exception {
        String decodeOrigin = URLEncoder.encode(origin, "UTF-8");
        String decodedestination = URLEncoder.encode(destination, "UTF-8");
        String sendUrl = url + "origin=" + decodeOrigin + "&destination=" + decodedestination + "&key=" + GOOGLE_KEY ;
        float distance = 0;
        String jsonObj = sendGet(sendUrl);
        
        ObjectMapper mapper = new ObjectMapper();
        
        
        JsonNode node = mapper.readTree(jsonObj);
        ArrayNode arrayNode = null;
        if( node.get("routes")!= null){
             arrayNode = (ArrayNode) node.get("routes");
             JsonNode jsonNode = null;
             if(arrayNode.get(0) != null){
                jsonNode = arrayNode.get(0);
                ArrayNode legsNodes = null;
                if(jsonNode.get("legs") != null){
                        legsNodes = (ArrayNode) jsonNode.get("legs");
                    JsonNode legsNode = null;
                    if(legsNodes.get(0) != null){
                        legsNode = legsNodes.get(0);
                        JsonNode distanceNode = null;
                        if(legsNode.get("distance").get("text") != null){
                            distanceNode = legsNode.get("distance").get("text");
                            if(distanceNode.getValueAsText() != null){
                                String value = distanceNode.getValueAsText();
                                distance = (int) Float.parseFloat(value.replace("km", ""));
                            }
                        }
                    }
                }
            }
        }
        return distance;
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
    
    private Map<String, Float> sortByValue(Map<String, Float> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Float>> list =
                new LinkedList<Map.Entry<String, Float>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Float> sortedMap = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }
}
