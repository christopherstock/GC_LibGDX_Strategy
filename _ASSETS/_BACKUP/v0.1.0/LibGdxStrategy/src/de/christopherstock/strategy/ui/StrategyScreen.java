/*  $Id: StrategyScreen.java 129 2012-08-05 14:02:43Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.graphics.*;
    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.StrategySettings.HUD;
    import  de.christopherstock.strategy.box2d.*;
    import  de.christopherstock.strategy.game.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyScreen
    {
        private     static              Rectangle               SCREEN_RECT                     = null;

        private     static              Pixmap                  icon16                          = null;
        private     static              Pixmap                  icon32                          = null;

        public      static              OrthographicCamera      camera                          = null;
        public      static              SpriteBatch             batch                           = null;

        public static final void init()
        {
            //crashes for html5
            try
            {
                icon16 = new Pixmap( Gdx.files.internal( "data/windowicon16.png" ) );
                icon32 = new Pixmap( Gdx.files.internal( "data/windowicon32.png" ) );
                Gdx.graphics.setIcon( new Pixmap[] { icon16, icon32, } );
            }
            catch ( Throwable t )
            {
            }

            //assign screen dimensions
            SCREEN_RECT = new Rectangle( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

            //create and init camera
            camera = new OrthographicCamera();

            //set ortho mode
            camera.setToOrtho( false, SCREEN_RECT.width, SCREEN_RECT.height );

            //create sprite batch
            StrategyScreen.batch = new SpriteBatch();
        }

        public static final void draw()
        {
            //clear screen
            Gdx.gl.glClearColor(  0.0f, 0.0f, 0.0f, 1 );
            Gdx.gl.glClear(       GL10.GL_COLOR_BUFFER_BIT );

            //update camera metrics and tell the SpriteBatch to render in the coordinate system specified by the camera
            StrategyScreen.camera.update();
            StrategyScreen.batch.setProjectionMatrix( StrategyScreen.camera.combined );

            //begin sprite batch
            {
                StrategyScreen.batch.begin();

                //draw level
                StrategyLevel.current().draw( batch );

                //draw HUD
                StrategyHUD.draw( batch );

                //draw box2D
                if ( HUD.BOX_2D_TEST )
                {
                    StrategyBox2D.draw( batch );
                }

                //end batch
                StrategyScreen.batch.end();
            }

            //draw stage
            StrategyStage.draw();
        }

        public static final void dispose()
        {
            icon16.dispose();
            icon32.dispose();
            batch.dispose();
        }

        public static final float getScreenWidth()
        {
            return SCREEN_RECT.width;
        }

        public static final float getScreenHeight()
        {
            return SCREEN_RECT.height;
        }
    }
