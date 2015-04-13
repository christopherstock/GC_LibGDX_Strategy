/*  $Id: StrategyStageButton.java 127 2012-08-05 12:58:48Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
    import  com.badlogic.gdx.scenes.scene2d.*;
    import  com.badlogic.gdx.scenes.scene2d.ui.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.game.unit.*;

    /**************************************************************************************
    *   All ui-buttons the game makes use of.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyStageButton extends Button implements ClickListener
    {
        public enum StrategyStageButtonState
        {
            EUnselected,
            ESelected,
            EActiveButSelectable,
            ;
        }

        private                         String                          iCaption                    = null;
        private                         StrategyStageButtonType         iKind                       = null;

        public StrategyStageButton( String aCaption, StrategyStageButtonType aKind, TextureRegion unsel, TextureRegion hover, TextureRegion sel )
        {
            this( aCaption, unsel, hover, sel, aKind );
        }

        private StrategyStageButton( String aCaption, TextureRegion unselect, TextureRegion hover, TextureRegion select, StrategyStageButtonType aKind )
        {
            super( unselect, hover, select );

            setClickListener( this );

            iCaption    = aCaption;
            iKind       = aKind;

            width       = StrategyHUD.BUTTON_WIDTH;
            height      = 2 * StrategyFont.presidentBlack.iFont.getLineHeight();
            x           = StrategyHUD.getHUDRect().x + ( StrategyHUD.getHUDRect().width - width ) / 2;
/*
            final TextButtonStyle buttonStyle = new TextButtonStyle();
            buttonStyle.font = new BitmapFont();
            buttonStyle.fontColor = Color.WHITE;
            buttonStyle.pressedOffsetY = 1f;
            buttonStyle.downFontColor = new Color( 0.8f, 0.8f, 0.8f, 1f );
            iButton.setStyle(buttonStyle);
*/
        }

        public final void setYForPosition( StrategyGameObject unit, int index )
        {
            y = ( unit != null && unit instanceof StrategyBuilding && ( (StrategyBuilding)unit ).isFactory() ? StrategyHUD.PRODUCTION_RECT.y : StrategyHUD.ENERGY_RECT.y ) - StrategyHUD.HUD_BORDER_SIZE - height - ( ( height + StrategyHUD.HUD_BORDER_SIZE ) * index );
        }

        @Override
        public void click( Actor a, float f1, float f2 )
        {
            //StrategyDebug.bugfix.info( "Click on button [" + iCaption + "]" );

            //check clicked button
            iKind.click();
        }

        @Override
        public void draw( SpriteBatch batch, float f1 )
        {
            //invoke super draw
            super.draw( batch, f1 );

            //draw caption
            if ( this == iKind.iButtonActiveButSelectable )
            {
                StrategyFont.presidentBlack.iFont.drawWrapped( batch, iCaption, x, y + height + ( ( StrategyFont.presidentBlack.iFont.getLineHeight() - height ) / 2 ), width, HAlignment.CENTER );
            }
            else
            {
                StrategyFont.presidentWhite.iFont.drawWrapped( batch, iCaption, x, y + height + ( ( StrategyFont.presidentBlack.iFont.getLineHeight() - height ) / 2 ), width, HAlignment.CENTER );
            }
        }
    }
