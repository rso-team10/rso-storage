package si.fri.rso.team10.dto;

public class TrackCount {

    private Long trackId;
    private Long listenCount;

    public TrackCount(Long trackId, Long listenCount) {
        this.trackId = trackId;
        this.listenCount = listenCount;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getListenCount() {
        return listenCount;
    }

    public void setListenCount(Long listenCount) {
        this.listenCount = listenCount;
    }
}
