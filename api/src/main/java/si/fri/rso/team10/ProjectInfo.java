package si.fri.rso.team10;

import java.util.List;

public class ProjectInfo {
    private List<String> clani;
    private String opis_projekta;
    private List<String> mikrostoritve;
    private List<String> github;
    private List<String> travis;
    private List<String> dockerhub;

    public void setClani(List<String> clani) {
        this.clani = clani;
    }

    public void setOpis_projekta(String opis_projekta) {
        this.opis_projekta = opis_projekta;
    }

    public void setMikrostoritve(List<String> mikrostoritve) {
        this.mikrostoritve = mikrostoritve;
    }

    public void setGithub(List<String> github) {
        this.github = github;
    }

    public void setTravis(List<String> travis) {
        this.travis = travis;
    }

    public void setDockerhub(List<String> dockerhub) {
        this.dockerhub = dockerhub;
    }

    public List<String> getClani() {
        return clani;
    }

    public String getOpis_projekta() {
        return opis_projekta;
    }

    public List<String> getMikrostoritve() {
        return mikrostoritve;
    }

    public List<String> getGithub() {
        return github;
    }

    public List<String> getTravis() {
        return travis;
    }

    public List<String> getDockerhub() {
        return dockerhub;
    }
}
