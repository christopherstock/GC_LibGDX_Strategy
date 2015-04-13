/*  $Id: StrategyUnit.java 116 2012-08-02 21:01:52Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game.unit;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.lib.math.*;

    /**************************************************************************************
    *   Represents one game unit.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyUnit extends StrategyGameObject
    {
        private                         StrategyGameObjectIndicator     iIndicatorUnitMove          = null;
        private                         StrategyGameObjectIndicator     iIndicatorUnitAttack        = null;

        private                         Vector2[]                       iCurrentWaypoints           = null;
        private                         Vector2                         iTargetMovement             = null;
        private                         Vector2                         iTargetIndicator            = null;
        private                         int                             iCurrentMoveFrameIndex      = 0;
        private                         int                             iCurrentMoveFrameDelay      = 0;
        private                         int                             iCurrentWaypointIndex       = 0;
        private                         float                           iTargetAngle                = 0;
        private                         Vector2                         iMove                       = null;

        public StrategyUnit( StrategyUnitType aType, float aX, float aY, Party aParty )
        {
            super( aParty, aType, aX, aY );

            iTargetMovement     = new Vector2( -1, -1 );
            iTargetIndicator    = new Vector2( -1, -1 );
            iAction             = StrategyGameObjectAction.EWait;

            iCurrentMoveFrameIndex  = aType.iImagesMove.length - 1;

            iIndicatorUnitMove      = new StrategyGameObjectIndicator( StrategyImage.EIndicatorUnitMove.createNewSprite() );
            iIndicatorUnitAttack    = new StrategyGameObjectIndicator( StrategyImage.EIndicatorUnitAttack.createNewSprite() );

            if ( isFriend() )
            {
                iIndicatorUnitSelection = new StrategyGameObjectIndicator( StrategyImage.EIndicatorUnitSelectionPlayer.createNewSprite() );
            }
            else if ( isEnemy() )
            {
                iIndicatorUnitSelection = new StrategyGameObjectIndicator( StrategyImage.EIndicatorUnitSelectionEnemy.createNewSprite() );
            }
        }

        @Override
        public final void draw( SpriteBatch batch, float mapLeft, float mapTop )
        {
            //draw unit
            TextureRegion icon = null;

            switch ( iAction )
            {
                case EMove:
                {
                    icon = ( (StrategyUnitType)iType ).iImagesMove[ iCurrentMoveFrameIndex ].getTextureRegion();
                    break;
                }

                default:
                {
                    icon = iType.getUnitTextureRegion();
                    break;
                }

            }


            batch.draw( icon, mapLeft + iRect.x, mapTop - iRect.y - iRect.height );

            //draw test tile
            if ( StrategyDebug.DRAW_TEST_TILES_UNITS )
            {
                batch.draw( StrategyImage.ETileTest.getTextureRegion(), mapLeft + iRect.x, mapTop - iRect.y - iRect.height );
            }
        }

        @Override
        public final void drawIndicators( SpriteBatch batch, float mapLeft, float mapTop )
        {
            //draw target indicators
            iIndicatorUnitMove.draw(        batch, mapLeft + iTargetIndicator.x,    mapTop - iTargetIndicator.y - iRect.height  );
            iIndicatorUnitAttack.draw(      batch, mapLeft + iTargetIndicator.x,    mapTop - iTargetIndicator.y - iRect.height  );

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
                iIndicatorUnitSelection.rotate(   StrategySettings.HUD.INDICATOR_UNIT_SELECTION_ROTATION    );
                iIndicatorUnitMove.rotate(        StrategySettings.HUD.INDICATOR_UNIT_MOVE_ROTATION         );
                iIndicatorUnitAttack.rotate(      StrategySettings.HUD.INDICATOR_UNIT_ATTACK_ROTATION       );
            }

            //fade indicators
            {
                //selection
                iIndicatorUnitSelection.fade(   isCurrentlySelected                                                                                         );
                iIndicatorUnitMove.fade(        isCurrentlySelected && iAction == StrategyGameObjectAction.EMove                                                  );
                iIndicatorUnitAttack.fade(      isCurrentlySelected && iAction == StrategyGameObjectAction.EAttack || iAction == StrategyGameObjectAction.EHarvest      );
            }

            //reference unit-type
            StrategyUnitType type = (StrategyUnitType)iType;

            //check party
            if ( isEnemy() )
            {
                //always pick a target unit ( let's check performance later here .. )
                //if ( iTargetUnit == null || iTargetUnit.iDead )
                {
                    //pick a target unit
                    iTargetUnit = StrategyLevel.current().getNearestPlayerUnit( this, type.iViewDistance, Party.EPlayer );

                    //show target unit if any
                    if ( iTargetUnit != null )
                    {
                        //StrategyDebug.bugfix.info( "ASSIGN NEW NEAREST TARGET: " + iTargetUnit.getCaption() + " FOR UNIT " + getCaption() );

                        iAction = StrategyGameObjectAction.EAttack;

                        iTargetMovement.x = iTargetUnit.iRect.x;
                        iTargetMovement.y = iTargetUnit.iRect.y;
                    }
                    else
                    {
                        iAction = StrategyGameObjectAction.EWait;
                    }
                }
            }
            else if ( isFriend() )
            {
                //StrategyDebug.bugfix.info( "Handle player unit .." );

                //pick target if desired
                if ( iAction == StrategyGameObjectAction.EWait && iTargetUnit == null )
                {
                    //StrategyDebug.bugfix.info( "pick target .." );

                    //pick a target
                    iTargetUnit = StrategyLevel.current().getNearestPlayerUnit( this, type.iViewDistance, Party.EEnemy );
/*
                        if ( iTargetUnit != null )
                        {
                            StrategyDebug.bugfix.info( "pickED target .." );

                            iAction = StrategyUnitAction.EAttack;

                            iTargetIndicator.x = iTargetUnit.iRect.x;
                            iTargetIndicator.y = iTargetUnit.iRect.y;
                        }
*/
                }

                //follow target if attacking
                if ( iAction == StrategyGameObjectAction.EAttack && iTargetUnit != null )
                {
                    //StrategyDebug.bugfix.info( System.currentTimeMillis() + " follow target .." );

                    //follow target if any!
                    iTargetMovement.x   = iTargetUnit.iRect.x;
                    iTargetMovement.y   = iTargetUnit.iRect.y;

                    iTargetIndicator.x = iTargetUnit.iRect.x;
                    iTargetIndicator.y = iTargetUnit.iRect.y;
                }
            }

            //switch movement
            {
                switch ( iAction )
                {
                    case EMove:
                    case EAttack:
                    case EHarvest:
                    {
                        //StrategyDebug.bugfix.info( "update move" );

                        //check if movement shall occur
                        if ( iTargetMovement.x != -1 || iTargetMovement.y != -1 )
                        {
                            //StrategyDebug.bugfix.info( "update move 1" );

                            //get target angle and move speed
                            //StrategyDebug.bugfix.info( "target angle: " + iTargetAngle );
/*
                            //make move at least 1px !
                            if ( iMove.x > 0.0f && iMove.x <  1.0f ) iMove.x =  1.0f;
                            if ( iMove.x < 0.0f && iMove.x > -1.0f ) iMove.x = -1.0f;
                            if ( iMove.y > 0.0f && iMove.y <  1.0f ) iMove.y =  1.0f;
                            if ( iMove.y < 0.0f && iMove.y > -1.0f ) iMove.y = -1.0f;
*/
                            //StrategyDebug.bugfix.info( "move: " + iMove.x  + " " + iMove.y );

                            //remember current position
                            Vector2       oldPosition = null;

                            //animate to target X
                            oldPosition = new Vector2( iRect.x, iRect.y );
                            if ( iTargetMovement.x != -1 )
                            {
                                //StrategyDebug.bugfix.info( "update move 2" );

                                iRect.x += iMove.x;
                                if
                                (
                                        ( iMove.x == 0.0f && iRect.x == iTargetMovement.x )
                                    ||  ( iMove.x >  0.0f && iRect.x >= iTargetMovement.x )
                                    ||  ( iMove.x <  0.0f && iRect.x <= iTargetMovement.x )
                                )
                                {
                                    iRect.x = iTargetMovement.x;
                                    iTargetMovement.x = -1;
                                }

                                //check unit collision x
                                if ( StrategyLevel.current().checkUnitCollision( this ) )
                                {
                                    //StrategyDebug.bugfix.info( "COLLISION X OCCURED!" );

                                    //reset position if collision occured
                                    iRect.x     = oldPosition.x;
                                    //iTarget.x   = -1;
                                }
                                //check level collision x
                                else if ( StrategyLevel.current().checkLevelCollision( this ) )
                                {
                                    //reset position and stop x movement if collision occured
                                    iRect.x     = oldPosition.x;
                                    iTargetMovement.x   = -1;
                                }
                            }

                            //animate to target Y
                            oldPosition = new Vector2( iRect.x, iRect.y );
                            if ( iTargetMovement.y != -1 )
                            {
                                iRect.y += iMove.y;

                                //StrategyDebug.bugfix.info( " iRect.y is now  " + iRect.y + " targetMovement " + iTargetMovement.y + " move y " + iMove.y   );

                                if
                                (
                                        ( iMove.y == 0.0f && iRect.y == iTargetMovement.y )
                                    ||  ( iMove.y >  0.0f && iRect.y >= iTargetMovement.y )
                                    ||  ( iMove.y <  0.0f && iRect.y <= iTargetMovement.y )
                                )
                                {
                                    //StrategyDebug.bugfix.info( "REACHED TARGET Y !!!" );

                                    iRect.y = iTargetMovement.y;
                                    iTargetMovement.y = -1;
                                }

                                //check unit collision y
                                if ( StrategyLevel.current().checkUnitCollision( this ) )
                                {
                                    //StrategyDebug.bugfix.info( "COLLISION Y OCCURED!" );


                                    //reset position if collision occured
                                    iRect.y     = oldPosition.y;
                                    //iTarget.y   = -1;
                                }
                                //check level collision y
                                else if ( StrategyLevel.current().checkLevelCollision( this ) )
                                {
                                    //reset position and stop x movement if collision occured
                                    iRect.y     = oldPosition.y;
                                    iTargetMovement.y   = -1;
                                }
                            }

                            //check if target has been reached now
                            //StrategyDebug.bugfix.info( " target reached? " + iTargetMovement.x  + " " + iTargetMovement.y + "    " + iRect.x + "  " + iRect.y );

                            if ( iTargetMovement.x == -1 && iTargetMovement.y == -1 )
                            {
                                //StrategyDebug.bugfix.info( "ASSIGN NEXT WP" );

                                //assign next waypoint - check if this was the last waypoint
                                if ( assignNextWaypoint() )
                                {
                                    iAction = StrategyGameObjectAction.EWait;
                                    StrategyLevel.current().updateHUDButtonsForUnit( this );
                                }

                                //StrategyDebug.bugfix.info( "reached target" );
                            }
                        }
                        break;
                    }

                    case ENone:
                    case EWait:
                    case EEliminate:
                    case EBuildSoldier1:
                    {
                        //don't move
                        break;
                    }
                }
            }

            //perform attack
            {
                if
                (
                        iTargetUnit != null
                    &&  ( iAction == StrategyGameObjectAction.EAttack || iAction == StrategyGameObjectAction.EWait )
                    &&  isAfterShotTimerIdle()
                )
                {
                    //check shoot range
                    Vector2 ownXY               = new Vector2( iRect.x + iRect.width / 2, iRect.y + iRect.height / 2 );
                    Vector2 targetXY            = new Vector2( iTargetUnit.iRect.x + iTargetUnit.iRect.width / 2, iTargetUnit.iRect.y + iTargetUnit.iRect.height / 2 );
/*
                    float   distanceToTarget    = ownXY.dst( targetXY );
                    StrategyDebug.bugfix.info( "DISTANCE: " + distanceToTarget );
                    if ( distanceToTarget < type.iShootDistance )
*/
                    if ( LibMathGeometry.isInRange( ownXY, targetXY, type.iShootDistance ) )
                    {
                        //StrategyDebug.bugfix.info( "FIRE AT UNIT !!" );

                        //harm player - prune target if dead
                        iTargetUnit.hurt( type.iDamage );
                        if ( iTargetUnit.isDead() )
                        {
                            //StrategyDebug.bugfix.info( "UNIT DIES - FORGET TARGET!!" );
                            iTargetUnit = null;
                        }

                        //set after shot delay
                        startAfterShotTimer();
                    }
                }
            }

            //animate on move
            if ( iAction == StrategyGameObjectAction.EMove )
            {
                if ( ++iCurrentMoveFrameDelay >= StrategySettings.General.UNIT_MOVE_FRAME_DELAY )
                {
                    iCurrentMoveFrameDelay = 0;

                    //next frame
                    ++iCurrentMoveFrameIndex;

                    //clip frame
                    if ( iCurrentMoveFrameIndex >= ( (StrategyUnitType)iType ).iImagesMove.length )
                    {
                        iCurrentMoveFrameIndex = 0;
                    }
                }
            }
        }

        @Override
        public final void moveTo( float mapClickX, float mapClickY, StrategyGameObjectAction aAction )
        {
            //StrategyDebug.bugfix.info( "MoveTo" );

            //assign action for this movement
            iAction = aAction;

            //reset delay
            iCurrentMoveFrameDelay = 0;

            //update HUD buttons
            StrategyLevel.current().updateHUDButtonsForUnit( this );


            //check way-find-algo
            Rectangle   bounds  = StrategyLevel.current().getBounds();
            Vector2     dst     = new Vector2();
            dst.x               = mapClickX - ( iType.getUnitTextureRegion().getTexture().getWidth()  / 2 );
            dst.y               = mapClickY - ( iType.getUnitTextureRegion().getTexture().getHeight() / 2 );

            //clip map click
            if ( dst.x < 0                                                                      ) dst.x = 0;
            if ( dst.x > bounds.width  - iType.getUnitTextureRegion().getTexture().getWidth()   ) dst.x = bounds.width  - iType.getUnitTextureRegion().getTexture().getWidth();
            if ( dst.y < 0                                                                      ) dst.y = 0;
            if ( dst.y > bounds.height - iType.getUnitTextureRegion().getTexture().getHeight()  ) dst.y = bounds.height - iType.getUnitTextureRegion().getTexture().getHeight();

            //return the shortest waypoints to reach the target WITHOUT collision
            iCurrentWaypoints = StrategyLevel.current().getShortestWaypointsToTarget( this, dst );



/*
if ( false )
{
    iCurrentWaypoints = new Vector2[]
    {
        new Vector2( iRect.getX(), iRect.getY() ),
        new Vector2( dst.x, dst.y ),
    };
}
*/

StrategyDebug.bugfix.info( "Number of waypoints: [" + iCurrentWaypoints.length + "]" );

            //check impossibility
            if ( iCurrentWaypoints.length == 0 )
            {
                StrategyDebug.bugfix.info( "Received 0 waypoints - no non-colliding route to target" );

                iAction = StrategyGameObjectAction.EWait;
                StrategyLevel.current().updateHUDButtonsForUnit( this );
            }
            else
            {
                //StrategyDebug.bugfix.info( " Received [" + iCurrentWaypoints.length + "] shortest waypoints" );

                //skip 1st node ( 1st node is source )
                iCurrentWaypointIndex = 0;
                assignNextWaypoint();

                iTargetIndicator.x = dst.x;
                iTargetIndicator.y = dst.y;

                //clip target inside level bounds
                if ( iTargetMovement.x < 0                                                  ) iTargetMovement.x = 0;
                if ( iTargetMovement.x > bounds.width  - iType.getUnitTextureRegion().getTexture().getWidth()   ) iTargetMovement.x = bounds.width  - iType.getUnitTextureRegion().getTexture().getWidth();
                if ( iTargetMovement.y < 0                                                  ) iTargetMovement.y = 0;
                if ( iTargetMovement.y > bounds.height - iType.getUnitTextureRegion().getTexture().getHeight()  ) iTargetMovement.y = bounds.height - iType.getUnitTextureRegion().getTexture().getHeight();
            }
        }

        @Override
        public final void performWait()
        {
            iAction     = StrategyGameObjectAction.EWait;
            iTargetMovement.x   = -1;
            iTargetMovement.y   = -1;
        }

        public final boolean assignNextWaypoint()
        {
            //assign next waypoint
            ++iCurrentWaypointIndex;

            //StrategyDebug.bugfix.info( "assignNextWaypoint .. [" + iCurrentWaypointIndex + "]" );

            if ( iCurrentWaypointIndex < iCurrentWaypoints.length )
            {
                iTargetMovement.x        = iCurrentWaypoints[ iCurrentWaypointIndex ].x;
                iTargetMovement.y        = iCurrentWaypoints[ iCurrentWaypointIndex ].y;

                //calc angle to target
                iTargetAngle    = LibMath.getAngleCorrect( iRect.x, iRect.y, iTargetMovement.x, iTargetMovement.y );
                iMove           = LibMath.getDistantPoint( iTargetAngle, ( (StrategyUnitType)iType ).iSpeed );

                //StrategyDebug.bugfix.info( " assigned next waypoint [" + iTargetMovement.x + "," + iTargetMovement.y + "] targetAngle [" + iTargetAngle + "]   MOVEMENT: [" + iMove.x + "][" + iMove.y + "]" );

                return false;
            }

            return true;
        }
    }
