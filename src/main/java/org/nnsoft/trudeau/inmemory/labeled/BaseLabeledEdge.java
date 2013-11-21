package org.nnsoft.trudeau.inmemory.labeled;

/*
 *   Copyright 2013 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import static java.lang.String.format;
import static org.nnsoft.trudeau.utils.Assertions.checkNotNull;
import static org.nnsoft.trudeau.utils.Objects.eq;
import static org.nnsoft.trudeau.utils.Objects.hash;

import java.io.Serializable;

public class BaseLabeledEdge
    implements Serializable
{

    private static final long serialVersionUID = -4985890761880816592L;
    private final String label;

    public BaseLabeledEdge( String label )
    {
        this.label = checkNotNull( label, "Argument 'label' must not be null" );
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return hash( 1, 31, label );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null || getClass() != obj.getClass() )
        {
            return false;
        }

        BaseLabeledEdge other = (BaseLabeledEdge) obj;

        return eq( label, other.label );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "%s()", getLabel() );
    }

}
