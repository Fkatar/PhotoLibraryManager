package leiphotos.domain.core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.metadatareader.JavaXTMetadataReaderAdapter;
import leiphotos.domain.metadatareader.JpegMetadataException;
import leiphotos.domain.metadatareader.JpegMetadataReader;
import leiphotos.domain.metadatareader.JpegMetadataReaderFactory;
import leiphotos.domain.facade.IPhoto;

class PhotoTest {

	@Test
	void testCreatePhotoWithoutGPS() throws FileNotFoundException {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		IPhoto photo = new Photo(expectedTitle, expectedAddedDate, null, expectedFile);
		
		assertEquals(photo.title(), expectedTitle);
		assertEquals(photo.file(), expectedFile);
		//assertEquals(photo.addedDate(), expectedAddedDate);
		assertEquals(photo.capturedDate(), expectedCapturedDate);
	}

	@Test
	void testCreatePhotoWithGPS() throws FileNotFoundException {
		String fileName = "photos/AnelJVasconcelos.jpeg";
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 2, 12, 9, 50);
		File expectedFile = new File(fileName);
		String expectedTitle = "Anel";
		//LocalDateTime expectedAddedDate = LocalDateTime.now();

		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", fileName);
		assertEquals(photo.title(), expectedTitle);
		assertEquals(photo.file(), expectedFile);
		//assertEquals(photo.addedDate(), expectedAddedDate);
		assertEquals(photo.capturedDate(), expectedCapturedDate);
	}

	@Test
	void testToggleFavourite() throws FileNotFoundException {
		String fileName = "photos/AnelJVasconcelos.jpeg";
		File expectedFile = new File(fileName);
		String expectedTitle = "Anel";
		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", fileName);
		photo.toggleFavourite();
		assertTrue(photo.isFavourite());
	}

	@Test
	void testSize() throws FileNotFoundException { // requires the use of a mock file class
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("photos/AnelJVasconcelos.jpeg", expectedSize);
		String fileName = expectedFile.getPath();
		String expectedTitle = "Anel";
		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", fileName);
		assertEquals(photo.size(), expectedSize);

	}

	@Test
	void testNoMatches() throws FileNotFoundException {
		String regexp = "Exp.*";
		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", "photos/AnelJVasconcelos.jpeg");
		assertFalse(photo.matches(regexp));
	}

	@Test
	void testMatchesTitle() throws FileNotFoundException {
		String regexp = "Anel.*";
		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", "photos/AnelJVasconcelos.jpeg");
		assertFalse(photo.matches(regexp));
	}

	@Test
	void testMatchesFile() throws FileNotFoundException {
		String regexp = "AnelJVasconcelos.*";
		IPhoto photo = PhotoFactory.INSTANCE.createPhoto("Anel", "photos/AnelJVasconcelos.jpeg");
		assertFalse(photo.matches(regexp));
	}

	@Test
	void testEquals() throws FileNotFoundException {
		File file1 = new File("photos/AnelJVasconcelos.jpeg");
		File file2 = new File("photos/ArchesNationalPark.JPG");
		File file3 = new File("photos/AnelJVasconcelos.jpeg");
		IPhoto photo1 = PhotoFactory.INSTANCE.createPhoto(file1.getName(), file1.getPath());
		IPhoto photo2 = PhotoFactory.INSTANCE.createPhoto(file2.getName(), file2.getPath());
		IPhoto photo3 = PhotoFactory.INSTANCE.createPhoto(file3.getName(), file3.getPath());
		assertTrue(photo1.equals(photo3));
		assertEquals(photo1, photo3);
		assertFalse(photo1.equals(photo2));
		assertFalse(photo3.equals(photo2));
	}

	@Test
	void testHashCode() throws FileNotFoundException {
		File file1 = new File("photos/AnelJVasconcelos.jpeg");
		File file2 = new File("photos/AnelJVasconcelos.jpeg");
		IPhoto photo1 = PhotoFactory.INSTANCE.createPhoto(file1.getName(), file1.getPath());
		IPhoto photo2 = PhotoFactory.INSTANCE.createPhoto(file2.getName(), file2.getPath());
		assertEquals(photo1.hashCode(), photo2.hashCode());
	}

	// COMPLETE ME

}