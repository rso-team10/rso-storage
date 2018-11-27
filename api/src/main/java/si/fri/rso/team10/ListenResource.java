package si.fri.rso.team10;

import si.fri.rso.team10.dto.TrackCount;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("listen")
public class ListenResource {

    @GET
    public Response getAllCounts() {
        return null;
    }

    @GET
    @Path("/most")
    public Response getMostListenedTrack() {
        return null;
    }

    @GET
    @Path("{trackId}")
    public Response getTrackCount(@PathParam("trackId") String trackId) {
        return null;
    }
}
