package leiphotos.domain.controllers;

import java.util.Comparator;
import java.util.List;

import leiphotos.domain.core.views.ILibraryView;
import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.ViewsType;

/**
 * Represents a controller for managing library views
 * It provides methods to retrieve photos based on different views and sorting
 * criteria
 * 
 * @author fc59790_59803
 */
public class ViewsController implements IViewsController {
	IViewsCatalog views;
	ILibraryView library;

	/**
	 * Constructor for ViewsController.
	 *
	 * @param views The catalog of views to be managed by this controller.
	 */
	public ViewsController(IViewsCatalog views) {
		this.views = views;
	}

	/**
	 * Retrieves a list of photos based on the specified view type.
	 * The list has the photos of the view sorted according to
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType The type of view to retrieve photos from
	 * @return a list of photos
	 */
	@Override
	public List<IPhoto> getPhotos(ViewsType viewType) {
		library = views.getView(viewType);
		return library.getPhotos();
	}

	/**
	 * Retrieves a list of photos based on the specified view type and regular
	 * expression.
	 * The list has the photos sorted according to
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType The type of view to retrieve photos from
	 * @param regexp   The regular expression to match against photo properties
	 * @return a list of photos that match the regular expression
	 */
	@Override
	public List<IPhoto> getMatches(ViewsType viewType, String regexp) {
		library = views.getView(viewType);
		return library.getMatches(regexp);
	}

	/**
	 * Sets the sorting criteria for the specified view type.
	 *
	 * @param v        The type of view to set the sorting criteria for
	 * @param criteria the comparator to use for sorting the photos
	 */
	@Override
	public void setSortingCriteria(ViewsType v, Comparator<IPhoto> criteria) {
		library = views.getView(v);
		library.setComparator(criteria);
	}

	/**
	 * Retorna uma string que representa as views
	 *
	 * @return uma string que representa as views
	 */
	@Override
	public String toString() {
		return views.toString();
	}

}
