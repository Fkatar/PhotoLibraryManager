/**
 * 
 */
package leiphotos.domain.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import leiphotos.domain.facade.IPhoto;

/**
 * 
 */
public abstract class ATrashLibrary implements TrashLibrary {
	protected HashMap<IPhoto, LocalDateTime> saveTime;

	/**
	 * 
	 */
	protected ATrashLibrary() {
		saveTime = new HashMap<>();
	}

	protected abstract void clean();

	protected abstract boolean cleaningTime();

	/**
	 * Returns a collection of the photos in the trash library.
	 * Has side-effects: some, undefined, criteria over
	 * the photos is checked when this method is call
	 * and may result in photos to be removed from the library
	 * 
	 * @return A collection of the photos in the library
	 * @ensures \result != null
	 */
	@Override
	public Collection<IPhoto> getPhotos() {
		if (cleaningTime()) {
			clean();
			return saveTime.keySet();
		}
		return saveTime.keySet();
	}

	/**
	 * Removes all photos in the trash library
	 *
	 * @returns if some photo was removed
	 */
	@Override
	public boolean deleteAll() {
		boolean has = !(saveTime.keySet().isEmpty());
		saveTime = new HashMap<>();
		return has;
	}

	/**
	 * Retorna string com as fotos da trashLibrary
	 *
	 * @return uma string com as fotos da trashLibrary
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (IPhoto photo : saveTime.keySet()) {
			sb.append(photo.toString());
		}
		return sb.toString();
	}

}
