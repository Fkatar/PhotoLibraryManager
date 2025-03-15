package leiphotos.domain.core;

import java.util.Collection;
import java.util.HashSet;

import leiphotos.domain.albums.IAlbum;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;

//Class automatically generated so the code compiles
//CHANGE ME
public class MainLibrary extends AbsSubject<LibraryEvent> implements Library {

	// Nota:
	// Uma biblioteca destas deve notificar os seus observadores, com eventos
	// apropriados para o efeito, sempre
	// que (1) é removida uma foto, (2) é adicionada uma foto e (3) alguma foto
	// sofre uma mudança.

	private Collection<IPhoto> photos;

	// Construtor
	public MainLibrary() {
		super();
		photos = new HashSet<>();
		// Instantiate a HashSet to hold Photo objects
	}

	/**
	 * Retorna o número de fotos da biblioteca principal.
	 *
	 * @return o número de fotos da biblioteca principal
	 */
	@Override
	public int getNumberOfPhotos() {
		return photos.size();
	}

	/**
	 * Adiciona uma foto e avisa os albums que estão á escuta
	 *
	 * @param photo
	 * @return se a foto foi adicionada ou nao
	 */
	@Override
	public boolean addPhoto(IPhoto photo) {
		if (photos.contains(photo)) {
			return false;
		}
		photos.add(photo);
		emitEvent(new PhotoAddedLibraryEvent(photo, this));
		return true;
	}

	/**
	 * Remove uma foto e avisa os albums que estão á escuta
	 *
	 * @param photo
	 * @return se a foto foi removida ou nao
	 */
	@Override
	public boolean deletePhoto(IPhoto photo) {
		if (photos.contains(photo)) {
			photos.remove(photo);
			emitEvent(new PhotoDeletedLibraryEvent(photo, this));
			return true;
		}
		return false;
	}

	/**
	 * Retorna as fotos da library
	 *
	 * @return as fotos da library
	 */
	@Override
	public Collection<IPhoto> getPhotos() {
		return photos;
	}

	/**
	 * Retorna uma coleção de fotos que correspondem à expressão especificada.
	 *
	 * @param regexp
	 * @return uma coleção de fotos que correspondem à expressão especificada.
	 */
	@Override
	public Collection<IPhoto> getMatches(String regexp) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (IPhoto photo : photos) {
			sb.append(photo.toString());
		}
		return sb.toString();
	}
}
