package leiphotos.domain.core;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.utils.RegExpMatchable;

public record GPSLocation(double latitude, double longitude) implements GPSCoordinates, RegExpMatchable {

	@Override
	public boolean matches(String regexp) {
		return this.toString().matches(regexp);
	}

}
