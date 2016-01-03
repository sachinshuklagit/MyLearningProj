package com.sachin.learning.stream.twitter;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
import twitter4j.json.DataObjectFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A Flume Source, which pulls data from Twitter's streaming API. Currently,
 * this only supports pulling from the sample API, and only gets new status
 * updates.
 */
public class DummySource extends AbstractSource
        implements EventDrivenSource, Configurable {

    private static final Logger logger =
            LoggerFactory.getLogger(DummySource.class);


    @Override
    public void configure(Context context) {
        System.out.println("configure() method called....");
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

        final Map<String, String> headers = new HashMap<String, String>();

        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
        for(int i=0;i<5;i++){
            Event event = EventBuilder.withBody("{\"name\":\"sachin\",\"desc\":\"test desc\"}".getBytes(), headers);
            channel.processEvent(event);
        }
        super.start();
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