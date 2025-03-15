package leiphotos.domain.core;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.io.File;

import leiphotos.domain.metadatareader.JpegMetadataReader;
import leiphotos.domain.metadatareader.JavaXTMetadataReaderAdapter;

public enum PhotoFactory {
    INSTANCE;

    public Photo createPhoto(String title, String pathToPhotoFile) throws FileNotFoundException {
        File file = new File(pathToPhotoFile);

        if (!file.exists()) {
            throw new FileNotFoundException("File" + file.getName() + "not found or could not be open");
        }

        JpegMetadataReader reader = new JavaXTMetadataReaderAdapter(file);
        GPSLocation gps;
        if (reader.getGpsLocation() == null) {
            gps = null;
        } else {
            gps = new GPSLocation(reader.getGpsLocation()[1], reader.getGpsLocation()[0]);
        }

        PhotoMetadata metaData = new PhotoMetadata(
                gps,
                reader.getDate(),
                reader.getCamera(),
                reader.getManufacturer());
        return new Photo(title, LocalDateTime.now(), metaData, file);

    }
}