/**
 * Demonstrate the basic array searching algorithms.
 * @author Ilkka Kokkarinen
 */

public class ArraySearchDemo {

    /**
     * Unoptimized linear search.
     * @param a The array to search the value in.
     * @param x The element to search for.
     * @return The first index in which the element is found,
     * or -1 if that element does not exist in the array. 
     */
    public static int linearSearch(int[] a, int x) {
        // We use a while-loop here instead of a foreach-loop to make individual steps clear.
        int i = 0;
        while(i < a.length && a[i] != x) { i++; }
        // Condition to create the final answer.
        return i < a.length ? i : -1;
    }

    /**
     * Sentinel search optimizes away one comparison per array element.
     * @param a The array to search the value in.
     * @param x The element to search for.
     * @return The first index in which the element is found,
     * or -1 if that element does not exist in the array. 
     */
    public static int sentinelSearch(int[] a, int x) {
        // Can't forget this important edge case.
        if(a.length == 0) { return -1; }
        // Take the original last element for safekeeping.
        int last = a[a.length - 1];
        // Place the element to look for as sentinel to the end of array.
        a[a.length - 1] = x;
        // Iterate through the array, but this time without a bounds check.
        int i = 0;
        while(a[i] != x) { i++; }
        // Restore the original lasy element in place.
        a[a.length - 1] = last;
        // Careful with the edge case in the condition.
        return (i < a.length - 1 || last == x) ? i : -1;
    }

    /**
     * Unrolled search to halve the number of bounds checks.
     * @param a The array to search the value in.
     * @param x The element to search for.
     * @return The first index in which the element is found,
     * or -1 if that element does not exist in the array. 
     */
    public static int unrolledSearch(int[] a, int x) {
        int i = 0;
        // Handle the first element in a special way.
        if(a.length % 2 == 1) { // odd man out
            if(a[i++] == x) { return 0; }
        }
        // Two elements compared in each round, saving half of bounds checks.
        while(i < a.length) {
            if(a[i++] == x) { return --i; }
            if(a[i++] == x) { return --i; }
        }
        return -1;
    }

    /**
     * The classic binary search for sorted arrays.
     * @param a The array to search the value in.
     * @param x The element to search for.
     * @return The first index in which the element is found. If the element
     * {@code x} is not found, returns the index where that element would go
     * to keep array in sorted order. If {@code x} is larger than the current
     * largest element of the array, returns {@code a.length} as special case.
     */
    public static int binarySearch(int[] a, int x) {
        // Invariant: the desired position is always between indices lo and hi, inclusive.
        int lo = 0, hi = a.length - 1;
        // Handle the search for element larger than last element as special case.
        if(hi == -1 || a[hi] < x) {
            return a.length;
        }
        // As long as indices have not met, move one of them depending on midpoint element comparison.
        while(lo < hi) {
            // Midpoint element position: notice the asymmetry lo <= mid < hi.
            int mid = (lo + hi) / 2;
            // Due to asymmetry, moves of lo and hi indices are asymmetric also.
            if(a[mid] < x) {
                lo = mid + 1; // Middle element is too small, move lo over it.
            }
            else {
                hi = mid; // Middle element is greater of equal, move hi into it.
            }
        }
        // The leftmost position where the element x could occur in a sorted array.
        return lo;
    }

    // A common but incorrect version of binary search.

    public static int binarySearchIncorrect(int[] a, int x) {
        // Invariant: the desired position is always between indices lo and hi, inclusive.
        int lo = 0, hi = a.length - 1;
        // Handle the search for element larger than last element as special case.
        if(hi == -1 || a[hi] < x) {
            return a.length;
        }
        // As long as indices have not met, move one of them depending on midpoint element comparison.
        while(lo < hi) {
            // Midpoint element position: notice the asymmetry lo <= mid < hi.
            int mid = (lo + hi) / 2;
            // Clever "optimization" to terminate early when the element is found.
            if(a[mid] == x) {
                // Who gives a crap about returning the leftmost position of x. I mean,
                //the authors of Arrays.binarySearch of Java did not. And still don't.
                return mid;
            }
            // Due to asymmetry, moves of lo and hi indices are asymmetric.
            if(a[mid] < x) {
                lo = mid + 1; // Middle element is too small, move lo over it.
            }
            else {
                // Yay! We made the algorithm faster by moving hi one step more!
                hi = mid - 1; // Middle element is too big, move hi over it.
            }
        }
        // Frankly, who even cares at this point.
        return lo;
    }
}