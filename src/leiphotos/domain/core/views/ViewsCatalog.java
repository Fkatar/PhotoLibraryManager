package leiphotos.domain.core.views;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;
import leiphotos.utils.Listener;

/**
 * Represents a catalog of views for different libraries
 * 
 * @author fc59790_59803
 */
public class ViewsCatalog implements IViewsCatalog {
	MainLibrary main;
	TrashLibrary trash;
	Listener<LibraryEvent> listener;

	/**
	 * Constructor for ViewsCatalog
	 *
	 * @param mainLib  The main library
	 * @param trashLib The trash library
	 */
	public ViewsCatalog(MainLibrary mainLib, TrashLibrary trashLib) {
		this.main = mainLib;
		this.trash = trashLib;
	}

	/**
	 * Returns the appropriate view based on the specified view type
	 *
	 * @param t The type of view to be retrieved.
	 * @return The library view corresponding to the specified type
	 */
	@Override
	public ILibraryView getView(ViewsType t) {
		ILibraryView libraryView;
		switch (t) {
			case ALL_MAIN:
				MainLibraryView mainView = new MainLibraryView(main);
				libraryView = mainView;
				break;
			case ALL_TRASH:
				TrashLibraryView trashView = new TrashLibraryView(trash);
				libraryView = trashView;
				break;
			case FAVOURITES_MAIN:
				Predicate<IPhoto> fav = IPhoto::isFavourite;
				MainLibraryView favView = new MainLibraryView(main, fav);
				libraryView = favView;
				break;
			default:
				Predicate<IPhoto> recent = p -> p.capturedDate().until(LocalDateTime.now(), ChronoUnit.DAYS) < 365;
				MainLibraryView recentView = new MainLibraryView(main, recent);
				libraryView = recentView;
				break;
		}
		return libraryView;
	}

	@Override
	public String toString() {
		return main.toString() + trash.toString();
	}
}
