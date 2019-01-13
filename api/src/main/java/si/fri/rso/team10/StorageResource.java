package si.fri.rso.team10;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("storage")
public class StorageResource {

    @Inject
    private StorageService storageService;

    @Inject
    @Metric(name = "stream-counter")
    private Counter counter;

    @Inject
    @Metric(name = "stream-frequency")
    private Meter meter;

    @GET
    @Path("{trackId}")
    public Response streamSong(@PathParam("trackId") String trackId) {
        try {
            var mediaLink = storageService.getFileUrl(Long.valueOf(trackId));
            if (mediaLink == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            storageService.recordStream(Long.valueOf(trackId));

            counter.inc();
            meter.mark();

            return Response.ok(mediaLink).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("{trackId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSong(@PathParam("trackId") String trackId, File file) {
        try {
            var uploadSuccessful = storageService.uploadFile(Long.valueOf(trackId), file);
            if (!uploadSuccessful) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            var success = storageService.activateSong(Long.valueOf(trackId));
            if (success) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
