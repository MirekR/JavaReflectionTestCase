package cz.mirek.reflection.domain;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DefaultOverridesTest {
    private final String firstNameLocal = "Cthulhu";
    private final String lastNameLocal = "Great Old One";
    private final int ageLocal = 5_000_000;

    @Test
    public void shouldCompareTwoObjects() {
        DataObject mirek1 = this.getDataObjectWithSuffix("");
        DataObject mirek2 = this.getDataObjectWithSuffix("");

        DataObjectStandardEquals mirek3 = this.getDataObjectStandardEqualsWithSuffix("");
        DataObjectStandardEquals mirek4 = this.getDataObjectStandardEqualsWithSuffix("");

        assertThat(mirek1, is(mirek2));
        assertThat(mirek3, is(mirek4));
    }

    @Test
    public void shouldHaveDifferentPerformanceAsCompilareOptimalizations() {
        DataObject mirek1 = this.getDataObjectWithSuffix("");
        DataObject mirek2 = this.getDataObjectWithSuffix("");

        DataObjectStandardEquals mirek3 = this.getDataObjectStandardEqualsWithSuffix("");
        DataObjectStandardEquals mirek4 = this.getDataObjectStandardEqualsWithSuffix("");

        int range = 1_000_000;
        long startTimeGeneric = System.currentTimeMillis();
        IntStream.range(1, range)
                .forEach(i -> {
                    assertThat(mirek1, is(mirek2));
                });
        long stopTimeGeneric = System.currentTimeMillis();
        long genericTime = stopTimeGeneric - startTimeGeneric;

        long startTimeStandard = System.currentTimeMillis();
        IntStream.range(1, range)
                .forEach(i -> {
                    assertThat(mirek3, is(mirek4));
                });
        long stopTimeStandard = System.currentTimeMillis();
        long standardTime = stopTimeStandard - startTimeStandard;

        double maxTimeDelayPercents = 1.10;
        long diffTime = (long) (genericTime - (standardTime * maxTimeDelayPercents));

        //assertThat(diffTime, lessThan(0L));
        assertThat(standardTime, is(genericTime));
    }

    private DataObject getDataObjectWithSuffix(String suffix) {
        return new DataObject()
                .setAge(ageLocal)
                .setLastName(lastNameLocal)
                .setFirstName(firstNameLocal + "_" + suffix);
    }

    private DataObjectStandardEquals getDataObjectStandardEqualsWithSuffix(String suffix) {
        return new DataObjectStandardEquals()
                .setAge(ageLocal)
                .setLastName(lastNameLocal)
                .setFirstName(firstNameLocal + "_" + suffix);
    }

    @Test
    public void shouldHaveSimilarPerformance() {
        int range = 10_000_000;
        long startTimeGeneric = System.currentTimeMillis();
        IntStream.range(1, range)
                .forEach(i -> {
                    String suffix = String.valueOf(System.currentTimeMillis());
                    DataObject mirek1 = this.getDataObjectWithSuffix(suffix);
                    DataObject mirek2 = this.getDataObjectWithSuffix(suffix);

                    assertThat(mirek1, is(mirek2));
                });
        long stopTimeGeneric = System.currentTimeMillis();
        long genericTime = stopTimeGeneric - startTimeGeneric;

        long startTimeStandard = System.currentTimeMillis();
        IntStream.range(1, range)
                .forEach(i -> {
                    String suffix = String.valueOf(System.currentTimeMillis());
                    DataObjectStandardEquals mirek3 = this.getDataObjectStandardEqualsWithSuffix(suffix);
                    DataObjectStandardEquals mirek4 = this.getDataObjectStandardEqualsWithSuffix(suffix);


                    assertThat(mirek3, is(mirek4));
                });
        long stopTimeStandard = System.currentTimeMillis();
        long standardTime = stopTimeStandard - startTimeStandard;

        double maxTimeDelayPercents = 1.10;
        long diffTime = (long) (genericTime - (standardTime * maxTimeDelayPercents));

        //assertThat(diffTime, lessThan(0L));
        assertThat(genericTime, lessThan((long)(standardTime * 3.0)));
    }
}