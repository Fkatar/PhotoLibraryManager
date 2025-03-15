package leiphotos.domain.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

import org.junit.Test;
import leiphotos.domain.metadatareader.JavaXTMetadataReaderAdapter;
import leiphotos.domain.metadatareader.JpegMetadataReader;

public class JavaXTMetadataReaderAdapterTest {

    @Test
    public void imagemAnelJVasconcelosTest() throws FileNotFoundException {

        File file = new File("photos/ArchesNationalPark.JPG");
        JpegMetadataReader reader = new JavaXTMetadataReaderAdapter(file);
        assertEquals("iPhone SE (3rd generation)", reader.getCamera());
        assertEquals("Apple", reader.getManufacturer());

        String time = "1923-01-01T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(time, formatter);
        assertEquals(date, reader.getDate());

    }

    @Test
    public void imagemArchesNationalParkTest() throws FileNotFoundException {

        File file = new File("photos/ArchesNationalPark.JPG");
        JpegMetadataReader reader = new JavaXTMetadataReaderAdapter(file);
        // assertEquals("iPhone SE (3rd generation)", reader.getCamera());
        // assertEquals("Apple", reader.getManufacturer());
        String time = "2009-08-10T16:24:22";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(time, formatter);
        assertEquals(date, reader.getDate());

    }

}
