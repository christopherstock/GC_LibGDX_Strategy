/*  $Id: StrategyHID.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.io;

    import  com.badlogic.gdx.*;
    import  de.christopherstock.strategy.game.*;

    /**************************************************************************************
    *   Handles user input. Only touch operations are handled. No keys this time!
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyHID
    {
        private         static          int         lastX                   = -1;
        private         static          int         lastY                   = -1;

        private         static          int         firstX                  = -1;
        private         static          int         firstY                  = -1;

        public static final void checkUserInput()
        {
            //check pointer down
            if ( Gdx.input.isTouched() )
            {
                //check click
                if ( lastX == -1 && lastY == -1 )
                {
                    firstX = Gdx.input.getX();
                    firstY = Gdx.input.getY();

                    //handle click on map window ( buttons are on the stage )
                    StrategyLevel.current().clickMapWindow( Gdx.input.getX(), Gdx.input.getY() );
                }
                else
                {
                    //check drag
                    int deltaX = Gdx.input.getX() - lastX;
                    int deltaY = Gdx.input.getY() - lastY;

                    //handle drag if delta occured
                    if ( deltaX != 0 || deltaY != 0 )
                    {
                        //StrategyDebug.bugfix.info( "drag [" + deltaX + "][" + deltaY + "]" );

                        //pass drag to level
                        StrategyLevel.current().scrollBy( firstX, firstY, deltaX, deltaY );
                    }
                }

                //assign
                lastX = Gdx.input.getX();
                lastY = Gdx.input.getY();
            }
            else
            {
                lastX = -1;
                lastY = -1;
            }
        }
    }
