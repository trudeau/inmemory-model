package org.nnsoft.trudeau.inmemory;

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

import org.nnsoft.trudeau.api.UndirectedGraph;

/**
 * A memory-based implementation of a mutable undirected Graph.
 *
 * This class is NOT thread safe!
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class UndirectedMutableGraph<V, E>
    extends BaseMutableGraph<V, E>
    implements UndirectedGraph<V, E>
{

    private static final long serialVersionUID = 3067145277295525946L;

    /**
     * {@inheritDoc}
     */
    public final int getDegree( V v )
    {
        return getAdjacencyList().get( v ).size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddVertex( V v )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveVertex( V v )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddEdge( V head, E e, V tail )
    {
        internalAddEdge( tail, e, head );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveEdge( E e )
    {
        internalRemoveEdge( getVertices( e ).getTail(), e, getVertices( e ).getHead() );
    }

}
