/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections.functors;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.collections.Predicate;

/**
 * Predicate implementation that returns true if none of the
 * predicates return true.
 * If the array of predicates is empty, then this predicate returns true.
 * <p>
 * NOTE: In versions prior to 3.2 an array size of zero or one
 * threw an exception.
 *
 * @since Commons Collections 3.0
 * @version $Revision$ $Date$
 *
 * @author Stephen Colebourne
 * @author Matt Benson
 */
public final class NonePredicate implements Predicate, PredicateDecorator, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 2007613066565892961L;
    
    /** The array of predicates to call */
    private final Predicate[] iPredicates;
    
    /**
     * Factory to create the predicate.
     * <p>
     * If the array is size zero, the predicate always returns true.
     *
     * @param predicates  the predicates to check, cloned, not null
     * @return the <code>any</code> predicate
     * @throws IllegalArgumentException if the predicates array is null
     * @throws IllegalArgumentException if any predicate in the array is null
     */
    public static Predicate getInstance(Predicate[] predicates) {
        FunctorUtils.validate(predicates);
        if (predicates.length == 0) {
            return TruePredicate.INSTANCE;
        }
        predicates = FunctorUtils.copy(predicates);
        return new NonePredicate(predicates);
    }

    /**
     * Factory to create the predicate.
     * <p>
     * If the collection is size zero, the predicate always returns true.
     *
     * @param predicates  the predicates to check, cloned, not null
     * @return the <code>one</code> predicate
     * @throws IllegalArgumentException if the predicates array is null
     * @throws IllegalArgumentException if any predicate in the array is null
     */
    public static Predicate getInstance(Collection predicates) {
        Predicate[] preds = FunctorUtils.validate(predicates);
        if (preds.length == 0) {
            return TruePredicate.INSTANCE;
        }
        return new NonePredicate(preds);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicates  the predicates to check, not cloned, not null
     */
    public NonePredicate(Predicate[] predicates) {
        super();
        iPredicates = predicates;
    }

    /**
     * Evaluates the predicate returning false if any stored predicate returns false.
     * 
     * @param object  the input object
     * @return true if none of decorated predicates return true
     */
    public boolean evaluate(Object object) {
        for (int i = 0; i < iPredicates.length; i++) {
            if (iPredicates[i].evaluate(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the predicates, do not modify the array.
     * 
     * @return the predicates
     * @since Commons Collections 3.1
     */
    public Predicate[] getPredicates() {
        return iPredicates;
    }

}
