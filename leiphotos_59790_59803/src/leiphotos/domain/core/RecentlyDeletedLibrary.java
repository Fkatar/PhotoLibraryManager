package leiphotos.domain.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import leiphotos.domain.facade.IPhoto;

public class RecentlyDeletedLibrary extends ATrashLibrary {
	private LocalDateTime update;

	public RecentlyDeletedLibrary() {
		super();
		update = LocalDateTime.now();
	}

	@Override
	protected void clean() {
		saveTime.forEach((photo, time) -> {
			if (time.until(LocalDateTime.now(), ChronoUnit.SECONDS) >= 15) {
				saveTime.remove(photo, time);
			}
		});

	}

	@Override
	protected boolean cleaningTime() {
		if (update.until(LocalDateTime.now(), ChronoUnit.SECONDS) >= 15) {
			update = LocalDateTime.now();
			return true;
		}
		return false;
	}

	@Override
	public int getNumberOfPhotos() {
		return saveTime.size();
	}

	@Override
	public boolean addPhoto(IPhoto photo) {
		if (saveTime.keySet().contains(photo)) {
			return false;
		}
		saveTime.put(photo, LocalDateTime.now());
		return true;
	}

	@Override
	public boolean deletePhoto(IPhoto photo) {
		return saveTime.remove(photo, saveTime.get(photo));
	}

	@Override
	public Collection<IPhoto> getMatches(String regexp) {
		Collection<IPhoto> match = new ArrayList<>();
		saveTime.forEach((photo, time) -> {
			if (photo.matches(regexp)) {
				match.add(photo);
			}
		});
		return match;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		saveTime.forEach((photo, time) -> {
			sb.append(photo.toString());
		});
		return sb.toString();
	}
}
