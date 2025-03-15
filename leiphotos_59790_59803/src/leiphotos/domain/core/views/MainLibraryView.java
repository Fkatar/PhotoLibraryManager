/**
 * 
 */
package leiphotos.domain.core.views;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.function.Predicate;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoAddedLibraryEvent;
import leiphotos.domain.core.PhotoChangedLibraryEvent;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

/**
 * Represents a view of the main library
 * 
 * @author fc59790_59803
 */
public class MainLibraryView extends ALibraryView implements Listener<LibraryEvent> {
	MainLibrary mainLibrary;
	LinkedList<IPhoto> cache;

	/**
	 * Constructor for MainLibraryView.
	 *
	 * @param mainLibrary The main library to which the view refers
	 * @param p           The predicate to determine if a photo belongs to the view
	 * @param list        The listener for library events
	 */
	public MainLibraryView(MainLibrary mainLibrary, Predicate<IPhoto> p) {
		super(mainLibrary, p);
		this.mainLibrary = mainLibrary;
		cache = new LinkedList<>();
		cache.addAll(getPhotos());
	}

	/**
	 * Constructor for MainLibraryView
	 *
	 * @param mainLibrary The main library to which the view refers
	 * @param list        The listener for library events
	 */
	public MainLibraryView(MainLibrary mainLibrary) {
		super(mainLibrary);
		this.mainLibrary = mainLibrary;
		cache = new LinkedList<>();
		cache.addAll(getPhotos());
	}

	/**
	 * Processes the library event
	 *
	 * @param e The library event to be processed
	 */
	@Override
	public void processEvent(LibraryEvent e) {
		if (e instanceof PhotoDeletedLibraryEvent) {
			cache.remove(e.getPhoto());
		} else if (e instanceof PhotoAddedLibraryEvent) {
			cache.add(e.getPhoto());
		} else if (e instanceof PhotoChangedLibraryEvent) {
			if (e.getPhoto().isFavourite() && !cache.contains(e.getPhoto()) && cache.element().isFavourite()) {
				cache.add(e.getPhoto());
			} else if (!e.getPhoto().isFavourite() && cache.contains(e.getPhoto())) {
				cache.remove(e.getPhoto());
			} else if (!isRecent(e.getPhoto()) && isRecent(cache.element())) {
				cache.remove(e.getPhoto());
			}
		}
		throw new UnsupportedOperationException("Unimplemented method 'processEvent'");
	}

	/**
	 * Checks if a photo is recent
	 * 
	 * @param photo the photo that is going to be checked
	 * @return true if the photo is recent
	 */
	private boolean isRecent(IPhoto photo) {
		return photo.capturedDate().until(LocalDateTime.now(), ChronoUnit.DAYS) < 365;
	}

}
