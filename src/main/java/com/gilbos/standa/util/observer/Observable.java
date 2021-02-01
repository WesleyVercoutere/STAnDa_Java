package com.gilbos.standa.util.observer;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Observable {
    /**
     * Tracks whether this object has changed.
     */
    private boolean changed;
    /* List of the Observers registered as interested in this Observable. */
    private LinkedHashSet observers;

    /**
     * Constructs an Observable with zero Observers.
     */
    public Observable() {
        observers = new LinkedHashSet();
    }

    /**
     * Adds an Observer. If the observer was already added this method does
     * nothing.
     *
     * @param observer Observer to add
     * @throws NullPointerException if observer is null
     */
    public synchronized void addObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException("can't add null observer");
        observers.add(observer);
    }

    /**
     * Reset this Observable's state to unchanged. This is called automatically
     * by <code>notifyObservers</code> once all observers have been notified.
     *
     * @see #notifyObservers()
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Returns the number of observers for this object.
     *
     * @return number of Observers for this
     */
    public synchronized int countObservers() {
        return observers.size();
    }

    /**
     * Deletes an Observer of this Observable.
     *
     * @param victim Observer to delete
     */
    public synchronized void deleteObserver(Observer victim) {
        observers.remove(victim);
    }

    /**
     * Deletes all Observers of this Observable.
     */
    public synchronized void deleteObservers() {
        observers.clear();
    }

    /**
     * True if <code>setChanged</code> has been called more recently than
     * <code>clearChanged</code>.
     *
     * @return whether or not this Observable has changed
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * If the Observable has actually changed then tell all Observers about it,
     * then reset state to unchanged.
     *
     * @see #notifyObservers(Object)
     * @see Observer#update(Observable, Object)
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If the Observable has actually changed then tell all Observers about it,
     * then reset state to unchanged. Note that though the order of
     * notification is unspecified in subclasses, in Observable it is in the
     * order of registration.
     *
     * @param obj argument to Observer's update method
     * @see Observer#update(Observable, Object)
     */
    public void notifyObservers(Object obj) {
        if (!hasChanged())
            return;
        // Create clone inside monitor, as that is relatively fast and still
        // important to keep threadsafe, but update observers outside of the
        // lock since update() can call arbitrary code.
        Set s;
        synchronized (this) {
            s = (Set) observers.clone();
        }
        int i = s.size();
        Iterator iter = s.iterator();
        while (--i >= 0)
            ((Observer) iter.next()).update(this, obj);
        clearChanged();
    }

    /**
     * Marks this Observable as having changed.
     */
    protected synchronized void setChanged() {
        changed = true;
    }
}