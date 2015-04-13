/*  $Id: StrategySettings.java 157 2012-08-08 22:46:31Z jenetic.bytemare@gmail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy;

    /***********************************************************************************************
    *   All settings.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    public abstract class StrategySettings
    {
        public static abstract class General
        {
            public  static      final       int             UNIT_MOVE_FRAME_DELAY                   = 10;
        }

        public static abstract class HUD
        {
            public  static      final       boolean         BOX_2D_TEST                             = false;

            public  static      final       float           HUD_SCREEN_WIDTH_RATIO                  = 0.2f;
            public  static      final       float           HUD_BORDER_RATIO                        = 0.1f;
            public  static      final       float           HUD_BUTTON_RATIO                        = 0.75f;
            public  static      final       float           HUD_AVATAR_RATIO                        = 0.5f;

            public  static      final       float           INDICATOR_UNIT_SELECTION_ROTATION       = 0.5f;
            public  static      final       float           INDICATOR_UNIT_MOVE_ROTATION            = 0.25f;
            public  static      final       float           INDICATOR_UNIT_ATTACK_ROTATION          = 0.25f;

            public  static      final       float           INDICATOR_BUILDING_SELECTION_ROTATION   = 0.5f;

            public  static      final       float           INDICATOE_ALPHA_DELTA                    = 0.075f;
        }
    }
