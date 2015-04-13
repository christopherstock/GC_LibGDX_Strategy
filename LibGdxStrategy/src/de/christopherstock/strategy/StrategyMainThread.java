/*  $Id: StrategyMainThread.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy;

    import  de.christopherstock.strategy.game.*;

    /***********************************************************************************************
    *   Represents one tick of the main thread ( 'game loop' ).
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    class StrategyMainThread
    {
        public static final void tick()
        {
            //update level
            StrategyLevel.current().update();
        }
    }
