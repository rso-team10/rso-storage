package si.fri.rso.team10.dto;

import java.util.Date;

public class UploadDate {

    private Long trackId;

    public UploadDate(Long trackId) {
        this.trackId = trackId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }
}
