package com.netflix.recipes.rss.pytheas.resources;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.recipes.rss.hystrix.AddRSSCommand;
import com.netflix.recipes.rss.hystrix.DeleteRSSCommand;
import com.netflix.recipes.rss.hystrix.GetRSSCommand;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Path("/feeds")
public class FeedsResource {
    private Logger LOG = LoggerFactory.getLogger(FeedsResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeeds()
    {
        HystrixCommand<String> getCommand = new GetRSSCommand();
        Future<String> future = getCommand.queue();

        try {
            String responseString = future.get();
            return Response.ok(responseString).build();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException in fetching RSS Feeds");
        } catch (ExecutionException e) {
            LOG.error("ExecutionException in fetching RSS Feeds");
        }

        return Response.ok().build();
    }

    @POST
    @Consumes("*/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFeed(@FormParam("feedURL") String feedURL) {
        HystrixCommand<String> addCommand = new AddRSSCommand(feedURL);
        Future<String> future = addCommand.queue();

        try {
            String response = future.get();
            LOG.debug("Response " + response);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException in adding RSS feed url - " + feedURL);
        } catch (ExecutionException e) {
            LOG.error("ExecutionException in adding RSS feed url - " + feedURL);
        }

        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFeedUrl(@FormParam("feedURL") String feedURL) {
        HystrixCommand<String> deleteCommand = new DeleteRSSCommand(feedURL);
        Future<String> future = deleteCommand.queue();

        try {
            String response = future.get();
            LOG.debug("Response " + response);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException in adding RSS feed url - " + feedURL);
        } catch (ExecutionException e) {
            LOG.error("ExecutionException in adding RSS feed url - " + feedURL);
        }

        return Response.ok().build();
    }

    @GET
    @Path("/admin")
    @Produces( MediaType.TEXT_HTML )
    public Viewable viewFeeds()
    {
        LOG.info("showIndex");
        Map<String, Object> model = new HashMap<String, Object>();
        return new Viewable( "/pytheas/index.ftl", model );
    }

}
