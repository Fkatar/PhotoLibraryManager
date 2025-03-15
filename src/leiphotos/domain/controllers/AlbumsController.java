package leiphotos.domain.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;

/**
 * Represents a controller for managing albums in the photo application
 * 
 * @author fc59790_59803
 */
public class AlbumsController implements IAlbumsController {

	private IAlbumsCatalog albumsCatalog;
	private String selectedAlbum;
	private MainLibrary mainLib;

	/**
	 * Constrói um controller de álbuns com o catálogo de álbuns dado
	 *
	 * @param albumsCatalog o catálogo de álbuns
	 */
	public AlbumsController(IAlbumsCatalog albumsCatalog) {
		this.albumsCatalog = albumsCatalog;
	}

	/**
	 * Cria um novo álbum com o nome dado
	 *
	 * @param name
	 * @return se o album foi criado
	 */
	@Override
	public boolean createAlbum(String name) {
		return albumsCatalog.createAlbum(name);
	}

	/**
	 * Remove o selectedAlbum
	 */
	@Override
	public void removeAlbum() {
		if (selectedAlbum != null) {
			albumsCatalog.deleteAlbum(selectedAlbum);
		}
	}

	/**
	 * Seleciona o álbum com o nome dado
	 *
	 * @param name do album
	 */
	@Override
	public void selectAlbum(String name) {
		if (albumsCatalog.containsAlbum(name)) {
			selectedAlbum = name;
		}
	}

	/**
	 * Adiciona as fotos dadas ao selectedAlbum
	 *
	 * @param selectedPhotos as fotos selecionadas
	 */
	@Override
	public void addPhotos(Set<IPhoto> selectedPhotos) {
		albumsCatalog.addPhotos(selectedAlbum, selectedPhotos);

	}

	/**
	 * Remove as fotos selecionadas do selectedAlbum
	 *
	 * @param selectedPhotos
	 */
	@Override
	public void removePhotos(Set<IPhoto> selectedPhotos) {
		if (selectedAlbum != null) {
			albumsCatalog.removePhotos(selectedAlbum, selectedPhotos);
		}

	}

	/**
	 * Retorna as fotos do selectedAlbum
	 *
	 * @return as fotos do selectedAlbum
	 */
	@Override
	public List<IPhoto> getPhotos() {
		if (selectedAlbum != null) {
			return albumsCatalog.getPhotos(selectedAlbum);
		}
		List<IPhoto> empty = new ArrayList<IPhoto>();
		return empty;
	}

	/**
	 * Retorna no caso de haver o selectedAlbum, senão retorna um null
	 *
	 * @return no caso de haver o selectedAlbum, senão retorna um null
	 */
	@Override
	public Optional<String> getSelectedAlbum() {
		return Optional.ofNullable(selectedAlbum);
	}

	/**
	 * Cria um smartAlbum que tem um predicate como critério
	 *
	 * @param name
	 * @param criteria
	 * @return se o album foi ou nao criado
	 */
	@Override
	public boolean createSmartAlbum(String name, Predicate<IPhoto> criteria) {
		if (albumsCatalog.createAlbum(name)) {
			Set<IPhoto> photos = mainLib.getPhotos().stream().filter(s -> criteria.test(s)).collect(Collectors.toSet());
			albumsCatalog.addPhotos(name, photos);
			return true;
		}
		return false;
	}

	/**
	 * Retorna os nomes de todos os Albums presentes no catalog
	 *
	 * @return os nomes de todos os Albums presentes no catalog
	 */
	@Override
	public Set<String> getAlbumNames() {
		return albumsCatalog.getAlbumsNames();
	}

	/**
	 * Retorna uma string com todas as fotos presentes no catalogo de albums
	 *
	 * @return uma string com todas as fotos presentes no catalogo de albums
	 */
	@Override
	public String toString() {
		return albumsCatalog.toString();
	}

}
