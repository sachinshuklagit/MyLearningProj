package temp;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sachin on 11/20/2015.
 */
public class TwitterStreamWithFlume  extends AbstractSource
        implements EventDrivenSource, Configurable {

    private static final Logger logger =
            LoggerFactory.getLogger(DummySource.class);

    TwitterStream twitterStream = null;


    @Override
    public void configure(Context context) {
        System.out.println("configure() method called....");
        String consumerKey = "4BExiNVW9wgr6HdzXgO32SB6h";
        String consumerSecret = "nnkXxxv5JoXJzDzfMhE1l42u9FfK26MCEAnYTJ5GAo0vN36B6w";
        String accessToken = "2156016636-dnO2fgdUXpGBZseXKjVgsxs3yIEtji5g2j4G1lX";
        String accessTokenSecret = "HHaHl8Tr49VSSZgjVNOakk2As2jGcbC60sWSzzFeNKNoB";


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(accessToken);
        cb.setOAuthAccessTokenSecret(accessTokenSecret);
        cb.setJSONStoreEnabled(true);
        cb.setIncludeEntitiesEnabled(true);

        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    }

    /**
     * Start processing events. This uses the Twitter Streaming API to sample
     * Twitter, and process tweets.
     */
    @Override
    public void start() {
        // The channel is the piece of Flume that sits between the Source and Sink,
        // and is used to process events.
        System.out.println("start method called....");

        final ChannelProcessor channel = getChannelProcessor();

        System.out.println("channel created....");

        final Map<String, String> headers = new HashMap<String, String>();
        StatusListener listener = new StatusListener() {
            // The onStatus method is executed every time a new tweet comes in.
            public void onStatus(Status status) {
                JSONObject obj = new JSONObject();
                try {
                    obj.append("text",status.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.err.println(e);
                }
                System.out.println("-->" + obj.toString());
                headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
                Event event = EventBuilder.withBody(obj.toString().getBytes(), headers);
                channel.processEvent(event);
            }

            // This listener will ignore everything except for new tweets
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            public void onScrubGeo(long userId, long upToStatusId) {}
            public void onException(Exception ex) {}
            public void onStallWarning(StallWarning warning) {}
        };
        twitterStream.addListener(listener);
        System.out.println("listener added....");
        FilterQuery query = new FilterQuery().track(new String[]{"news"});
        twitterStream.filter(query);

        System.out.println("making call to super.start() method.....");
        super.start();
        System.out.println("super.start() method call made.....");
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done!!!!.....");
    }

    /**
     * Stops the Source's event processing and shuts down the Twitter stream.
     */
    @Override
    public void stop() {
        logger.debug("Shutting down Dummy sample stream...");
        System.out.println("Shutting down Dummy sample stream...sop..");
        super.stop();
    }
}