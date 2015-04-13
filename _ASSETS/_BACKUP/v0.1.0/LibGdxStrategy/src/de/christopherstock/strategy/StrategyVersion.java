/*  $Id: StrategyVersion.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    enum StrategyVersion
    {
        V_0_0_5(    "0.0.5",    "02.08.2012, 00:12:18",     "attack enemies, let indicators fade in / out, let player find targets automatically ?!" ),
        V_0_0_4(    "0.0.4",    "30.07.2012, 23:58:22",     "button system, indicators for move- attack-target etc." ),
        V_0_0_2(    "0.0.2",    "05.07.2012, 00:11:31",     "prune y-inversion, box2d physics engine implementation, native ui components ( buttons ), encapsulate level and unit system, direct movement aside from 45° steps, auto-collision solution for movement," ),
        V_0_0_1(    "0.0.1",    "01.07.2012, 13:34:12",     "basic level and unit system, separate image and sound system, cleaned up the example code," ),
        ;

        private     String  version = null;
        private     String  date    = null;

        @SuppressWarnings( "unused" )
        private     String  log     = null;

        private StrategyVersion( String aVersion, String aDate, String aLog )
        {
            version = aVersion;
            date    = aDate;
            log     = aLog;
        }

        public static final String getCurrentVersionDesc()
        {
            return "v. " + values()[ 0 ].version + ", " + values()[ 0 ].date;
        }
    }
