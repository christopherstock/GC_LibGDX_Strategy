/*  $Id: StrategyStageButton.java 179 2013-04-11 12:37:39Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
    import  com.badlogic.gdx.scenes.scene2d.ui.*;
    import  com.badlogic.gdx.scenes.scene2d.utils.*;
    import  de.christopherstock.strategy.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.game.unit.*;

    /**************************************************************************************
    *   All ui-buttons the game makes use of.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyStageButton extends Button // implements ClickListener
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

        public StrategyStageButton( String aCaption, StrategyStageButtonType aKind, Drawable unsel, Drawable hover, Drawable sel )
        {
            this( aCaption, unsel, hover, sel, aKind );
        }

        private StrategyStageButton( String aCaption, Drawable unselect, Drawable hover, Drawable select, StrategyStageButtonType aKind )
        {
            super( unselect, hover, select );

          //outdated!
          //setClickListener( this );

            iCaption    = aCaption;
            iKind       = aKind;

            setWidth(  StrategyHUD.BUTTON_WIDTH );
            setHeight( 2 * StrategyFont.presidentBlack.iFont.getLineHeight() );

            setX( StrategyHUD.getHUDRect().x + ( StrategyHUD.getHUDRect().width - getWidth() ) / 2 );
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
            setY( ( unit != null && unit instanceof StrategyBuilding && ( (StrategyBuilding)unit ).isFactory() ? StrategyHUD.PRODUCTION_RECT.y : StrategyHUD.ENERGY_RECT.y ) - StrategyHUD.HUD_BORDER_SIZE - getHeight() - ( ( getHeight() + StrategyHUD.HUD_BORDER_SIZE ) * index ) );
        }


/*
    // outdated!

        @Override
        public void click( Actor a, float f1, float f2 )
        {
            //StrategyDebug.bugfix.info( "Click on button [" + iCaption + "]" );

            //check clicked button
            iKind.click();
        }
*/
        @Override
        public void setChecked( boolean b )
        {
            StrategyDebug.bugfix.info( "Check button [" + iCaption + "]" );

            //click button
            iKind.click();

            //invoke super method
            super.setChecked( b );
        }

        @Override
        public void draw( SpriteBatch batch, float f1 )
        {
            //StrategyDebug.bugfix.info( "drawing button [" + iCaption + "]" );

            //invoke super draw
            super.draw( batch, f1 );

            //draw caption
            if ( this == iKind.iButtonActiveButSelectable )
            {
                StrategyFont.presidentBlack.iFont.drawWrapped( batch, iCaption, getX(), getY() + getHeight() + ( ( StrategyFont.presidentBlack.iFont.getLineHeight() - getHeight() ) / 2 ), getWidth(), HAlignment.CENTER );
            }
            else
            {
                StrategyFont.presidentWhite.iFont.drawWrapped( batch, iCaption, getX(), getY() + getHeight() + ( ( StrategyFont.presidentBlack.iFont.getLineHeight() - getHeight() ) / 2 ), getWidth(), HAlignment.CENTER );
            }
        }
    }
