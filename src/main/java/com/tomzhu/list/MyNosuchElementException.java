package com.tomzhu.list;

/**
 * a exception represent no such element. like {@link java.util.NoSuchElementException}
 *
 * @author tomzhu
 * @since 1.7
 */
public class MyNosuchElementException extends Exception{

    public MyNosuchElementException(String message) {
        super(message);
    }

}
