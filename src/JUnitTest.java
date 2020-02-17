import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class JUnitTest {
	 private static ListaDoblementeEnlazada listaDoblementeEnlazadaVacia;
	    private static ListIterator iterator;
	    private static Iterator descendingIterator;

    @Before
    public void setUp()
    {
    	listaDoblementeEnlazadaVacia = new ListaDoblementeEnlazada();
    }
	 @Test
	 public void testListaNula()
	 {
		 assertEquals(null, listaDoblementeEnlazadaVacia);
	 }

}
