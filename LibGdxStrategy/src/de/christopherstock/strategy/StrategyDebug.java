/*  $Id: StrategyDebug.java 164 2012-11-18 10:37:51Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy;

    import  com.badlogic.gdx.utils.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyDebug
    {
        //debug switches
        public      static  final   boolean         DRAW_WAYPOINT_EDGES                     = false;

        public      static  final   boolean         DRAW_TEST_TILES_UNITS                   = false;
        public      static  final   boolean         DRAW_TEST_TILES_BUILDINGS               = false;
        public      static  final   boolean         DRAW_TEST_TILES_MAP                     = false;


        //specified debug groups


        //paramount debug groups
        public      static          Logger          bugfix                                  = new Logger( "bugfix", Logger.INFO     );
        public      static          Logger          major                                   = new Logger( "major",  Logger.INFO     );
        public      static          Logger          err                                     = new Logger( "err",    Logger.ERROR    );
    }
