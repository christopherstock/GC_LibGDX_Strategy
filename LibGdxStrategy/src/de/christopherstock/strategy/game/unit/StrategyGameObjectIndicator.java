/*  $Id: StrategyUnitIndicator.java 104 2012-08-02 16:38:37Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game.unit;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  de.christopherstock.strategy.StrategySettings.HUD;

    /**************************************************************************************
    *   One unit indicator on the map.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    class StrategyGameObjectIndicator
    {
        protected                   Sprite                          iSprite                     = null;
        protected                   float                           iAlpha                      = 0.0f;

        protected StrategyGameObjectIndicator( Sprite aSprite )
        {
            iSprite = aSprite;
        }

        protected void rotate( float rot )
        {
            iSprite.rotate( rot );
        }

        protected void fade( boolean in )
        {
            if ( in )
            {
                fadeIn();
            }
            else
            {
                fadeOut();
            }
        }

        private void fadeIn()
        {
            iAlpha += HUD.INDICATOE_ALPHA_DELTA;
            if ( iAlpha > 1.0f ) iAlpha = 1.0f;
        }

        private void fadeOut()
        {
            iAlpha -= HUD.INDICATOE_ALPHA_DELTA;
            if ( iAlpha < 0.0f ) iAlpha = 0.0f;
        }

        protected final void draw( SpriteBatch batch, float mapLeft, float mapTop )
        {
            //draw the selector on the unit's location
            iSprite.setX( mapLeft       );
            iSprite.setY( mapTop        );
            iSprite.draw( batch, iAlpha );
        }


    }
