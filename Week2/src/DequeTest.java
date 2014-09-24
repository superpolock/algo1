import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.NullPointerException;

public class DequeTest {
	
	private Deque<Integer> deque;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		deque = new Deque<Integer>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsEmpty() {
		assertTrue( deque.isEmpty());
	}

	@Test
	public void testSize() {
		assertTrue( deque.size() == 0);
	}

	@Test
	public void testAddFirst() {
		deque.addFirst(2);
		assertTrue( deque.size() == 1);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddNull() {
		deque.addFirst(null);
	}

	@Test
	public void testAddLast() {
		deque.addLast(3);
		assertTrue( deque.size() == 1);
		assertTrue( deque.removeFirst() == 3);
	}

	@Test
	public void testRemoveFirst() {
		deque.addLast(4);
		deque.addLast(5);
		assertTrue( 4 == deque.removeFirst());
	}

	@Test
	public void testRemoveLast() {
		deque.addFirst(6);
		deque.addFirst(7);
		assertTrue(false);
		assertTrue( 6 == deque.removeLast());
	}

	@Test
	public void testIterator() {
		deque.addFirst(8);
		Iterator<Integer> it = deque.iterator();
		assertTrue( 8 == it.next() );
		assertTrue( 0 == deque.size() );
		assertTrue( deque.isEmpty());
	}

	@Test
	public void testConfirmAddFirst() {
		deque.addFirst(9);
		assertTrue( 9 == deque.removeLast() );
		deque.addFirst(10);
		deque.addFirst(11);
		assertTrue( 11 == deque.removeFirst() );
		assertTrue( 10 == deque.removeFirst() );
	}

	@Test
	public void testConfirmAddLast() {
		deque.addLast(12);
		deque.addLast(15);
		assertTrue( 12 == deque.removeFirst() );
		deque.addFirst(13);
		deque.addFirst(14);
		deque.addFirst(15);
		assertTrue( 14 == deque.removeFirst() );
		assertTrue( 13 == deque.removeFirst() );
	}
}
