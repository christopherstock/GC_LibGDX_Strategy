/*  $Id: StrategyHUD.java 127 2012-08-05 12:58:48Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.StrategySettings.HUD;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.io.*;

    /**************************************************************************************
    *   The heads-up display ( right sidebar ).
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyHUD
    {
        public      static              Rectangle               HUD_RECT                        = null;
        public      static              Rectangle               MAP_RECT                        = null;
        public      static              Rectangle               AVATAR_RECT                     = null;
        public      static              Rectangle               ENERGY_RECT                     = null;
        public      static              Rectangle               PRODUCTION_RECT                 = null;

        public      static              float                   HUD_BORDER_SIZE                 = 0;
        public      static              float                   INDICATOR_BAR_HEIGHT            = 0;
        public      static              float                   BUTTON_WIDTH                    = 0;

        public static final void init()
        {
            float   HUD_WIDTH       = StrategyScreen.getScreenWidth() * HUD.HUD_SCREEN_WIDTH_RATIO;
            float   AVATAR_SIZE     = HUD_WIDTH * HUD.HUD_AVATAR_RATIO;
            int     MAGIC_X_PIXEL   = 1;    //map rect gets overlapped by hud drawing otherwise

            HUD_BORDER_SIZE         = HUD_WIDTH * HUD.HUD_BORDER_RATIO;
            INDICATOR_BAR_HEIGHT    = HUD_BORDER_SIZE;
            BUTTON_WIDTH            = HUD_WIDTH * HUD.HUD_BUTTON_RATIO;

            HUD_RECT            = new Rectangle( StrategyScreen.getScreenWidth() - HUD_WIDTH, 0, HUD_WIDTH, StrategyScreen.getScreenHeight() );

            MAP_RECT            = new Rectangle( 0, 0, StrategyScreen.getScreenWidth() - HUD_RECT.width - MAGIC_X_PIXEL, StrategyScreen.getScreenHeight() );

            float y             = HUD_RECT.height - HUD_BORDER_SIZE - AVATAR_SIZE - StrategyFont.presidentBlack.iFont.getLineHeight() - StrategyHUD.HUD_BORDER_SIZE;

            AVATAR_RECT         = new Rectangle( HUD_RECT.x + ( HUD_RECT.width - AVATAR_SIZE ) / 2, y, AVATAR_SIZE, AVATAR_SIZE );
            y -= ( INDICATOR_BAR_HEIGHT + StrategyHUD.HUD_BORDER_SIZE ); // + StrategyFont.sampleFont.getLineHeight() );

            ENERGY_RECT         = new Rectangle( ( HUD_RECT.x + ( HUD_RECT.width - BUTTON_WIDTH ) / 2 ), y, BUTTON_WIDTH, INDICATOR_BAR_HEIGHT );
            y -= ( INDICATOR_BAR_HEIGHT + StrategyHUD.HUD_BORDER_SIZE ); // + StrategyFont.sampleFont.getLineHeight() );

            PRODUCTION_RECT     = new Rectangle( ( HUD_RECT.x + ( HUD_RECT.width - BUTTON_WIDTH ) / 2 ), y, BUTTON_WIDTH, INDICATOR_BAR_HEIGHT );
            y -= ( INDICATOR_BAR_HEIGHT + StrategyHUD.HUD_BORDER_SIZE );
        }

        public static final void draw( SpriteBatch batch )
        {
            //draw the HUD
            batch.draw( StrategyImage.EBgHUD.getTextureRegion(), HUD_RECT.x, HUD_RECT.y, HUD_RECT.width, HUD_RECT.height );

            //draw avatar of currently selected unit
            StrategyLevel.current().drawCurrentUnitHUD( batch );
        }

        public static final float getMapWindowWidth()
        {
            return MAP_RECT.width;
        }

        public static final float getMapWindowHeight()
        {
            return MAP_RECT.height;
        }

        public static final Rectangle getHUDRect()
        {
            return HUD_RECT;
        }

        public static final boolean isInMapWindow( int x, int y )
        {
            return MAP_RECT.contains( x, y );
        }
    }
