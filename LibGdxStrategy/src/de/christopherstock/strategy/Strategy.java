/*  $Id: Strategy.java 177 2012-11-25 13:47:00Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy;

    import  com.badlogic.gdx.ApplicationListener;
    import  de.christopherstock.strategy.StrategySettings.HUD;
    import  de.christopherstock.strategy.box2d.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.ui.*;

    /***********************************************************************************************
    *   LibGDX's application class represents the point of entry.
    *
    *   TODO    ASAP        stop movement if unit gets shot?
    *   TODO    HIGH        attack fx particle engine, smoke on damaged vehicles etc.
    *   TODO    INIT        units can walk behing buildings!
    *   TODO    INIT        create StrategySprite with sprite image support!
    *
    *   DONE                pruned java.awt.geom - not included in Android lib
    *   DONE                unit animations
    *   DONE                production gauge
    *   DONE                buildings and factories
    *   DONE                energy bar in different colors
    *   DONE                smaller font! different fonts / colors
    *   DONE                solved try way-finding-algo!
    *
    *   DISMISSED           animated 3d vehicle sprites?
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    public class Strategy implements ApplicationListener
    {
        @Override
        public void create()
        {
            //acclaim
            StrategyDebug.major.info( "Welcome to the LibGdx Strategy project v. [" + StrategyVersion.getCurrentVersionDesc() + "]" );

            //init screen
            StrategyScreen.init();

            //init fonts
            StrategyFont.init();

            //init HUD
            StrategyHUD.init();

            //load images
            StrategyImage.loadImages();

            //init sounds
            //StrategySound.test();

            //init level
            StrategyLevel.init();

            //create buttons
            StrategyStageButtonType.initAll();

            //create stage
            StrategyStage.init();

            //init box2d test
            if ( HUD.BOX_2D_TEST )
            {
                StrategyBox2D.init();
            }
        }

        @Override
        public void render()
        {
            //StrategyDebug.major.info( "render() " + System.currentTimeMillis() );

            //process user input
            StrategyHID.checkUserInput();

            //animate
            StrategyMainThread.tick();

            //draw
            StrategyScreen.draw();
        }

        @Override
        public void dispose()
        {
            //dispose all native resources
            StrategyImage.disposeImages();

            //dispose screen resources ( camera )
            StrategyScreen.dispose();

            //dispose sounds
            //StrategySound.dispose();

            //dispose fonts
            StrategyFont.dispose();

            //dispose box2d
            if ( HUD.BOX_2D_TEST )
            {
                StrategyBox2D.dispose();
            }
        }

        @Override
        public void resize(int width, int height)
        {
        }

        @Override
        public void pause()
        {
        }

        @Override
        public void resume()
        {
        }
    }
