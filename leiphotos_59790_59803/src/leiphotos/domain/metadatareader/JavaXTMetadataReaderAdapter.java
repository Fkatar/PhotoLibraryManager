package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javaxt.io.Image;
import leiphotos.domain.albums.AlbumsCatalog;
import leiphotos.services.JavaXTJpegMetadataReader;

public class JavaXTMetadataReaderAdapter implements JpegMetadataReader {
    private JavaXTJpegMetadataReader reader;
    private String camera;
    private String manufacturer;
    private LocalDateTime defaultDateTime;
    private String aperture;

    public JavaXTMetadataReaderAdapter(File file) throws FileNotFoundException {
    	if(!file.exists()) {
    		throw new FileNotFoundException("File"+ file.getName() + "not found or could not be open");
    	}
        String noInformation = "No Data";
        reader = new JavaXTJpegMetadataReader(file);
        camera = noInformation;
        manufacturer = noInformation;
        defaultDateTime = LocalDateTime.of(1923, 01, 01, 0, 0);
        aperture = noInformation;
    }

    @Override
    public String getCamera() {
        if (reader.getCamara() == null) {
            return camera;
        }
        return reader.getCamara();
    }

    @Override
    public String getManufacturer() {
        if (reader.getManufacturer() == null) {
            return manufacturer;
        }
        return reader.getManufacturer();
    }

    @Override
    public LocalDateTime getDate() {
        // turn string into Date
        String time = reader.getDate();
        if (time == null) {
            return defaultDateTime;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
        return LocalDateTime.parse(time, formatter);

    }

    @Override
    public String getAperture() {
        if (reader.getAperture() == null) {
            return aperture;
        }
        return reader.getAperture();
    }

    @Override
    public double[] getGpsLocation() {
        return reader.getGPS();
    }

}
