import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.zip.CRC32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArraySearchTest {

    private static final int ARRAY_SEARCH_ROUNDS = 3000;

    private static final int BINARY_SEARCH_ROUNDS = 3000;

    @Test public void testLinearSearch() {
        testArraySearch(0, 2544970263L);
    }

    @Test public void testSentinelSearch() {
        testArraySearch(1, 2544970263L);
    }

    @Test public void testUnrolledSearch() {
        testArraySearch(2, 2544970263L);
    }

    private void testArraySearch(int mode, long expected) {
        Random rng = new Random(12345 + ARRAY_SEARCH_ROUNDS);
        CRC32 check = new CRC32();
        for(int round = 0; round < ARRAY_SEARCH_ROUNDS; round++) {
            int m = 2*round + 1;
            int[] allItems = new int[m];
            for(int j = 0; j < m; j++) {
                allItems[j] = rng.nextInt(10*m) - m/2;
            }
            int[] items = Arrays.copyOfRange(allItems, 0, m/2);
            for(int x: allItems) {
                int result;
                if (mode == 0) {
                    result = ArraySearchDemo.linearSearch(items, x);
                } else if (mode == 1) {
                    result = ArraySearchDemo.sentinelSearch(items, x);
                }
                else {
                    result = ArraySearchDemo.unrolledSearch(items, x);
                }
                check.update(result);
            }
        }
        assertEquals(expected, check.getValue());
    }

    @Test public void testBinarySearch() {
        Random rng = new Random(12345);
        for(int round = 0; round < BINARY_SEARCH_ROUNDS; round++) {
            int m = 2 * round;
            int[] allItems = new int[m];
            for (int j = 0; j < m; j++) {
                allItems[j] = rng.nextInt(10 * m) - m / 2;
            }
            int[] items = Arrays.copyOfRange(allItems, 0, m / 2);
            Arrays.sort(items);
            for (int x : allItems) {
                int idx = ArraySearchDemo.binarySearch(items, x);
                if(idx < items.length) {
                    assertTrue(x <= items[idx]);
                }
                if(idx > 0) {
                    assertTrue(x > items[idx-1]);
                }
                if(idx < items.length - 1) {
                    assertTrue(x <= items[idx+1]);
                }
            }
        }
    }
}
