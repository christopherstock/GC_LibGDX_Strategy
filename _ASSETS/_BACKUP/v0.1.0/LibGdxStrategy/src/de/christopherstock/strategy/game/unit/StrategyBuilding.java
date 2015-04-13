/*  $Id: StrategyUnit.java 115 2012-08-02 20:56:16Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game.unit;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  de.christopherstock.strategy.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.io.*;

    /**************************************************************************************
    *   Represents one game unit.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyBuilding extends StrategyGameObject
    {
        public                      int                 iCreationTimer                  = 0;
        public                      StrategyUnitType    iUnitTypeToBuild                = null;

        public StrategyBuilding( StrategyBuildingType aType, int aX, int aY, Party aParty )
        {
            super( aParty, aType, aX, aY );

            if ( isFriend() )
            {
                iIndicatorUnitSelection = new StrategyGameObjectIndicator( StrategyImage.EIndicatorBuildingSelectionPlayer.createNewSprite() );
            }
            else if ( isEnemy() )
            {
                iIndicatorUnitSelection = new StrategyGameObjectIndicator( StrategyImage.EIndicatorBuildingSelectionEnemy.createNewSprite() );
            }
        }

        @Override
        public final void draw( SpriteBatch batch, float mapLeft, float mapTop )
        {
            //draw unit
            batch.draw( iType.getUnitTextureRegion(), mapLeft + iRect.x, mapTop - iRect.y - iRect.height );

            //draw test tile
            if ( StrategyDebug.DRAW_TEST_TILES_BUILDINGS )
            {
                batch.draw( StrategyImage.ETileTestBig.getTextureRegion(), mapLeft + iRect.x, mapTop - iRect.y - iRect.height );
            }

            //StrategyDebug.bugfix.info( "building rect height " + iRect.height );
        }

        @Override
        public final void drawIndicators( SpriteBatch batch, float mapLeft, float mapTop )
        {
            //draw selection last
            iIndicatorUnitSelection.draw(   batch, mapLeft + iRect.x,               mapTop - iRect.y - iRect.height             );
        }

        @Override
        public final void update( boolean isCurrentlySelected )
        {
            //tick after-shot-delay-timer
            tickAfterShotTimer();

            //rotate indicators
            {
                iIndicatorUnitSelection.rotate( StrategySettings.HUD.INDICATOR_BUILDING_SELECTION_ROTATION    );
            }

            //fade indicators
            {
                //selection
                iIndicatorUnitSelection.fade(   isCurrentlySelected );
            }

            //reference unit-type
            //StrategyBuildingType type = (StrategyBuildingType)iType;

            //ticker creation timer
            if ( iCreationTimer > 0 )
            {
                --iCreationTimer;

                if ( iCreationTimer == 0 )
                {
                    //StrategyDebug.bugfix.info( "spawn new unit" );

                    //spawn new unit
                    StrategyLevel.current().iUnitsToAdd.addElement( new StrategyUnit( iUnitTypeToBuild, iRect.x + ( iRect.width - iUnitTypeToBuild.getUnitTextureRegion().getTexture().getWidth() ) / 2, iRect.y + iRect.height, Party.EPlayer ) );

                    iAction             = StrategyGameObjectAction.ENone;
                    iUnitTypeToBuild    = null;

                    StrategyLevel.current().updateHUDButtonsForUnit( this );
                }
            }
        }

        @Override
        public final void moveTo( float mapClickX, float mapClickY, StrategyGameObjectAction aAction )
        {
            //buildings won't move ;)
        }

        @Override
        public final void performWait()
        {
        }

        public final boolean isFactory()
        {
            return ( (StrategyBuildingType)iType ).iIsFactory;
        }
    }
