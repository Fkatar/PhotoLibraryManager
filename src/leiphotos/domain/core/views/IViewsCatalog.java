package leiphotos.domain.core.views;

import leiphotos.domain.facade.ViewsType;

/**
 * Represents the catalogs in views  of each type in ViewsType
 * 
 * @author fc59790_59803
 */

public interface IViewsCatalog {
	
	/**
	 * Returns the view in the catalog with a specific 
	 * type (from ViewsType)
	 * 
	 * @param t The type that is going to be returned
	 * @return The view in the catalog with a specific type
	 */
	ILibraryView getView(ViewsType t); 
}
