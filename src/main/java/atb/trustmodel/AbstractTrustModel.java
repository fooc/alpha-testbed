/*
 * Copyright (c) 2013 David Jelenc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     David Jelenc - initial API and implementation
 */
package atb.trustmodel;

import atb.interfaces.ParametersPanel;
import atb.interfaces.RandomGenerator;
import atb.interfaces.TrustModel;

/**
 * A template class for implementing {@link TrustModel} interfaces. It provides
 * default implementations to a couple of basic methods.
 *
 * @param <T> The data type in which the trust model conveys trust
 * @author David
 */
public abstract class AbstractTrustModel<T extends Comparable<T>>
        implements TrustModel<T> {

    protected RandomGenerator generator;

    @Override
    public void setRandomGenerator(RandomGenerator generator) {
        this.generator = generator;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public ParametersPanel getParametersPanel() {
        return null;
    }
}
