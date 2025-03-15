package leiphotos.domain.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoFactory;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IPhoto;

/**
 * The class Libraries Controller in the LeiPhotos application.
 * This interface provides methods to import, delete, empty trash, toggle
 * favorite,
 * and search photos in libraries.
 * 
 * @author fc59790_59803
 */
public class LibrariesController implements ILibrariesController {

	private MainLibrary mainLib;
	private TrashLibrary trashLib;

	/**
	 * Constrói um controller de librarys
	 *
	 * @param mainLib  a biblioteca principal
	 * @param trashLib a lixeira
	 */
	public LibrariesController(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLib = mainLib;
		this.trashLib = trashLib;
	}

	/**
	 * Importa uma foto
	 *
	 * @param title
	 * @param pathToPhotoFile
	 * @return a foto criada ou um Optional.empty() no caso de o
	 *         FileNotFoundException ser lançado
	 */
	@Override
	public Optional<IPhoto> importPhoto(String title, String pathToPhotoFile) {
		IPhoto newPhoto;
		try {
			newPhoto = PhotoFactory.INSTANCE.createPhoto(title, pathToPhotoFile);
			boolean add = mainLib.addPhoto(newPhoto);
			if (add) {
				return Optional.of(newPhoto);
			}
			return Optional.empty();
		} catch (FileNotFoundException e) {
			return Optional.empty();
		}

	}

	/**
	 * Apaga as selectedPhotos se elas existirem
	 *
	 * @param selectedPhotos
	 */
	@Override
	public void deletePhotos(Set<IPhoto> selectedPhotos) {
		for (IPhoto photo : selectedPhotos) {
			mainLib.deletePhoto(photo);
			trashLib.addPhoto(photo);
		}
	}

	/**
	 * Esvazia a trashLibrary
	 */
	@Override
	public void emptyTrash() {
		trashLib.deleteAll();
	}

	/**
	 * Muda o estado de favoritismo de uma foto.
	 *
	 * @param selectedPhotos
	 */
	@Override
	public void toggleFavourite(Set<IPhoto> selectedPhotos) {
		for (IPhoto photo : selectedPhotos) {
			photo.toggleFavourite();
		}
	}

	/**
	 * Retorna as fotos que correspondem com a regExp
	 *
	 * @param regExp
	 * @return as fotos que correspondem com a regExp
	 */
	@Override
	public Iterable<IPhoto> getMatches(String regExp) {
		Collection<IPhoto> match = new ArrayList<>();
		match.addAll(mainLib.getMatches(regExp));
		match.addAll(trashLib.getMatches(regExp));
		return match;
	}

	/**
	 * Retorna uma string com as fotos das librarys
	 *
	 * @return uma string com as fotos das librarys
	 */
	@Override
	public String toString() {
		return mainLib.toString() + trashLib.toString();
	}

}
