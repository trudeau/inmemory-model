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

import static org.junit.Assert.assertEquals;
import static org.nnsoft.trudeau.api.Graphs.synchronize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.After;
import org.junit.Test;
import org.nnsoft.trudeau.api.Graph;
import org.nnsoft.trudeau.api.MutableGraph;
import org.nnsoft.trudeau.inmemory.labeled.BaseLabeledEdge;
import org.nnsoft.trudeau.inmemory.labeled.BaseLabeledVertex;
import org.nnsoft.trudeau.inmemory.labeled.BaseLabeledWeightedEdge;
import org.nnsoft.trudeau.inmemory.labeled.BaseWeightedEdge;
import org.nnsoft.trudeau.math.monoid.primitive.DoubleWeightBaseOperations;

/**
 * Provides a simple test case to test the Graph serialization.
 */
public class GraphSerializationTestCase
{

    private final static String FILE_NAME = "target/serialiazedGraph.dat";

    @After
    public void cleanUp()
    {
        File f = new File( FILE_NAME );
        if ( f.exists() )
        {
            f.delete();
        }
    }

    @Test
    public void serializeUndirectedGraph()
        throws Exception
    {
        MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildGraphConnections( g );

        checkSerialization( g );
    }

    @Test
    public void serializeDirectedGraph()
        throws Exception
    {
        MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildGraphConnections( g );

        checkSerialization( g );
    }

    @Test
    public void serializeUndirectedWeightdGraph()
        throws Exception
    {
        MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> g =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
        buildWeightedGraphConnections( g );

        checkSerialization( g );
    }

    @Test
    public void serializeDirectedWeightdGraph()
        throws Exception
    {
        MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> g =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
        buildWeightedGraphConnections( g );

        checkSerialization( g );
    }

    @Test
    public void serializeSpanningTree()
        throws Exception
    {
        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> spanningTree =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(),
                                                                                                 new BaseWeightedEdge<Double>() );

        buildWeightedGraphConnections( spanningTree );

        checkSerialization( spanningTree );
    }

    @Test
    public void serializeSyncronyzedDirectedWeightdGraph()
        throws Exception
    {
        MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> g =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
        buildWeightedGraphConnections( g );

        checkSerialization( synchronize( g ) );
    }

    @Test
    public void serializePath()
        throws Exception
    {
        BaseLabeledVertex start = new BaseLabeledVertex( "start" );
        BaseLabeledVertex goal = new BaseLabeledVertex( "goal" );
        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> g =
            new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>(
                                                                                                  start,
                                                                                                  goal,
                                                                                                  new DoubleWeightBaseOperations(),
                                                                                                  new BaseWeightedEdge<Double>() );

        g.addConnectionInTail( start, new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ), a );
        g.addConnectionInTail( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ), b );
        g.addConnectionInTail( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 3D ), c );
        g.addConnectionInTail( c, new BaseLabeledWeightedEdge<Double>( "c <-> goal", 3D ), goal );

        checkSerialization( g );
    }

    /**
     * @param g
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void checkSerialization( Graph<BaseLabeledVertex, ? extends Serializable> g )
        throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileOutputStream fout = new FileOutputStream( FILE_NAME );
        ObjectOutputStream oos = new ObjectOutputStream( fout );
        oos.writeObject( g );
        oos.close();

        FileInputStream fin = new FileInputStream( FILE_NAME );
        ObjectInputStream ois = new ObjectInputStream( fin );
        Object cloned = ois.readObject();
        ois.close();

        assertEquals( g, cloned );
    }

    private static void buildGraphConnections( MutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph )
    {
        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );

        graph.addVertex( a );
        graph.addVertex( b );
        graph.addVertex( c );
        graph.addVertex( d );

        graph.addEdge( a, new BaseLabeledEdge( "a -> c" ), c );
        graph.addEdge( c, new BaseLabeledEdge( "c -> d" ), d );
        graph.addEdge( d, new BaseLabeledEdge( "d -> b" ), b );
    }

    private static void buildWeightedGraphConnections( MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> graph )
    {
        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );

        graph.addVertex( a );
        graph.addVertex( b );
        graph.addVertex( c );
        graph.addVertex( d );

        graph.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a -> c", 1D ), c );
        graph.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c -> d", 1D ), d );
        graph.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d -> b", 1D ), b );
    }

}
