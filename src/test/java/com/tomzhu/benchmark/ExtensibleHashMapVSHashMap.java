package com.tomzhu.benchmark;

import com.tomzhu.hash.ExtensibleHashMap;

import java.util.HashMap;

/**
 * @author tomzhu.
 */
public class ExtensibleHashMapVSHashMap {

    public static void main(String args[]) {
        // a benchmark for extensible hash map and standard java HashMap
        HashMap<Integer, Integer> hashMap = null;
        ExtensibleHashMap<Integer, Integer> extensibleHashMap = null;
        int testTime = 10;
        int size = 100;

        int i = 0, j = 0, limit = 1000000, sep = 10000;
        StringBuilder total = new StringBuilder();
        size = 10000;
        while (size <= limit) {
            hashMap = new HashMap<Integer, Integer>();
            extensibleHashMap = new ExtensibleHashMap<Integer, Integer>();
            int[] arr = new int[size];
            StringBuilder string1 = new StringBuilder();
            StringBuilder string2 = new StringBuilder();
            // generate all input;
            i = 0;
            while (i < size) {
                arr[i++] = (int) (Math.random() * 1000000000);
            }
            // first, test insert.
            string1.append("hashMap:\ninsert:");
            string2.append("extensibleHashMap:\ninsert");
            j = 0;
            while (j < testTime) {
                i = 0;
                string1.append("\nhashMap: ");
                string2.append("\nextensibleHashMap: ");
                long t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        hashMap.put(arr[i], arr[i]);
                        string1.append( (System.currentTimeMillis() - t) );
                        t = System.currentTimeMillis();
                    } else
                        hashMap.put(arr[i], arr[i]);
                    i ++;
                }

                i = 0;
                t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        extensibleHashMap.insert(arr[i], arr[i]);
                        string2.append(System.currentTimeMillis() - t);
                        t = System.currentTimeMillis();
                    } else
                        extensibleHashMap.insert(arr[i], arr[i]);
                    i ++;
                }
                j ++;
            }

            // Second, test contains.
            string1.append("\ncontains:");
            string2.append("\ncontains:");
            j = 0;
            while (j < testTime) {
                string1.append("\nhashMap: ");
                string2.append("\nextensibleHashMap: ");
                i = 0;
                long t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        hashMap.containsKey(arr[i]);
                        string1.append(System.currentTimeMillis() - t);
                        t = System.currentTimeMillis();
                    } else
                        hashMap.containsKey(arr[i]);
                    i ++;
                }

                i = 0;
                t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        extensibleHashMap.contains(arr[i]);
                        string2.append(System.currentTimeMillis() - t);
                        t = System.currentTimeMillis();
                    } else
                        extensibleHashMap.contains(arr[i]);
                    i ++;
                }
                j ++;
            }

            // third, test delete.
            string1.append("\ndelete:");
            string2.append("\ndelete");
            j = 0;
            while (j < testTime) {
                i = 0;
                string1.append("\nhashMap: ");
                string2.append("\nextensibleHashMap: ");
                long t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        hashMap.remove(arr[i]);
                        string1.append(System.currentTimeMillis() - t);
                        t = System.currentTimeMillis();
                    } else
                        hashMap.remove(arr[i]);
                    i ++;
                }

                i = 0;
                t = System.currentTimeMillis();
                while (i < size) {
                    if (i > 0 && (i + 1) % sep == 0) {
                        // calculating the time.
                        extensibleHashMap.remove(arr[i]);
                        string2.append(System.currentTimeMillis() - t);
                        t = System.currentTimeMillis();
                    } else
                        extensibleHashMap.remove(arr[i]);
                    i ++;
                }
                j ++;
            }
            string1.append("\n");
            string2.append("\n");
            total.append(string1).append(string2);
            size *= 10;
            sep *= 10;
        }
        System.out.println(total);
        System.out.println("testing string");


    }



}
