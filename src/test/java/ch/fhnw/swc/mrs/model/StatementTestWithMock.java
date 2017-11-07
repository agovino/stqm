package ch.fhnw.swc.mrs.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * This class demonstrates how to test abstract classes
 * using Mock frameworks. 
 *
 */
public class StatementTestWithMock {

	private Statement s;
	private List<Rental> rentals;

	@Before
	public void setup() {
		Rental r1 = mock(Rental.class);
		Rental r2 = mock(Rental.class);
		Rental r3 = mock(Rental.class);

		rentals = new ArrayList<>(3);
		rentals.add(r1);
		rentals.add(r2);
		rentals.add(r3);
	}

	@Test
	public void testStatement() {
		s = mock(Statement.class,
				withSettings().useConstructor("Muster", "Hans", rentals).defaultAnswer(CALLS_REAL_METHODS));
		assertEquals("Muster", s.getLastName());
		assertEquals("Hans", s.getFirstName());
		assertEquals(3, s.getRentals().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFirstName() throws Throwable {
		try {
			s = mock(Statement.class,
					withSettings().useConstructor("Muster", "Maximilian", rentals).defaultAnswer(CALLS_REAL_METHODS));
		} catch (Exception e) {
			throw ExceptionUtils.getRootCause(e);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLastName() throws Throwable {
		try {
			s = mock(Statement.class,
					withSettings().useConstructor("Mustermann", "Hans", rentals).defaultAnswer(CALLS_REAL_METHODS));
		} catch (Exception e) {
			throw ExceptionUtils.getRootCause(e);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRentals() throws Throwable {
		try {
			s = mock(Statement.class, withSettings().useConstructor("Muster", "Hans", null).defaultAnswer(CALLS_REAL_METHODS));
		} catch (Exception e) {
			throw ExceptionUtils.getRootCause(e);
		}
	}


}
