package ch.fhnw.swc.mrs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.fhnw.swc.mrs.data.DbMRSServices;

public class ITBill {
    
	Movie m;
	User u;
    ByteArrayOutputStream baos;
    PrintStream outStream;
    
    /**
     * Create a movie valid movie.
     * Create a valid user.
     * Redirect standard out to baos and save original for backup.
     */
    @Before
    public void setup() {
		m = new Movie();
		m.setTitle("Avatar");
		m.setAgeRating(3);
		m.setPriceCategory(RegularPriceCategory.getInstance());
	    
	    u = new User("Jenny", "Zoë", LocalDate.of(1974, 3, 16));

	    // redirect system.out to be able to verify the correct output
	    outStream = System.out;
	    baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos, true);
	    System.setOut(ps);
	}
    
    /**
     * Restore redirected system.out.
     */
    @After
    public void teardown() {
    	System.setOut(outStream);
    }
	
    /**
     * Test correct order of string parameters.
     * Expect correct order as in test with lines[2].
     */
	@Test
	public void testPrintRegularUsername() {
		DbMRSServices dbservice = new DbMRSServices();
		dbservice.init();
		assertTrue(dbservice.createRental(u, m));
		
		String statement = baos.toString();
		String[] lines = statement.split("\\n");
		assertEquals("Statement", lines[0]);
		assertEquals("=========", lines[1]);
		assertEquals("for: Zoë Jenny", lines[2]);
		assertEquals("", lines[3]);
        assertEquals("Days   Price  Title", lines[4]);
        assertEquals("-------------------", lines[5]);
        assertEquals("   0    0.00  Avatar", lines[6]);
	}

	/**
	 * Test length of first name argument.
	 * Expect that nothing has been changed on the db.
	 */
	@Test
	public void testPrintLongFirstNameUsername() {
		DbMRSServices dbservice = new DbMRSServices();
		dbservice.init();
	    User longFN = new User("Tolkien", "John Ronald Reuel", LocalDate.of(1892, 1, 3));   

		assertFalse(dbservice.createRental(longFN, m));
	}

	/**
	 * Test length of last name argument.
	 * Expect that nothing has been changed on the db.
	 */
	@Test
	public void testPrintLongNameUsername() {
		DbMRSServices dbservice = new DbMRSServices();
		dbservice.init();
	    User longN = new User("de Cervantes Saavedra", "Miguel", LocalDate.of(1547, 9, 29));   

		assertFalse(dbservice.createRental(longN, m));
	}

	/**
	 * Test null argument for rental list.
	 * Does NullPointerException show in the caller? Or is it caught
	 * in the callee and a boolean value is returned?
	 */
	@Test
	public void testPrintNoRentals() {
		DbMRSServices dbservice = new DbMRSServices();
		dbservice.init();

		assertFalse(dbservice.createRental(u, null));
		
	}

}
