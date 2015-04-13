/*  $Id: LibMathGeometry.java 124 2012-08-04 19:50:56Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.lib.math;

    import  com.badlogic.gdx.math.*;

    /**************************************************************************************
    *   A collection of static geometry utility methods.
    *
    *   @author     Christopher Stock
    *   @version    1.0
    **************************************************************************************/
    public final class LibMathGeometry
    {
        public      static      final       int                 OUT_LEFT                    = 1;
        public      static      final       int                 OUT_TOP                     = 2;
        public      static      final       int                 OUT_RIGHT                   = 4;
        public      static      final       int                 OUT_BOTTOM                  = 8;

        /**************************************************************************************
        *   Checks if the direct distance between the two points is in the range of the given maximum.
        *
        *   @param  p1              The 1st point.
        *   @param  p2              The 2nd point.
        *   @param  maxDistance     The maximum distance the two points may have.
        *   @return                 <code>true</code> if the two points do not exceed the maximum distance.
        *                           Otherwise <code>false</code>.
        **************************************************************************************/
        public static boolean isInRange( Vector2 p1, Vector2 p2, float maxDistance )
        {
            float distanceX = p1.x - p2.x;
            float distanceY = p1.y - p2.y;
            return ( ( distanceX * distanceX ) + ( distanceY * distanceY ) ) <= ( maxDistance * maxDistance );
        }

        public static final boolean isInside( Rectangle child, Rectangle parent )
        {
            return
            (
                    child.x     >= parent.x
                &&  child.y     >= parent.y
                &&  child.x     <  parent.width  - child.width
                &&  child.y     <  parent.height - child.height
            );
        }

        public static final boolean overlaps( Rectangle rect1, Rectangle rect2 )
        {
            return
            !(
                    rect1.x                 >=  rect2.x + rect2.width
                ||  rect1.y                 >=  rect2.y + rect2.height
                ||  rect1.x + rect1.height  <=  rect2.x
                ||  rect1.y + rect1.height  <=  rect2.y
            );
        }

        public static final boolean lineIntersectsRectangle( Vector2 p1, Vector2 p2, Rectangle rect )
        {
            return lineIntersectsRectangle( rect, p1.x, p1.y, p2.x, p2.y );
/*
            return Intersector.intersectSegmentPlane( new Vector3( p1.x, p1.y, 0.0f ), new Vector3( p2.x, p2.y, 0.0f ), new Plan, intersection );
            return Intersector.intersectRayBoundsFast( , box );
            return ;lineIntersectsRectangle(p1, p2, rect)
            return new Line2D.Float( new Point2D.Float( p1.x, p1.y ), new Point2D.Float( p2.x, p2.y ) ).intersects( new Rectangle2D.Float( rect.x, rect.y, rect.width, rect.height ) );
*/
        }

        private static boolean lineIntersectsRectangle( Rectangle r, double x1, double y1, double x2, double y2 )
        {
            int out1, out2;
            if ( ( out2 = outcode( r, x2, y2 ) ) == 0)
            {
                return true;
            }
            while ( ( out1 = outcode( r, x1, y1 ) ) != 0 )
            {
                if ( ( out1 & out2 ) != 0 )
                {
                    return false;
                }
                if ( ( out1 & ( OUT_LEFT|OUT_RIGHT ) ) != 0 )
                {
                    double x = r.x;
                    if ( ( out1 & OUT_RIGHT ) != 0 )
                    {
                        x += r.width;
                    }
                    y1 = y1 + ( x - x1 ) * ( y2 - y1 ) / ( x2 - x1 );
                    x1 = x;
                }
                else
                {
                    double y = r.y;
                    if ( ( out1 & OUT_BOTTOM ) != 0 )
                    {
                        y += r.height;
                    }
                    x1 = x1 + ( y - y1 ) * ( x2 - x1 ) / ( y2 - y1 );
                    y1 = y;
                }
            }
            return true;
        }

        public static int outcode( Rectangle r, double x, double y)
        {
            int out = 0;
            if ( r.width <= 0 )
            {
                out |= OUT_LEFT | OUT_RIGHT;
            }
            else if ( x < r.x )
            {
                out |= OUT_LEFT;
            }
            else if ( x > r.x + (double) r.width )
            {
                out |= OUT_RIGHT;
            }
            if ( r.height <= 0 )
            {
                out |= OUT_TOP | OUT_BOTTOM;
            }
            else if ( y < r.y )
            {
                out |= OUT_TOP;
            }
            else if ( y > r.y + (double) r.height )
            {
                out |= OUT_BOTTOM;
            }
            return out;
        }

        /**************************************************************************************
        *   Find the intersection of this circle with the line defined by point p1 & p2.
        *
        *   @param      line        The line to check collision with the specified circle.
        *   @param      circle      The circle to check collision with the specified line.
        *   @return                 The NEARER point of intersection seen from Line2D.Float.getP1().
        *                           <code>null</code> if no intersection occured.
        **************************************************************************************/
/*
        public static Vector2       findLineCircleIntersection( Line2D.Float line, Ellipse2D.Float circle )
        {
            Vector2 p1 = (Vector2)line.getP1();
            Vector2 p2 = (Vector2)line.getP2();

            float radius  = (float)circle.getWidth() / 2;
            float circleX = (float)circle.getX() + radius;
            float circleY = (float)circle.getY() + radius;

            //substract line by circle values
            p1.x -= circleX;    p1.y -= circleY;
            p2.x -= circleX;    p2.y -= circleY;

            float dx = p2.x - p1.x;
            float dy = p2.y  - p1.y;
            float dr = (float)Math.sqrt( dx * dx + dy * dy );
            float D  = p1.x * p2.y - p2.x * p1.y;
            float discriminant = radius * radius * dr * dr - D * D;
            if ( discriminant < 0.0f )
            {
                //No intersection;
                return null;
            }
            else if ( discriminant == 0.0f )
            {
                //Tangant line there is only ONE intersection
            }
            else if ( discriminant > 0.0f )
            {
                //The line intersects at TWO points
            }

            //Compute intersection for ONE point (to compute intersection at OTHER point change + to a -)
            float x1 = (float)( ( D  * dy + Math.signum( dy ) * dx * Math.sqrt( discriminant ) ) / ( dr * dr ) );
            float y1 = (float)( ( -D * dx + Math.abs(    dy )      * Math.sqrt( discriminant ) ) / ( dr * dr ) );

            float x2 = (float)( ( D  * dy - Math.signum( dy ) * dx * Math.sqrt( discriminant ) ) / ( dr * dr ) );
            float y2 = (float)( ( -D * dx - Math.abs(    dy )      * Math.sqrt( discriminant ) ) / ( dr * dr ) );

            //translate back
            x1 += circleX;
            y1 += circleY;

            x2 += circleX;
            y2 += circleY;

            //return nearer point to line-point 1
            Vector2       ret =
            (
                        line.getP1().distance( new Vector2( x1, y1 ) )
                    <   line.getP1().distance( new Vector2( x2, y2 ) )
                ?   new Vector2( x1, y1 )
                :   new Vector2( x2, y2 )
            );

            return ret;
        }
*/
        /**************************************************************************************
        *   Checks if two line-segments intersect and returns the point of intersection.
        *
        *   @param  line1   Line-segment 1.
        *   @param  line2   Line-segment 2.
        *   @return         The point of intersection.
        *                   <code>null</code> if the lines to not intersect.
        **************************************************************************************/
/*
        public static Vector2       findLineSegmentIntersection( Line2D.Float line1, Line2D.Float line2 )
        {
            double[]    intersectionCoords  = new double[ 2 ];
            int         res                 = findLineSegmentIntersection
            (
                line1.getX1(), line1.getY1(),
                line1.getX2(), line1.getY2(),
                line2.getX1(), line2.getY1(),
                line2.getX2(), line2.getY2(),
                intersectionCoords
            );

            //may fail
            if ( res < 1 )
            {
                System.err.println( "FATAL!! bug in external class Geometry! no intersection point found!" );
                return null;
            }

            return new Vector2( (float)intersectionCoords[ 0 ], (float)intersectionCoords[ 1 ] );
        }
*/
        /*********************************************************************************
        *   Compute the length of the line from (x0,y0) to (x1,y1).
        *
        *   @param      x0  First  line end point x.
        *   @param      y0  First  line end point y.
        *   @param      x1  Second line end point x.
        *   @param      y1  Second line end point y.
        *   @return         Length of line from (x0,y0) to (x1,y1).
        ***********************************************************************************/
/*
        private static double length( double x0, double y0, double x1, double y1 )
        {
            double dx = x1 - x0;
            double dy = y1 - y0;

            return Math.sqrt ( dx*dx + dy*dy );
        }
*/
        /**************************************************************************************
        *   Compute the intersection between two line segments, or two lines
        *   of infinite length.
        *
        *   @param  x0              X coordinate first end point first line segment.
        *   @param  y0              Y coordinate first end point first line segment.
        *   @param  x1              X coordinate second end point first line segment.
        *   @param  y1              Y coordinate second end point first line segment.
        *   @param  x2              X coordinate first end point second line segment.
        *   @param  y2              Y coordinate first end point second line segment.
        *   @param  x3              X coordinate second end point second line segment.
        *   @param  y3              Y coordinate second end point second line segment.
        *   @param  intersection    Preallocated by caller to double[2]
        *   @return -1              if lines are parallel (x,y unset),
        *           -2              if lines are parallel and overlapping (x, y center)
        *           0               if intesrection outside segments (x,y set)
        *          +1               if segments intersect (x,y set)
        **************************************************************************************/
/*
        private static int findLineSegmentIntersection
        (
            double x0, double y0,
            double x1, double y1,
            double x2, double y2,
            double x3, double y3,
            double[] intersection
        )
        {
            // TO DO: Make limit depend on input domain
            final double LIMIT        = 1e-5;
            final double INFINITY = 1e10;

            double x, y;

            //
            // Convert the lines to the form y = ax + b
            //

            // Slope of the two lines
            double a0 = LibMath.equals (x0, x1, LIMIT) ?
                                    INFINITY : (y0 - y1) / (x0 - x1);
            double a1 = LibMath.equals (x2, x3, LIMIT) ?
                                    INFINITY : (y2 - y3) / (x2 - x3);

            double b0 = y0 - a0 * x0;
            double b1 = y2 - a1 * x2;

            // Check if lines are parallel
            if ( LibMath.equals ( a0, a1 ) )
            {
                if (!LibMath.equals (b0, b1))
                    return -1; // Parallell non-overlapping

                if (LibMath.equals (x0, x1)) {
                    if (Math.min (y0, y1) < Math.max (y2, y3) ||
                            Math.max (y0, y1) > Math.min (y2, y3)) {
                        double twoMiddle = y0 + y1 + y2 + y3 -
                                                             LibMath.min (y0, y1, y2, y3) -
                                                             LibMath.max (y0, y1, y2, y3);
                        y = (twoMiddle) / 2.0;
                        x = (y - b0) / a0;
                    }
                    else return -1;    // Parallell non-overlapping
                }
                else {
                    if (Math.min (x0, x1) < Math.max (x2, x3) ||
                            Math.max (x0, x1) > Math.min (x2, x3)) {
                        double twoMiddle = x0 + x1 + x2 + x3 -
                                                             LibMath.min (x0, x1, x2, x3) -
                                                             LibMath.max (x0, x1, x2, x3);
                        x = (twoMiddle) / 2.0;
                        y = a0 * x + b0;
                    }
                    else return -1;
                }

                intersection[0] = x;
                intersection[1] = y;
                return -2;
            }

            // Find correct intersection point
            if (LibMath.equals (a0, INFINITY)) {
                x = x0;
                y = a1 * x + b1;
            }
            else if (LibMath.equals (a1, INFINITY)) {
                x = x2;
                y = a0 * x + b0;
            }
            else {
                x = - (b0 - b1) / (a0 - a1);
                y = a0 * x + b0;
            }

            intersection[0] = x;
            intersection[1] = y;

            // Then check if intersection is within line segments
            double distanceFrom1;

            if (LibMath.equals (x0, x1)) {
                if (y0 < y1)
                    distanceFrom1 = y < y0 ? LibMathGeometry.length (x, y, x0, y0) :
                                                    y > y1 ? LibMathGeometry.length (x, y, x1, y1) : 0.0;
                else
                    distanceFrom1 = y < y1 ? LibMathGeometry.length (x, y, x1, y1) :
                                                    y > y0 ? LibMathGeometry.length (x, y, x0, y0) : 0.0;
            }
            else {
                if (x0 < x1)
                    distanceFrom1 = x < x0 ? LibMathGeometry.length (x, y, x0, y0) :
                                                    x > x1 ? LibMathGeometry.length (x, y, x1, y1) : 0.0;
                else
                    distanceFrom1 = x < x1 ? LibMathGeometry.length (x, y, x1, y1) :
                                                    x > x0 ? LibMathGeometry.length (x, y, x0, y0) : 0.0;
            }

            double distanceFrom2;

            if ( LibMath.equals ( x2, x3 ) )
            {
                if (y2 < y3)
                    distanceFrom2 = y < y2 ? LibMathGeometry.length (x, y, x2, y2) :
                                                    y > y3 ? LibMathGeometry.length (x, y, x3, y3) : 0.0;
                else
                    distanceFrom2 = y < y3 ? LibMathGeometry.length (x, y, x3, y3) :
                                                    y > y2 ? LibMathGeometry.length (x, y, x2, y2) : 0.0;
            }
            else
            {
                if (x2 < x3)
                    distanceFrom2 = x < x2 ? LibMathGeometry.length (x, y, x2, y2) :
                                                    x > x3 ? LibMathGeometry.length (x, y, x3, y3) : 0.0;
                else
                    distanceFrom2 = x < x3 ? LibMathGeometry.length (x, y, x3, y3) :
                                                    x > x2 ? LibMathGeometry.length (x, y, x2, y2) : 0.0;
            }

            return
            (
                    LibMath.equals (distanceFrom1, 0.0) && LibMath.equals (distanceFrom2, 0.0)
                ?   1
                :   0
            );
        }
*/
        /**************************************************************************************
        *   Checks if a line-segment intersects the given circle.
        *
        *   @param  line    The line-segment to check for intersection with circle.
        *   @param  circle  The circle. CAUTION! only the circle's width is used as it's radius!
        *   @return         <code>true</code> if the line intersects the circle in one ( tangent )
        *                   or two ( secant ) points. Otherwise <code>false</code>.
        *   @deprecated     can be replaced by native api.
        **************************************************************************************/
/*
        @Deprecated
        public static final boolean isLineIntersectingCircle( Line2D.Float line, Ellipse2D.Float circle )
        {
            //check if the closest distance from the circle's center is lower as it's radius
            return
            (
                line.ptSegDist( new Vector2( circle.getCenterX(), circle.getCenterY() ) ) <= circle.getWidth() / 2
            );
        }
*/
    }
