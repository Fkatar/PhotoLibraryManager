package leiphotos.domain.albums;
import java.util.List;
import java.util.Set;

import leiphotos.domain.facade.IPhoto;
//Class automatically generated so the code compiles
//CHANGE ME
public interface IAlbumsCatalog {
    
    boolean createAlbum(String albumName);

    boolean deleteAlbum(String albumName);

    boolean containsAlbum(String albumName);

    boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos);

    boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos);

    List<IPhoto> getPhotos(String albumName);

    Set<String> getAlbumsNames();
}
