/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.netflix.recipes.rss.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.recipes.rss.util.RSSModule;
import com.netflix.recipes.rss.server.MiddleTierServer;
import com.google.common.io.Closeables;
import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

/**
 * Middle Tier Test
 */
public class EmbeddedMiddleTierForTests {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddedMiddleTierForTests.class);

    public  MiddleTierServer middleTierServer;
    
    public EmbeddedMiddleTierForTests() {
    }
    
    public void setUp() throws Exception {
    	System.setProperty("archaius.deployment.applicationId", "middletier");
    	System.setProperty("archaius.deployment.environment", "ci");
        
    	Injector injector = LifecycleInjector.builder().withModules(new RSSModule()).createInjector();

    	LifecycleManager lifecycleManager = injector.getInstance(LifecycleManager.class);
    	lifecycleManager.start();

    	middleTierServer = injector.getInstance(MiddleTierServer.class);
    	middleTierServer.start();
    }

    public void tearDown() throws Exception {
        Closeables.closeQuietly(middleTierServer);
    }
}
