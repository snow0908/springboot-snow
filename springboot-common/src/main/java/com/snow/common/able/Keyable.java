package com.snow.common.able;

/**
 * 具有主键性质的接口，类似<code>DB</code>表中的<code>id</code>
 *
 * @param <T>
 *
 * @author <a href="#">yanhua</a>
 * @version create on 2014年7月19日 上午2:30:48
 */
public interface Keyable<T> {

    public T getId();

}
