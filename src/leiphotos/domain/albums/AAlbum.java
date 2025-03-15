package leiphotos.domain.albums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoAddedLibraryEvent;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.core.PhotoChangedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

public abstract class AAlbum implements IAlbum {

    // atributos
    private String name;
    private List<IPhoto> list;

    /**
     * Constroi um album com nome name
     *
     * @param name
     */
    protected AAlbum(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    /**
     * Retorna o número de fotos no álbum.
     *
     * @return o número de fotos
     */
    @Override
    public int numberOfPhotos() {
        return list.size();
    }

    /**
     * Retorna o nome do álbum.
     *
     * @return o nome do álbum
     */
    @Override
    public String getName() {
        return name;

    }

    /**
     * Retorna todas as fotos do álbum.
     *
     * @return List<IPhoto>
     */
    @Override
    public List<IPhoto> getPhotos() {
        return list;

    }

    /**
     * Retorna se todas as fotos selecionadas foram adicionadas
     * Se alguma já lá estiver nenhuma é adicionada
     *
     * @param selectedPhotos as fotos selecionadas
     * @return boolean
     */
    @Override
    public boolean addPhotos(Set<IPhoto> selectedPhotos) {
        for (IPhoto photo : selectedPhotos) {
            if (list.contains(photo)) {
                return false;
            }
        }
        list.addAll(selectedPhotos);
        return true;
    }

    /**
     * Retorna se todas as fotos selecionadas foram removidas
     * Se alguma selecionada não estiver nenhuma é removida
     *
     * @param selectedPhotos as fotos selecionadas
     * @return boolean
     */
    @Override
    public boolean removePhotos(Set<IPhoto> selectedPhotos) {
        for (IPhoto photo : selectedPhotos) {
            if (!list.contains(photo)) {
                return false;
            }
        }
        list.removeAll(selectedPhotos);
        return true;

    }

    /**
     * Processa o evento enviado pela library
     *
     * @param LibraryEvent
     */
    @Override
    public void processEvent(LibraryEvent e) {

        if (e instanceof PhotoAddedLibraryEvent) {
            return;
        } else if (e instanceof PhotoDeletedLibraryEvent) {
            Set photos = new HashSet<IPhoto>();
            photos.add(e.getPhoto());
            removePhotos(photos);
        } else if (e instanceof PhotoChangedLibraryEvent) {
            return;
        }
    }

    /**
     * Retorna uma string que representa o álbum.
     *
     * @return uma string representando o álbum
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IPhoto photo : list) {
            sb.append(photo.toString());
        }
        return sb.toString();
    }

}
