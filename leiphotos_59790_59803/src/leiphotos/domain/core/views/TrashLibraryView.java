package leiphotos.domain.core.views;

import leiphotos.domain.core.TrashLibrary;

/**
 * Represents a view of the trash library
 * 
 * @author fc59790_59803
 */
public class TrashLibraryView extends ALibraryView {
	TrashLibrary trash;
	
	/**
     * Constructor for TrashLibraryView.
     *
     * @param trash The trash library to which the view refers
     */
	TrashLibraryView(TrashLibrary trash) {
		super(trash);
		this.trash = trash;
	}
}

