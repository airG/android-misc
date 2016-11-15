/*
 * ****************************************************************************
 *   Copyright  2016 airG Inc.                                                 *
 *                                                                             *
 *   Licensed under the Apache License, Version 2.0 (the "License");           *
 *   you may not use this file except in compliance with the License.          *
 *   You may obtain a copy of the License at                                   *
 *                                                                             *
 *       http://www.apache.org/licenses/LICENSE-2.0                            *
 *                                                                             *
 *   Unless required by applicable law or agreed to in writing, software       *
 *   distributed under the License is distributed on an "AS IS" BASIS,         *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 *   See the License for the specific language governing permissions and       *
 *   limitations under the License.                                            *
 * ***************************************************************************
 */

package com.airg.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 An extension of the {@link HashMap} that also preservers the insertion order. This implementation allows for fast
 random access to the data as well as sequential access via an internal {@link java.util.ArrayList}.
 As this structure is based on a {@link HashMap}, it only keeps unique data values.

 @param <KEY>
 key type
 @param <VALUE>
 value type

 @author Mahram Z. Foadi */
@SuppressWarnings ( {"UnusedDeclaration", "WeakerAccess"})
public final class ArrayHash <KEY, VALUE> extends HashMap<KEY, VALUE> {
    /**
     default generated serial
     */
    private static final long serialVersionUID = 1L;
    private List<KEY> orderedKeys;

    /**
     **********************************************************************
     Constructor
     */
    public ArrayHash () {
        super ();
        orderedKeys = new ArrayList<>();
    }

    public ArrayHash (final int capacity, final float loadFactor) {
        super (capacity, loadFactor);
        orderedKeys = new ArrayList<> (capacity);
    }

    public ArrayHash (final int capacity) {
        super (capacity);
        orderedKeys = new ArrayList<> (capacity);
    }

    /**
     **********************************************************************
     Copy constructor

     @param original
     Original ArrayHash object to copy
     */
    public ArrayHash (final ArrayHash<KEY, VALUE> original) {
        super (original);

        if (original == null) {
            orderedKeys = new ArrayList<> ();
        } else {
            orderedKeys = new ArrayList<> (original.orderedKeys);
        }
    }

    /**
     **********************************************************************
     Put a key value pair in the end of this data structure. Null key or value
     will be ignored

     @param key
     Key
     @param value
     Value

     @return

     @throws NullPointerException
     if the key or value is null
     */
    public VALUE put (final KEY key, final VALUE value) throws NullPointerException {
        put (false, key, value);
        return value;
    }

    /**
     **********************************************************************
     Put a key value pair as the first item of this data structure. Null key
     or value will be ignored

     @param key
     Key
     @param value
     Value

     @throws NullPointerException
     if the key or value is null
     */
    public VALUE putHead (final KEY key, final VALUE value) throws NullPointerException {
        put (true, key, value);

        return value;
    }

    /**
     **********************************************************************
     Put a key value pair in this data structure. Null key or value will be
     ignored

     @param key
     Key
     @param value
     Value

     @throws NullPointerException
     if the key or value is null
     */
    private void put (final boolean head, final KEY key, final VALUE value) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException ("key");
        }
        if (value == null) {
            throw new NullPointerException ("value");
        }

        if (containsKey (key)) {
            orderedKeys.remove (key);
            remove (key);
        }

        super.put (key, value);

        if (head) {
            orderedKeys.add (0, key);
        } else {
            final int size = orderedKeys.size ();
            orderedKeys.add (size, key);
        }
    }

    /**
     inserts a key value pair at the given index

     @param idx
     index to insert at
     @param key
     key
     @param value
     value

     @throws IndexOutOfBoundsException
     if the index is negative or beyond the size of the array
     */
    public void putAtIndex (final int idx, final KEY key, final VALUE value) throws IndexOutOfBoundsException {
        if (idx < 0 || idx > size ()) {
            throw new IndexOutOfBoundsException ("Index " + idx + " is beyond the bounds of this array. Size: " +
                    size ());
        }

        if (key == null) {
            throw new NullPointerException ("key");
        }
        if (value == null) {
            throw new NullPointerException ("value");
        }

        if (containsKey (key)) {
            orderedKeys.remove (key);
            remove (key);
        }

        super.put (key, value);
        orderedKeys.add (idx, key);
    }

    /**
     *********************************************************************************
     Get a value by an index

     @param index
     The position of the value.

     @return The value of the given index.
     */
    public VALUE getAtIndex (final int index) {
        if ((index >= 0) && (index < orderedKeys.size ())) {
            return get (orderedKeys.get (index));
        }

        return null;
    }

    /**
     ****************************************************************************
     Remove a value based on the given key

     @param key
     key

     @return The removed value
     */
    @Override
    public VALUE remove (final Object key) {
        if (containsKey (key)) {
            VALUE value = super.remove (key);
            orderedKeys.remove (key);
            return value;
        }

        return null;
    }

    /**
     ****************************************************************************
     Remove a value. All (key, value) pairs for this value will be removed.

     @param value
     value to remove

     @return The removed value
     */
    public boolean removeValue (final VALUE value) {
        if (!containsValue (value)) {
            return false;
        }

        KEY removeKey = null;

        for (final KEY key : keySet ()) {
            if (get (key) == value) {
                removeKey = key;
                break;
            }
        }

        super.remove (removeKey);
        orderedKeys.remove (removeKey);

        return true;
    }

    /**
     ****************************************************************************
     Remove the head element, i.e. the element at position 0

     @return the removed element, null if none
     */
    public VALUE removeHead () {
        if (orderedKeys.size () > 0) {
            return super.remove (orderedKeys.remove (0));
        }

        return null;
    }

    /**
     ****************************************************************************
     Remove a value from the given position

     @param position
     position

     @return The removed value
     */
    public VALUE removeAtIndex (final int position) {
        if ((position >= 0) && (position < orderedKeys.size ())) {
            return super.remove (orderedKeys.remove (position));
        }

        return null;
    }

    /**
     *******************************************************************************
     Clear all data
     */
    public void clear () {
        super.clear ();
        orderedKeys.clear ();
    }

    /**
     *******************************************************************************
     The item count of this data structure

     @return
     */
    public int size () {
        if (orderedKeys.size () != super.size ()) {
            throw new IllegalStateException ("List and HashMap are out of sync");
        }

        return orderedKeys.size ();
    }

    /**
     finds the index of the given key

     @param key
     key to search for

     @return index of key or -1 if not found
     */
    public int indexOf (final KEY key) {
        return orderedKeys.indexOf (key);
    }

    public KEY keyAtIndex (final int index) {
        return orderedKeys.get(index);
    }

    public List<KEY> getOrderedKeys () {
        return new ArrayList<>(orderedKeys);
    }
}
