package leiphotos.domain.core;

import java.time.LocalDateTime;

import leiphotos.utils.RegExpMatchable;

public record PhotoMetadata(GPSLocation gps, LocalDateTime date, String camera, String manufacturer)
        implements RegExpMatchable {

    @Override
    public boolean matches(String regexp) {
        return gps.matches(regexp) || camera.matches(regexp) || manufacturer.matches(regexp);
    }

    @Override
    public final String toString() {
        return "File: ";
    }
}
