package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.IPhoto;

class RecentlyDeletedLibraryTest {

	private static final int SECONDS_IN_TRASH = 16; // CHANGE ME
	private static final int SECONDS_TO_CHECK = 5; // CHANGE ME

	private RecentlyDeletedLibrary library;

	@BeforeEach
	void setUp() {
		library = new RecentlyDeletedLibrary();
	}

	@Test
	void testAddPhoto() {
		MockPhoto photo = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		assertTrue(library.addPhoto(photo));
		assertTrue(library.getPhotos().contains(photo));
		// assertTrue(library.getPhotos().contains(photo));
		assertEquals(1, library.getNumberOfPhotos());
	}

	@Test
	void testAddExistingPhoto() {
		MockPhoto photo = new MockPhoto(new File("AnelJVasconcelos.jpeg"));

		// COMPLETE ME
	}

	@Test
	void testDeletePhoto() {
		MockPhoto photo = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		library.addPhoto(photo);
		assertTrue(library.deletePhoto(photo));

	}

	@Test
	void testDeleteNotExistingPhoto() {
		MockPhoto photo1 = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		MockPhoto photo2 = new MockPhoto(new File("Bean.jpeg"));
		library.addPhoto(photo1);
		assertFalse(library.deletePhoto(photo2));
	}

	@Test
	void testDeleteAll() {
		MockPhoto photo1 = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		MockPhoto photo2 = new MockPhoto(new File("Bean.jpeg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		assertTrue(library.deleteAll());
		assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testGetMatchesEmpty() {
		Collection<IPhoto> matches = library.getMatches(".*");
		assertNotNull(matches);
		assertEquals(0, matches.size());
	}

	@Test
	void testGetMatchesNotEmpty() {
		MockPhoto photoY = new MockPhoto(new File("AnelJVasconcelos.jpeg"), true);
		MockPhoto photoN = new MockPhoto(new File("Bean.jpeg"), false);
		library.addPhoto(photoY);
		library.addPhoto(photoN);
		Collection<IPhoto> matches = library.getMatches(".*");
		assertNotEquals(0, matches.size());
	}

	@Test
	void testAutomaticDelete() throws InterruptedException {
		MockPhoto photo1 = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		MockPhoto photo2 = new MockPhoto(new File("Bean.jpeg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_IN_TRASH * 2000);
		Collection<IPhoto> photos = library.getPhotos();
		assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testAutomaticDeleteNoEffectTooSoon() {
		MockPhoto photo1 = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		MockPhoto photo2 = new MockPhoto(new File("Bean.jpeg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Collection<IPhoto> photos = library.getPhotos();
		assertEquals(2, library.getNumberOfPhotos());
	}

	@Test
	void testAutomaticDeleteNoEffectCheckedJustBefore() throws InterruptedException {
		MockPhoto photo1 = new MockPhoto(new File("AnelJVasconcelos.jpeg"));
		MockPhoto photo2 = new MockPhoto(new File("Bean.jpeg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_TO_CHECK * 1000);
		Collection<IPhoto> photos = library.getPhotos();
		assertEquals(2, library.getNumberOfPhotos());
	}

}