package cz.mirek.reflection.domain;

import javafx.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

public class HashBucketTest {
    private static final String BUCKET_PREFIX = "BUCKET_";

    @Test
    public void shouldDistributeEvenly() {
        int buckets = 10;
        int intervalSize = Integer.MAX_VALUE / (buckets / 2);
        List<Pair<Integer, Integer>> bucketIntervals = new ArrayList<>(buckets);
        Map<String, List<String>> bucketsValues = new HashMap<>();

        IntStream.range(0, buckets)
                .forEach(i -> {
                    int start = Integer.MIN_VALUE + intervalSize * i;
                    int end = Integer.MIN_VALUE + intervalSize * (i + 1) - 1;

                    Pair<Integer, Integer> interval = new Pair<>(start, end);
                    bucketIntervals.add(interval);
                    bucketsValues.put(BUCKET_PREFIX + i, new LinkedList<>());
                });

        IntStream.range(0, 1_000_000)
                .forEach(i -> {
                    UUID uuid = UUID.randomUUID();
                    String name = "mirek" + i + Math.random() + "_" + uuid.toString();
                    int bucket = findBucket(name.hashCode(), bucketIntervals);

                    bucketsValues.get(BUCKET_PREFIX + bucket).add(name);
                });

        bucketsValues.forEach((k, v) -> {
            System.out.println(k + " has " + v.size() + " values!");
        });

    }

    private int findBucket(int hashCode, List<Pair<Integer, Integer>> bucketIntervals) {
        for (int i = 0; i < bucketIntervals.size(); i++) {
            Pair<Integer, Integer> pair = bucketIntervals.get(i);
            if (pair.getKey() <= hashCode && pair.getValue() >= hashCode) {
                return i;
            }
        }
        return 0;
    }
}
