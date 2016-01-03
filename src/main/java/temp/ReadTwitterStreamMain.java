package temp;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import util.ReadProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sachin on 11/14/2015.
 */
public class ReadTwitterStreamMain {

    private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";

    // mvn exec:java -Dexec.mainClass="temp.ReadTwitterStreamMain" -Dexec.args="arg0 arg1 arg2"
    public static void main(String[] args) {
        try{
            System.out.println("Starting Twitter public stream consumer thread.");

            // Enter your consumer key and secret below
            OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(ReadProperties.getProp("twitter.consumer.key"))
                    .apiSecret(ReadProperties.getProp("twitter.consumer.secret"))
                    .build();

            // Set your access token
            Token accessToken = new Token(ReadProperties.getProp("twitter.access.token"), ReadProperties.getProp("twitter.access.secret"));

            // Let's generate the request
            System.out.println("Connecting to Twitter Public Stream");
            OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
            request.addHeader("version", "HTTP/1.1");
            request.addHeader("host", "stream.twitter.com");
//            request.setConnectionKeepAlive(true);
            request.addHeader("user-agent", "Twitter Stream Reader");
            request.addBodyParameter("track", "paris"); // Set keywords you'd like to track here
            service.signRequest(accessToken, request);
            Response response = request.send();

            // Create a reader to read Twitter's stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));

            String line;
            System.out.println("Reading the stream......");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}
