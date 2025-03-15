package leiphotos.domain.albums;

import java.net.http.WebSocket.Listener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IPhoto;

public class AlbumsCatalog implements IAlbumsCatalog {

	private HashMap<String, Album> albums;
	private MainLibrary mainLib;

	/**
	 * Constrói um catálogo de álbuns com a biblioteca principal especificada.
	 *
	 * @param mainLib a biblioteca principal
	 */
	public AlbumsCatalog(MainLibrary mainLib) {
		albums = new HashMap<>();
		this.mainLib = mainLib;
	}

	/**
	 * Cria um novo álbum com o nome recebido
	 *
	 * @param albumName
	 * @return se o album foi criado ou nao
	 */
	@Override
	public boolean createAlbum(String albumName) {
		if (containsAlbum(albumName)) {
			return false;
		}
		Album album = new Album(albumName);
		albums.put(albumName, album);
		mainLib.registerListener(album);
		return true;
	}

	/**
	 * Apaga o album com o nome dado
	 *
	 * @param albumName o nome do álbum a ser excluído
	 * @return true se o álbum foi excluído com sucesso, false caso contrário
	 */
	@Override
	public boolean deleteAlbum(String albumName) {
		if (!containsAlbum(albumName)) {
			return false;
		}
		albums.remove(albumName);
		mainLib.unregisterListener(albums.get(albumName));
		return true;
	}

	/**
	 * Vê se album com o nome dado está no catalog
	 *
	 * @param albumName
	 * @return se o album foi encontrado ou nao
	 */
	@Override
	public boolean containsAlbum(String albumName) {
		return albums.containsKey(albumName);

	}

	/**
	 * Adiciona as fotos dadas ao album com o nome dado
	 *
	 * @param albumName
	 * @param selectedPhotos
	 * @return se as fotos foram ou não adicionadas ao album
	 */
	@Override
	public boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if (containsAlbum(albumName)) {
			return albums.get(albumName).addPhotos(selectedPhotos);
		}
		return false;
	}

	/**
	 * Remove as fotos selecionadas ao album com o nome dado
	 *
	 * @param albumName
	 * @param selectedPhotos
	 * @return se as fotos foram realmente retiradas do album
	 */
	@Override
	public boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if (containsAlbum(albumName)) {
			return albums.get(albumName).removePhotos(selectedPhotos);
		}
		return false;
	}

	/**
	 * Retorna as fotos do album com o nome dado
	 *
	 * @param albumName
	 * @return a lista de fotos no álbum
	 */
	@Override
	public List<IPhoto> getPhotos(String albumName) {
		return albums.get(albumName).getPhotos();
	}

	/**
	 * Retorna um set com o nome de todos os albums
	 *
	 * @return um set com o nome de todos os albums
	 */
	@Override
	public Set<String> getAlbumsNames() {
		return albums.keySet();
	}

	/**
	 * Retorna uma string contendo a informação de todas as fotos do album
	 *
	 * @return uma string contendo a informação de todas as fotos do album
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (IAlbum album : albums.values()) {
			sb.append(album.toString());
		}
		return sb.toString();
	}
}
