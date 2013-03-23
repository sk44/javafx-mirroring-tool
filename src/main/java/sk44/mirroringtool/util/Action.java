/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.util;

/**
 * Generic functional interface like Action delegate on C#.
 *
 * @author sk
 */
public interface Action<T> {

    /**
     * Encapsulates a method that has a single parameter and does not return a value.
     *
     * @param obj
     */
    void execute(T obj);
}
