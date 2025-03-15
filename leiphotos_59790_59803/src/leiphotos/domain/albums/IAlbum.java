package leiphotos.domain.albums;
import java.util.List;
import java.util.Set;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;


public interface IAlbum extends Listener<LibraryEvent> {
    
int numberOfPhotos();

String getName();

List<IPhoto> getPhotos();

boolean addPhotos(Set<IPhoto> selectedPhotos);

boolean removePhotos(Set<IPhoto> selectedPhotos);

}
