package si.fri.rso.team10.dto;

public class ListenInstance {

    private Long trackId;
    private Long userId;

    public ListenInstance(Long trackId) {
        this.trackId = trackId;

        // TODO fix when we have users
        this.userId = 1L;
    }

    public Long getTrackId() {
        return trackId;
    }

    public Long getUserId() {
        return userId;
    }
}
