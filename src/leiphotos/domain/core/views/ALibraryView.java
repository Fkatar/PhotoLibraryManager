package leiphotos.domain.core.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Represents the views of libraries
 * 
 * @author fc59790_59803
 */
public abstract class ALibraryView implements ILibraryView {
	Predicate<IPhoto> predicate;
	Comparator<IPhoto> comp;
	Library library;

	/**
	 * Constructor for ALibraryView
	 * 
	 * @param library   The library to which the view refers.
	 * @param predicate The predicate to determine if a photo belongs to the view
	 */
	protected ALibraryView(Library library, Predicate<IPhoto> p) {
		this.library = library;
		this.predicate = p;
		this.comp = Comparator.comparingLong(IPhoto::size);
	}

	/**
	 * Constructor for ALibraryView
	 * 
	 * @param library The library to which the view refers
	 */
	protected ALibraryView(Library library) {
		this.library = library;
		this.comp = Comparator.comparingLong(IPhoto::size);
	}

	/**
	 * Set a comparator used for sorting photos in the view
	 * 
	 * @param c The comparator to be set
	 */
	@Override
	public void setComparator(Comparator<IPhoto> c) {
		this.comp = c;
	}

	/**
	 * Returns the number of photos in the view
	 * 
	 * @return the number of photos in view
	 */
	@Override
	public int numberOfPhotos() {
		return this.getPhotos().size();
	}

	/**
	 * Returns the photos in view following the current sorting,
	 * criteria
	 * 
	 * @return The photos in view sorted
	 */
	@Override
	public List<IPhoto> getPhotos() {
		List<IPhoto> allPhotos;
		if (predicate != null) {
			allPhotos = library.getPhotos().stream().filter(s -> predicate.test(s)).collect(Collectors.toList());
		} else {
			allPhotos = collectionsToList(library.getPhotos());
		}
		allPhotos.sort(comp);
		return allPhotos;
	}

	/**
	 * Returns the photos in view that match with the given
	 * expression, sorted by the current criteria
	 * 
	 * @param regexp The expression that is going to be checked
	 * @return all the photos in view that match with the given
	 *         expression
	 */
	@Override
	public List<IPhoto> getMatches(String regexp) {
		List<IPhoto> allPhotos = collectionsToList(library.getMatches(regexp));
		if (predicate != null) {
			allPhotos = allPhotos.stream().filter(s -> predicate.test(s)).collect(Collectors.toList());
		} else {
			allPhotos = collectionsToList(library.getMatches(regexp));
		}
		allPhotos.sort(comp);
		return allPhotos;
	}

	/**
	 * Change a collection to a list
	 * 
	 * @param collPhotos the collection
	 * @return the new list
	 */
	private List<IPhoto> collectionsToList(Collection<IPhoto> collPhotos) {
		List<IPhoto> allPhotos;
		if (collPhotos == null) {
			allPhotos = new ArrayList<>(); // Initialize an empty list if collPhotos is null
		} else if (collPhotos instanceof List) {
			allPhotos = (List<IPhoto>) collPhotos;
		} else {
			allPhotos = new ArrayList<>(collPhotos);
		}
		return allPhotos;
	}
}
