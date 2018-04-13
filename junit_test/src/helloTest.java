import static org.junit.Assert.*;
import org.junit.Test;


/**
 * helloTest
 */
public class helloTest {
    @Test
    public void testHello() {
        assertEquals("Hello!", hello.getHello());
    }

    @Test
    public void testNothing() {}

    @Test
    public void testFail() {
        fail("No reason");
    }
}