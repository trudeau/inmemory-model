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

import org.nnsoft.trudeau.api.Mapper;
import org.nnsoft.trudeau.api.SpanningTree;
import org.nnsoft.trudeau.math.monoid.Monoid;

/**
 * A memory-based implementation of a mutable spanning tree.
 *
 * This class is NOT thread safe!
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class MutableSpanningTree<V, WE, W>
    extends UndirectedMutableGraph<V, WE>
    implements SpanningTree<V, WE, W>
{

    private static final long serialVersionUID = -4371938772248573879L;

    private final Monoid<W> weightOperations;

    private final Mapper<WE, W> weightedEdges;

    private W weight;

    /**
     * Creates a new instance of {@link MutableSpanningTree}
     *
     * @param weightOperations
     * @param weightedEdges
     */
    public MutableSpanningTree( Monoid<W> weightOperations, Mapper<WE, W> weightedEdges )
    {
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;

        this.weight = weightOperations.identity();
    }

    /**
     * {@inheritDoc}
     */
    public W getWeight()
    {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddEdge( V head, WE e, V tail )
    {
        super.decorateAddEdge( head, e, tail );
        weight = weightOperations.append( weight, weightedEdges.map( e ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveEdge( WE e )
    {
        weight = weightOperations.append( weight, weightOperations.inverse( weightedEdges.map( e ) ) );
    }

}
