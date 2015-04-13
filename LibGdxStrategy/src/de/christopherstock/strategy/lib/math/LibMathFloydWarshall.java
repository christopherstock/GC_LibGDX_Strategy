/*  $Id: LibMathGeometry.java 124 2012-08-04 19:50:56Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.lib.math;

    import  java.util.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.game.unit.*;

    /**************************************************************************************
    *   Floyd-Warshall algorithm for determining the shortest path between two nodes in a graph.
    *   Thread Safety: immutable
    *
    *   @author     Christopher Stock
    *   @version    1.0
    **************************************************************************************/
    public final class LibMathFloydWarshall
    {
        /**************************************************************************************
        *   An edge specifies a connection of two Nodes and it's distance.
        **************************************************************************************/
        public static class Edge implements Comparable<Edge>
        {
            public               Node           from                = null;
            public               Node           to                  = null;
            public               float          weight              = 0.0f;
            public               boolean        collided            = false;

            public Edge( final Node argFrom, final Node argTo, final float argWeight )
            {
                from     = argFrom;
                to       = argTo;
                weight   = argWeight;
                collided = false;
            }

            @Override
            public int compareTo( final Edge argEdge )
            {
                return (int)( weight - argEdge.weight );
            }
        }

        /**************************************************************************************
        *   One node. TODO extend Vector2
        **************************************************************************************/
        public static class Node implements Comparable<Node>
        {
            int                                 iIndex              = -1;           // used for Tarjan's algorithm
            public      Vector2                 iPos                = null;          // referenced position in the map
            public      StrategyGameObject      iUnit               = null;

           public Node( final int aIndex, Vector2 aPos, StrategyGameObject aUnit )
           {
               iIndex   = aIndex;
               iPos     = aPos;
               iUnit    = aUnit;
           }

           @Override
           public int compareTo( final Node argNode )
           {
               return argNode == this ? 0 : -1;
           }
        }

        private final float[][] D;
        private final Node[][] P;

        public static final void main( String[] args )
        {
            System.out.println( "FloydWarshall:main()" );

            Node[] nodes = new Node[]
            {
                new Node( 0, null, null ),
                new Node( 1, null, null ),
                new Node( 2, null, null ),
                new Node( 3, null, null ),
                new Node( 4, null, null ),
            };

            Edge[] edges = new Edge[]
            {
                new Edge( nodes[ 0 ], nodes[ 1 ], 20 ),
                new Edge( nodes[ 1 ], nodes[ 2 ], 30 ),
                new Edge( nodes[ 1 ], nodes[ 3 ], 40 ),
                new Edge( nodes[ 2 ], nodes[ 4 ], 30 ),
                new Edge( nodes[ 3 ], nodes[ 4 ], 10 ),
            };

            LibMathFloydWarshall fw = new LibMathFloydWarshall( nodes, edges );

            System.out.println( "Shortest path:" );
            List<Node> shortestPath = fw.getShortestPath( nodes[ 0 ], nodes[ 3 ] );
            for ( Node n : shortestPath )
            {
                System.out.println( " Node [" + n.iIndex + "]" );
            }



        }

        /**
         * Create an instance of this class by describing the graph
         * upon which it will operate.
         * <p>
         * Note <code>Node.name</code> must contain the index of the
         * node in the <code>nodes</code> parameter.  Thus <code>Node[1].name</code>
         * must equal one.
         * <p>
         * On small computers the practical maximum graph size with a 4-byte Node is
         * about 23,000, at which point the data size of an instance begins to exceed 4 GB.
         *
         * @param nodes Array of Node; must be completely populated
         * @param edges Array of Edge, completely populated; order is not important
         */
        public LibMathFloydWarshall ( final Node[] nodes, final Edge[] edges )
        {
            final int maxNodes = 23000;  // roughly 4 GB
            assert nodes.length < maxNodes : "nodes.length cannot exceed "+ maxNodes
                +".\nSize of class data structures is at least (2*(node size)*nodes.length**2).";

            D = initializeWeight(nodes, edges);
            P = new Node[nodes.length][nodes.length];

            for( int k=0; k<nodes.length; k++ )
            {
                for( int i=0; i<nodes.length; i++ )
                {
                    for( int j=0; j<nodes.length; j++ )
                    {
                        if
                        (
                                D[ i ][ k ] != Integer.MAX_VALUE
                            &&  D[ k ][ j ] != Integer.MAX_VALUE
                            &&  D[ i ][ k ] + D[ k ][ j ] < D[ i ][ j ]
                        )
                        {
                            D[ i ][ j ] = D[ i ][ k ] + D[ k ][ j ];
                            P[ i ][ j ] = nodes[ k ];
                        }
                    }
                }
            }
        }

        /**
         * Determines the length of the shortest path from vertex A (source)
         * to vertex B (target), calculated by summing the weights of the edges
         * traversed.
         * <p>
         * Note that distance, like path, is not commutative.  That is,
         * distance(A,B) is not necessarily equal to distance(B,A).
         *
         * @param source Start Node
         * @param target End Node
         * @return The path length as the sum of the weights of the edges traversed,
         * or <code>Integer.MAX_VALUE</code> if there is no path
         */
        public float getShortestDistance ( final Node source, final Node target )
        {
            return D[source.iIndex][target.iIndex];
        }

        /**
         * Describes the shortest path from vertex A (source) to vertex B (target)
         * by returning a collection of the vertices traversed, in the order traversed.
         * If there is no such path an empty collection is returned.
         * <p>
         * Note that because each Edge applies only to one direction of traverse,
         * the path from A to B may not be the same as the path from B to A.
         *
         * @param source Start Node
         * @param target End Node
         * @return A List (ordered Collection) of Node, possibly empty
         */
        public List<Node> getShortestPath ( final Node source, final Node target )
        {
            if(D[source.iIndex][target.iIndex] == Integer.MAX_VALUE){
                return new ArrayList<Node>(); // no path
            }
            final List<Node> path = getIntermediatePath( source, target );
            path.add( 0, source );
            path.add( target );
            return path;
        }

        private List<Node> getIntermediatePath ( final Node source, final Node target )
        {
            if(P[source.iIndex][target.iIndex] == null)  {
                return new ArrayList<Node>();
            }
            final List<Node> path = new ArrayList<Node>();
            path.addAll( getIntermediatePath(source, P[source.iIndex][target.iIndex]));
            path.add( P[source.iIndex][target.iIndex]);
            path.addAll( getIntermediatePath(P[source.iIndex][target.iIndex], target));
            return path;
        }

        private float[][] initializeWeight ( final Node[] nodes, final Edge[] edges )
        {
            float[][] Weight = new float[ nodes.length ][ nodes.length ];
            for(int i=0; i<nodes.length; i++)  {
                Arrays.fill( Weight[i], Integer.MAX_VALUE );
            }
            for( Edge e : edges )
            {
                //System.out.println( "EDGE TEST " + e.from.index + " " + e.to.index );

                Weight[ e.from.iIndex ][ e.to.iIndex ] = e.weight;
            }
            return Weight;
        }
    }
