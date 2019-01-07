package io.github.xiaobogaga.list;

/**
 * a exception represent not supported action. like {@link UnsupportedOperationException}
 *
 * @author tomzhu
 * @since 1.7
 */
public class MyNotSupportException extends Exception{

    public MyNotSupportException(String message) {
        super(message);
    }

}
