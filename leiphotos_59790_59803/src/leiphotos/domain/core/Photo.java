package leiphotos.domain.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.RegExpMatchable;

public class Photo implements IPhoto, RegExpMatchable {

	// atributos
	private String title;
	private File file;
	private LocalDateTime captureDate;
	private LocalDateTime addedDate;
	private boolean isFavourite;
	private GPSCoordinates gps;

	private PhotoMetadata meta;

	/**
	 * Constroi uma foto com os atributos dados
	 *
	 * @param title
	 * @param dateAddedLib
	 * @param meta
	 * @param pathToFile
	 * @throws FileNotFoundException
	 */
	public Photo(String title, LocalDateTime dateAddedLib, PhotoMetadata meta, File pathToFile)
			throws FileNotFoundException {
		this.title = title;
		captureDate = meta.date();
		this.meta = meta;
		addedDate = dateAddedLib;
		isFavourite = false;
		gps = meta.gps();
		if (!pathToFile.exists()) {
			throw new FileNotFoundException("File" + file.getName() + "not found or could not be open");
		}
		this.file = pathToFile;
	}

	/**
	 * Retorna o título da foto.
	 *
	 * @return o título da foto
	 */
	@Override
	public String title() {
		if (title == null) {
			return "Title";
		}
		return title;
	}

	/**
	 * Retorna o captureDate
	 *
	 * @return o captureDate
	 */
	@Override
	public LocalDateTime capturedDate() {
		return captureDate;
	}

	/**
	 * Retorna o addedDate
	 *
	 * @return o addedDate
	 */
	@Override
	public LocalDateTime addedDate() {
		if (addedDate == null)
			return LocalDateTime.now();
		return addedDate;
	}

	/**
	 * Retorna se a foto é favorita
	 *
	 * @return se a foto é favorita
	 */
	@Override
	public boolean isFavourite() {
		return isFavourite;
	}

	/**
	 * Alterna o estado de isFavourite da foto.
	 */
	@Override
	public void toggleFavourite() {
		isFavourite = !isFavourite;
	}

	/**
	 * Retorna se possivel o gps senao retorna um null
	 *
	 * @return se possivel o gps senao retorna um null
	 */
	@Override
	public Optional<? extends GPSCoordinates> getPlace() {
		return Optional.ofNullable(gps);
	}

	/**
	 * Retorna o tamanho da foto.
	 *
	 * @return o tamanho da foto
	 */
	@Override
	public long size() {
		return file.length();
	}

	/**
	 * Retorna o ficheiro da foto.
	 *
	 * @return o ficheiro da foto
	 */
	@Override
	public File file() {
		return file;
	}

	/**
	 * Verifica se a foto dá match com a expressao dada
	 *
	 * @param regexp
	 * @return se a foto dá match com a expressao
	 */
	@Override
	public boolean matches(String regexp) {
		return meta.matches(regexp) || title.matches(regexp) || Long.toString(size()).matches(regexp);
	}

	/**
	 * Compara esta foto com outra
	 *
	 * @param obj
	 * @return se a foto é igual segundo a nossa comparação
	 */
	@Override
	public boolean equals(Object obj) {
		IPhoto photo = (IPhoto) obj;
		return (this.title.equals(photo.title()) &&
				this.file.equals(photo.file()) &&
				this.captureDate.equals(photo.capturedDate()) &&
				this.getPlace().equals(photo.getPlace()));

	}

	/**
	 * Retorna uma string com os varios elementos da foto.
	 *
	 * @return uma string com os varios elementos da foto.
	 */
	@Override
	public String toString() {
		String strGPS;
		if (gps == null) {
			strGPS = "No Location";
		} else {
			strGPS = "{Lat:" + gps.latitude() + "Long:" + gps.longitude() + " Desc:}";
		}
		return "File:" + file +
				"\nTitle:" + title + " Size:" + this.size() + " Added Date:" + addedDate.toString() +
				"\n" + strGPS + ", " + captureDate.toString() + ", " + meta.manufacturer().toString() + "\n";
	}

}
