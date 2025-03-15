package leiphotos.domain.core.views;

import java.util.Comparator;
import java.util.List;
import leiphotos.domain.facade.IPhoto;

/**
 * Represents the views of libraries
 * 
 * @author fc59790_59803
 */
public interface ILibraryView {
	
	/**
	 * Set a comparator used for sorting photos in the view
	 * 
	 * @param c The comparator to be set
	 */
	void setComparator(Comparator<IPhoto> c);
	
	/**
	 * Returns the number of photos in view
	 * 
	 * @return the number of photos in view
	 */
	int numberOfPhotos();
	
	/**
	 * Returns the photos in view following the current sorting
	 * criteria 
	 * 
	 * @return the photos in view sorted
	 */
	List<IPhoto> getPhotos();
	
	/**
	 * Returns the photos in view that match with the given 
	 * expression, sorted by the current criteria
	 * 
	 * @param regexp the expression that is going to be checked
	 * @return all The photos in view that match with the given 
	 * expression
	 */
	List<IPhoto> getMatches(String regexp);
}
