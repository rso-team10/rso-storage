package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("project")
public class ProjectInfoResource {

    @GET
    @Path("info")
    public Response getProjectInfo() {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setClani(Arrays.asList("nk3007", "ak6689"));
        projectInfo.setOpis_projekta("Naš projekt je namenjen pretoku (streaming) glasbenih posnetkov.");
        projectInfo.setMikrostoritve(buildEndPoints());
        projectInfo.setGithub(Arrays.asList("https://github.com/rso-team10/rso-catalogues","https://github.com/rso-team10/rso-stats", "https://github.com/rso-team10/rso-webapp", "https://github.com/rso-team10/rso-storage"));
        projectInfo.setTravis(Arrays.asList("https://travis-ci.org/rso-team10/rso-stats", "https://travis-ci.org/rso-team10/rso-catalogues", "https://travis-ci.org/rso-team10/rso-storage"));
        projectInfo.setDockerhub(Arrays.asList("https://hub.docker.com/r/kozeljko/stats/", "https://hub.docker.com/r/kozeljko/catalogues/", "https://hub.docker.com/r/kozeljko/storage/"));

        return Response.ok(projectInfo).build();
    }

    private List<String> buildEndPoints() {
        var list = new ArrayList<String>();

        list.add("http://35.184.20.15:8081/v1/artists");
        list.add("http://35.184.20.15:8081/v1/albums");
        list.add("http://35.184.20.15:8081/v1/tracks");
        list.add("http://35.232.226.72:8082/v1/listen");
        list.add("http://35.232.226.72:8082/v1/listen/most");
        list.add("http://35.232.226.72:8082/v1/upload");

        return list;
    }
}
