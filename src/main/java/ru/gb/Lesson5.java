package ru.gb;

import static sun.jvm.hotspot.runtime.PerfMemory.start;

public class Lesson5 {

    static final int size = 10_000_000;
    static final int HALF = size / 2;


    public static void methodOne() {
        float[] arr1 = new float[size];

        for (int i = 0; i < size; i++) {
            arr1[i] = 1.0f;
        }
        long timeStart = System.currentTimeMillis();
        for (int j = 0; j < arr1.length; j++) {

            arr1[j] = (float) (arr1[j] * Math.sin(0.2f + j / 5f) * Math.cos(0.2f + j / 5f) * Math.cos(0.4f + j / 2f));
        }
        long timeOne = System.currentTimeMillis() - timeStart;

        System.out.println("time for methodOne: " + timeOne);
        System.out.println("Element_methodOne_1000: " + arr1[1_000]);
        System.out.println("Element_methodOne_555000: " + arr1[555_000]);
    }

    public static void methodTwo() {

        float[] arr2 = new float[size];

        for (int i = 0; i < size; i++) {
            arr2[i] = 1.0f;
        }
        System.out.println("до расчетов: arr2_1000 " + arr2[1000]);

        long timeStart2 = System.currentTimeMillis();


        float[] arrLeft = new float[HALF];
        float[] arrRight = new float[HALF];

        System.arraycopy(arr2, 0, arrLeft, 0, HALF);
        System.arraycopy(arr2, HALF, arrRight, 0, HALF);
        System.out.println("Element_methodTwo_1000: arrLeft " + arrLeft[1_000]);
        System.out.println("Element_methodTwo_4545000: arrRight " + arrRight[4545_000]);

        Thread thread1 = new Thread(() -> {

            for (int j = 0; j < HALF; j++) {

                arrLeft[j] = (float) (arrLeft[j] * Math.sin(0.2f + j / 5f) * Math.cos(0.2f + j / 5f) * Math.cos(0.4f + j / 2f));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int j = 0; j < HALF; j++) {

                arrRight[j] = (float) (arrRight[j] * Math.sin(0.2f + j / 5f) * Math.cos(0.2f + j / 5f) * Math.cos(0.4f + j / 2f));
            }
        });

        thread1.start();
        thread2.start();

        System.out.println("после первого потока ");
        System.out.println("arr2_1000 " + arr2[1000]);
        System.out.println("arr2_555000" + arr2[500]);

        System.arraycopy(arrLeft, 0, arr2, 0, HALF);
        System.arraycopy(arrRight, 0, arr2, HALF, HALF);

        System.out.println("после первого потока ");
        System.out.println("arr2_1000 " + arr2[1000]);
        System.out.println("arr2_555000" + arr2[500]);


        long timeTwo = System.currentTimeMillis() - timeStart2;

        System.out.println("time for methodTwo: " + timeTwo);
        System.out.println("Element_methodTwo_1000: " + arr2[1_000]);
        System.out.println("Element_methodTwo_555000: " + arr2[555_000]);

    }

    public static void main(String[] args) {

        methodOne();
        methodTwo();
    }

}