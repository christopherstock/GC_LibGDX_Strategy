/*  $Id: StrategyUnit.java 109 2012-08-02 19:41:28Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game.unit;

    import  java.util.*;
    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.lib.math.*;
    import  de.christopherstock.strategy.ui.*;

    /**************************************************************************************
    *   Represents one game unit.
    *
    *   TODO encapsulate!
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public abstract class StrategyGameObject
    {
        public static enum Party
        {
            EPlayer,
            EEnemy,
            ;
        }

        protected static interface GameObjectType
        {
            public abstract TextureRegion getAvatarTextureRegion();

            public abstract TextureRegion getUnitTextureRegion();

            public abstract String getCaption();

            public abstract float getStartupEnergy();

            public abstract StrategyStageButtonType[] getButtonsToSet();

            public abstract int getConstructionTime();
        }

        public                      StrategyGameObjectAction        iAction                     = null;

        public                      Rectangle                       iRect                       = null;
        protected                   StrategyGameObject              iTargetUnit                 = null;
        protected                   GameObjectType                  iType                       = null;
        protected                   StrategyGameObjectIndicator     iIndicatorUnitSelection     = null;

        private                     Party                           iParty                      = null;
        private                     float                           iEnergy                     = 0.0f;
        private                     int                             iAfterShotDelayTimer        = 0;

        protected StrategyGameObject( Party aParty, GameObjectType aType, float aX, float aY )
        {
            iEnergy = aType.getStartupEnergy();
            iParty  = aParty;
            iType   = aType;

            iRect   = new Rectangle( aX, aY, iType.getUnitTextureRegion().getTexture().getWidth(), iType.getUnitTextureRegion().getTexture().getHeight() );
        }

        protected void hurt( float damage )
        {
            iEnergy -= damage;
            if ( iEnergy < 0.0f ) iEnergy = 0.0f;
        }

        protected float getEnergy()
        {
            return iEnergy;
        }

        public final boolean isDead()
        {
            return ( iEnergy <= 0.0f );
        }

        public final boolean isEnemy()
        {
            return isParty( Party.EEnemy );
        }

        public final boolean isFriend()
        {
            return isParty( Party.EPlayer );
        }

        protected final boolean isParty( Party party )
        {
            return ( iParty == party );
        }

        public final void drawHUD( SpriteBatch batch )
        {
            //draw avatar
            batch.draw( iType.getAvatarTextureRegion(), StrategyHUD.AVATAR_RECT.x, StrategyHUD.AVATAR_RECT.y, StrategyHUD.AVATAR_RECT.width, StrategyHUD.AVATAR_RECT.height );

            //draw avatar overlay
            batch.draw( StrategyImage.EAvatarOverlay.getTextureRegion(), StrategyHUD.AVATAR_RECT.x, StrategyHUD.AVATAR_RECT.y, StrategyHUD.AVATAR_RECT.width, StrategyHUD.AVATAR_RECT.height );

            //draw caption
            StrategyFont.presidentBlack.iFont.drawWrapped( batch, iType.getCaption(), StrategyHUD.HUD_RECT.x, StrategyHUD.HUD_RECT.height - StrategyHUD.HUD_BORDER_SIZE, StrategyHUD.HUD_RECT.width, HAlignment.CENTER );

            //draw energy bg & fg
            {
                float energyInPercent = ( getEnergy() / iType.getStartupEnergy() );
                batch.draw( StrategyImage.EGaugeEnergyBg.getTextureRegion(), StrategyHUD.ENERGY_RECT.x, StrategyHUD.ENERGY_RECT.y, StrategyHUD.ENERGY_RECT.width, StrategyHUD.ENERGY_RECT.height );
                StrategyImage imgFg = null;
                if ( energyInPercent >= 0.8f )
                {
                    imgFg = StrategyImage.EGaugeEnergyFgHigh;
                }
                else if ( energyInPercent >= 0.6f )
                {
                    imgFg = StrategyImage.EGaugeEnergyFgOk;
                }
                else if ( energyInPercent >= 0.4f )
                {
                    imgFg = StrategyImage.EGaugeEnergyFgMedium;
                }
                else if ( energyInPercent >= 0.2f )
                {
                    imgFg = StrategyImage.EGaugeEnergyFgLow;
                }
                else
                {
                    imgFg = StrategyImage.EGaugeEnergyFgDamaged;
                }
                batch.draw( imgFg.getTextureRegion().getTexture(), StrategyHUD.ENERGY_RECT.x, StrategyHUD.ENERGY_RECT.y, StrategyHUD.ENERGY_RECT.width * energyInPercent, StrategyHUD.ENERGY_RECT.height, 0, 0, (int)(StrategyImage.EGaugeEnergyFgHigh.getTextureRegion().getTexture().getWidth() * energyInPercent ), StrategyImage.EGaugeEnergyFgHigh.getTextureRegion().getTexture().getHeight(), false, false );
            }

            //draw production
            if ( this instanceof StrategyBuilding && ( (StrategyBuilding)this ).isFactory() )
            {
                StrategyBuilding b = (StrategyBuilding)this;
                batch.draw( StrategyImage.EGaugeEnergyBg.getTextureRegion(), StrategyHUD.PRODUCTION_RECT.x, StrategyHUD.PRODUCTION_RECT.y, StrategyHUD.PRODUCTION_RECT.width, StrategyHUD.PRODUCTION_RECT.height );

                //fg if production is in progress
                float productionInPercent = 0.0f;
                if ( b.iUnitTypeToBuild != null )
                {
                    productionInPercent = ( (float)( b.iUnitTypeToBuild.iTicksTillConstruction - b.iCreationTimer ) / (float)b.iUnitTypeToBuild.iTicksTillConstruction );
                    batch.draw( StrategyImage.EGaugeProductionFg.getTextureRegion().getTexture(), StrategyHUD.PRODUCTION_RECT.x, StrategyHUD.PRODUCTION_RECT.y, StrategyHUD.PRODUCTION_RECT.width * productionInPercent, StrategyHUD.PRODUCTION_RECT.height, 0, 0, (int)(StrategyImage.EGaugeProductionFg.getTextureRegion().getTexture().getWidth() * productionInPercent ), StrategyImage.EGaugeProductionFg.getTextureRegion().getTexture().getHeight(), false, false );
                }
            }
        }

        public final void updateHUDButtons( StrategyGameObject unit, StrategyGameObjectAction unitAction, StrategyGameObjectAction iMapAction, StrategyStageButtonType[] buttonsToSet )
        {
            if ( isFriend() )
            {
                //show buttons for player's units
                StrategyStage.setupStageWithButtons( unit, buttonsToSet, unitAction, iMapAction );
            }
            else if ( isEnemy() )
            {
                //no buttons for enemy units
                StrategyStage.setupStageWithButtons( unit, new StrategyStageButtonType[] {}, null, null );
            }
        }

        public final void setTargetUnit( StrategyGameObject targetUnit )
        {
            iTargetUnit = targetUnit;
        }

        public final boolean isInside( Rectangle parent )
        {
            //use own function for rectangle inside calculation
            return LibMathGeometry.isInside( iRect, parent );

            //inoperative if iRect has x|y 0.0|0.0 !! damn
            //return ( otherRectangle.contains( iRect ) );
        }

        public final StrategyGameObject getNearestUnit( Vector<StrategyGameObject> allUnits, float maxDistance, Party fromParty )
        {
            Vector2 xy = new Vector2( iRect.x + iRect.width / 2, iRect.y + iRect.height / 2 );

            //html5 does not support Hashtables!
            //TODO to ObjectMap<K, V>

            Vector<StrategyGameObject>  objects     = new Vector<StrategyGameObject>();
            Vector<Float>               distances   = new Vector<Float>();

            float nearestDistance = -1;

            //browse all units
            for ( StrategyGameObject unit : allUnits )
            {
                //only pick player units ( watch out not to pick the own unit! )
                if ( unit.isParty( fromParty ) )
                {
                    Vector2 unitXY      = new Vector2( unit.iRect.x + unit.iRect.width / 2, unit.iRect.y + unit.iRect.height / 2 );
                    float   distance    = xy.dst( unitXY );

                    objects.addElement( unit );
                    distances.addElement( new Float( distance ) );

                    //assign new nearest distance if lower
                    if ( nearestDistance == -1 || nearestDistance > distance )
                    {
                        //consider max distance
                        if ( distance <= maxDistance )
                        {
                            nearestDistance = distance;
                        }
                    }
                }
            }

            //browse all units in the hashtable
            for ( int i = 0; i < objects.size(); ++i )
            {
                if ( distances.elementAt( i ).floatValue() == nearestDistance )
                {
                    return objects.elementAt( i );
                }
            }

            return null;
        }

        public final StrategyGameObjectAction getAction()
        {
            return iAction;
        }

        public final StrategyGameObjectAction getCurrentAction()
        {
            return null;
        }

        public final boolean checkCollision( StrategyGameObject otherUnit )
        {
            return checkCollision( otherUnit.iRect );
        }

        public final boolean checkCollision( Rectangle otherRectangle )
        {
            //StrategyDebug.bugfix.info( "own rect ["+iRect+"] other rect ["+otherRectangle+"]" );

            return LibMathGeometry.overlaps( otherRectangle, iRect );

            //return otherRectangle.overlaps( iRect );
        }

        public final boolean checkCollision( Vector2 p1, Vector2 p2 )
        {
            //StrategyDebug.bugfix.info( "own rect ["+iRect+"] other rect ["+otherRectangle+"]" );

            return LibMathGeometry.lineIntersectsRectangle( p1, p2, iRect );

            //return otherRectangle.overlaps( iRect );
        }

        public final boolean checkSelection( float mapClickX, float mapClickY )
        {
            //check if the unit is clicked
            boolean matches =
            (
                    mapClickX >= iRect.x
                &&  mapClickY >= iRect.y
                &&  mapClickX <  iRect.x + iType.getUnitTextureRegion().getTexture().getWidth()
                &&  mapClickY <  iRect.y + iType.getUnitTextureRegion().getTexture().getHeight()
            );

            return matches;
        }

        public final StrategyStageButtonType[] getButtonsToSet()
        {
            return iType.getButtonsToSet();
        }

        public final void tickAfterShotTimer()
        {
            if ( --iAfterShotDelayTimer < 0 ) iAfterShotDelayTimer = 0;
        }

        public final boolean isAfterShotTimerIdle()
        {
            return ( iAfterShotDelayTimer == 0 );
        }

        public final void startAfterShotTimer()
        {
            iAfterShotDelayTimer = ( (StrategyUnitType)iType ).iAfterShotDelayTicks;
        }

        public abstract void update( boolean selected );

        public abstract void draw( SpriteBatch batch, float mapLeft, float mapTop );

        public abstract void drawIndicators( SpriteBatch batch, float mapLeft, float mapTop );

        public abstract void moveTo( float mapClickX, float mapClickY, StrategyGameObjectAction aAction );

        public abstract void performWait();
    }
