import org.junit.Test;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShlemielTest {

    private static final int SEED = 12345;

    @Test public void testAccumulate() {
        Random rng = new Random(SEED);
        for(int n = 0; n < 1000; n++) {
            int[] items = new int[n];
            for(int j = 0; j < n; j++) {
                items[j] = rng.nextInt(2*n + 1) - n;
            }
            int[] result1 = Shlemiel.accumulateShlemiel(items);
            int[] result2 = Shlemiel.accumulate(items);
            assertArrayEquals(result1, result2);
        }
    }

    @Test public void testLongestAscendingSubarray() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 1000; n++) {
            int[] items = new int[n];
            items[0] = rng.nextInt(10 * n);
            for (int j = 1; j < n; j++) {
                if (rng.nextInt(100) < 80) {
                    items[j] = items[j - 1] + rng.nextInt(0, n);
                } else {
                    items[j] = rng.nextInt(10 * n);
                }
            }
            int result1 = Shlemiel.longestAscendingSubarrayShlemiel(items);
            int result2 = Shlemiel.longestAscendingSubarray(items);
            assertEquals(result1, result2);
        }
    }

    @Test public void testTwoSummingElements() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 500; n++) {
            int[] items = new int[n];
            items[0] = rng.nextInt(10);
            for(int j = 1; j < n; j++) {
                items[j] = items[j-1] + 1 + rng.nextInt(n*n);
            }
            for(int j = 0; j < 10; j++) {
                int goal = rng.nextInt(items[n-1] + items[n/2]);
                boolean result1 = Shlemiel.twoSummingElementsShlemiel(items, goal);
                boolean result2 = Shlemiel.twoSummingElements(items, goal);
                assertEquals(result1, result2);
            }
        }
    }

    @Test public void testEvaluatePolynomial() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 10000; n++) {
            int m = 1 + n/2000;
            double[] coefficients = new double[m];
            for(int i = 0; i < m; i++) {
                coefficients[i] = rng.nextDouble() * 10 - 5;
            }
            for(int i = 0; i < 10; i++) {
                double x = rng.nextDouble() * 6 - 3;
                double result1 = Shlemiel.evaluatePolynomialShlemiel(coefficients, x);
                double result2 = Shlemiel.evaluatePolynomialLinear(coefficients, x);
                double result3 = Shlemiel.evaluatePolynomialHorner(coefficients, x);
                assertTrue(Math.abs(result1 - result2) < 0.00001);
                assertTrue(Math.abs(result2 - result3) < 0.00001);
            }
        }
    }

    @Test public void testContainsAllNumbers() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 1000; n++) {
            int[] a = new int[n];
            for(int j = 0; j < 10; j++) {
                for(int i = 0; i < n; i++) {
                    a[i] = i+1;
                }
                for(int i = 1; i < n; i++) {
                    int k = rng.nextInt(i);
                    int tmp = a[i]; a[i] = a[k]; a[k] = tmp;
                }
                if(rng.nextBoolean()) {
                    a[rng.nextInt(n)] = rng.nextInt(n) + 1;
                }
                boolean result1 = Shlemiel.containsAllNumbersShlemiel(Arrays.copyOf(a, a.length), n);
                boolean result2 = Shlemiel.containsAllNumbersSorting(Arrays.copyOf(a, a.length), n);
                boolean result3 = Shlemiel.containsAllNumbersLinear(Arrays.copyOf(a, a.length), n);
                assertEquals(result1, result2);
                assertEquals(result2, result3);
            }
        }
    }

    private static String[] words = new String[1000];

    static {
        StringBuilder word = new StringBuilder();
        Random rng = new Random(SEED);
        for(int i = 0; i < words.length; i++) {
            word.append((char) rng.nextInt(1000) + 32);
            words[i] = word.toString();
        }
    }

    @Test public void testRemoveShortStrings() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 1000; n++) {
            ArrayList<String> strings = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                strings.add(words[rng.nextInt(words.length)]);
            }
            int len = rng.nextInt(words.length);
            ArrayList<String> copy = new ArrayList<>(strings);
            Shlemiel.removeShortStringsShlemiel(strings, len);
            Shlemiel.removeShortStrings(copy, len);
            assertEquals(strings, copy);
        }
    }

    @Test public void testHasMajority() {
        Random rng = new Random(SEED);
        for(int n = 1; n < 1000; n++) {
            int[] items = new int[n];
            int look = rng.nextInt(3) + 2;
            int take = rng.nextInt(90) + 10;
            for(int j = 0; j < n; j++) {
                if(j > look && rng.nextInt(100) < take) {
                    items[j] = items[j-1-rng.nextInt(look)];
                }
                else {
                    items[j] = rng.nextInt(n*n);
                }
            }
            int copies = rng.nextInt(n/2 + 1) + n/4;
            int item = items[rng.nextInt(n)];
            for(int j = 0; j < copies; j++) {
                int i = rng.nextInt(n);
                items[i] = item;
            }
            boolean result1 = Shlemiel.hasMajorityShlemiel(items);
            boolean result2 = Shlemiel.hasMajorityHashMap(items);
            boolean result3 = Shlemiel.hasMajorityLinear(items);
            assertEquals(result1, result2);
            assertEquals(result2, result3);
        }
    }
}