package com.netflix.recipes.rss.pytheas;

import com.netflix.explorers.annotations.ExplorerGuiceModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.HashMap;
import java.util.Map;

@ExplorerGuiceModule
public class FeedsGuiceModule extends JerseyServletModule {
    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class).asEagerSingleton();
        Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.config.property.packages",
                "com.netflix.explorers.resources;" +
                        "com.netflix.explorers.providers;" +
                        "com.netflix.recipes.rss.pytheas");

        // Route all requests through GuiceContainer
        serve("/*").with(GuiceContainer.class, params);
    }
}
