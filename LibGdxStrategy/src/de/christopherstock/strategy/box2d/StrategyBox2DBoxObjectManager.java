/*  $Id: StrategyBox2DBoxObjectManager.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  java.util.Vector;
    import  com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import  com.badlogic.gdx.math.Vector2;
    import  com.badlogic.gdx.physics.box2d.World;

    /**********************************************************************************************
    *   Ready to export.
    **********************************************************************************************/
    class StrategyBox2DBoxObjectManager
    {
        private     static  final   float                                   BOX_TO_WORLD            = 100.0f;
        private     static  final   float                                   WORLD_TO_BOX            = 0.01f;
        private     static  final   float                                   BOX_STEP                = 1 / 60.0f;
        private     static  final   int                                     VELOCITY_ITERATIONS     = 8;
        private     static  final   int                                     POSITION_ITERATIONS     = 3;

        private                     World                                   world                   = null;
        private                     Vector<StrategyBox2DBaseBoxObject>      bodies                  = null;
        private                     float                                   accumulator             = 0.0f;
        private                     boolean                                 isPaused                = false;

        public StrategyBox2DBoxObjectManager()
        {
            //world       = new World( new Vector2( 0, -20 ), true );
            world       = new World( new Vector2( 0, -15.0f ), true );
            bodies      = new Vector<StrategyBox2DBaseBoxObject>();
            isPaused    = false;
        }

        public World getWorld()
        {
            return world;
        }

        protected static float convertToBox( float x )
        {
            return ( x * WORLD_TO_BOX );
        }

        protected static float convertToWorld( float x )
        {
            return ( x * BOX_TO_WORLD );
        }

        public void pause()
        {
            accumulator = 0;
            isPaused    = true;
        }
        
        public void resume()
        {
            accumulator = 0;
            isPaused    = false;
        }

        public int getNewObjectIndex()
        {
            return bodies.size();
        }

        public void addObject( StrategyBox2DBaseBoxObject obj )
        {
            bodies.add( obj );
        }

        public void update( float dt )
        {
            if ( !isPaused )
            {
                accumulator += dt;
                while ( accumulator > dt )
                {
                    world.step( BOX_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS );
                    accumulator -= BOX_STEP;
                }
            }

            for ( StrategyBox2DBaseBoxObject bo : bodies )
            {
                bo.update( dt );
            }
        }

        public void draw( SpriteBatch sp )
        {
            for ( StrategyBox2DBaseBoxObject bo : bodies )
            {
                bo.draw( sp );
            }
        }

        public void dispose()
        {
            for ( StrategyBox2DBaseBoxObject tbo : bodies )
            {
                tbo.destroyBody();
            }
            bodies.clear();
        }
    }
