/*  $Id: StrategyLevel.java 165 2012-11-19 19:00:41Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game;

    import  java.util.*;
    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.graphics.*;
    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.StrategyDebug;
    import  de.christopherstock.strategy.StrategySettings.HUD;
    import  de.christopherstock.strategy.box2d.*;
    import  de.christopherstock.strategy.game.unit.*;
    import  de.christopherstock.strategy.game.unit.StrategyGameObject.*;
    import  de.christopherstock.strategy.lib.math.*;
    import  de.christopherstock.strategy.lib.math.LibMathFloydWarshall.Edge;
    import  de.christopherstock.strategy.ui.*;

    /**************************************************************************************
    *   Represents one game level.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyLevel
    {
        private     static              StrategyLevel                       currentLevel        = null;

        private     static              Vector<LibMathFloydWarshall.Node>   debugAllNodes       = new Vector<LibMathFloydWarshall.Node>();
        private     static              Vector<Vector2>                     debugAllWayPoints   = new Vector<Vector2>();
        private     static              Vector<LibMathFloydWarshall.Edge>   debugAllEdgesRed    = new Vector<LibMathFloydWarshall.Edge>();
        private     static              Vector<LibMathFloydWarshall.Edge>   debugAllEdgesGreen  = new Vector<LibMathFloydWarshall.Edge>();

        private                         StrategyLevelMap                    iMap                = null;
        public                          Vector<StrategyGameObject>          iUnits              = null;
        public                          Vector<StrategyGameObject>          iUnitsToAdd         = null;
        private                         StrategyGameObject                  iSelectedUnit       = null;
        private                         StrategyGameObjectAction            iSelectedAction     = StrategyGameObjectAction.ENone;

        public static final void init()
        {
            currentLevel = new StrategyLevel();
        }

        private StrategyLevel()
        {
            iMap        = new StrategyLevelMap();

            iUnits      = new Vector<StrategyGameObject>();
            iUnitsToAdd = new Vector<StrategyGameObject>();

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier1,             0,      0,      Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier5,             100,    0,      Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier6,             100,    80,     Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             20,     200,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyBuilding(   StrategyBuildingType.EWaterTower,       470,    270,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyBuilding(   StrategyBuildingType.EBarracks,         270,    70,     Party.EPlayer   ) );
/*
          //iUnits.addElement(  new StrategyUnit( StrategyUnitKind.ESoldier1,   0,      0,      UnitParty.EPlayer   ) );
          //iUnits.addElement(  new StrategyUnit( StrategyUnitKind.ESoldier1,   160,    0,      UnitParty.EPlayer   ) );

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             100,     400,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             200,     400,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             300,     400,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             400,     400,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             500,     400,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             600,     400,    Party.EPlayer   ) );

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             100,     500,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             200,     500,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             300,     500,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             400,     500,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             500,     500,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             600,     500,    Party.EPlayer   ) );

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             100,     600,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             200,     600,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             300,     600,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             400,     600,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             500,     600,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             600,     600,    Party.EPlayer   ) );

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             100,     700,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             200,     700,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             300,     700,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             400,     700,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             500,     700,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             600,     700,    Party.EPlayer   ) );

            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             100,     800,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             200,     800,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             300,     800,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             400,     800,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             500,     800,    Party.EPlayer   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier4,             600,     800,    Party.EPlayer   ) );
*/

            //iUnits.addElement(  new StrategyUnit( StrategyUnitType.ESoldier5,   100,    100,    UnitParty.EPlayer   ) );
            //iUnits.addElement(  new StrategyUnit( StrategyUnitType.ESoldier6,   200,    100,    UnitParty.EPlayer   ) );
            //iUnits.addElement(  new StrategyUnit( StrategyUnitType.ESoldier7,   0,      200,    UnitParty.EPlayer   ) );
            //iUnits.addElement(  new StrategyUnit( StrategyUnitType.ESoldier8,   100,    200,    UnitParty.EPlayer   ) );
/*
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier1,     600,    800,     Party.EEnemy   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier1,     700,    900,     Party.EEnemy   ) );
            iUnits.addElement(  new StrategyUnit(       StrategyUnitType.ESoldier1,     700,    1000,    Party.EEnemy   ) );
*/
        }

        public final void draw( SpriteBatch batch )
        {
            //draw map
            iMap.draw( batch );

            //draw units
            drawUnits( batch );

            //draw debug
            if ( StrategyDebug.DRAW_WAYPOINT_EDGES )
            {
                drawDebug( batch );
            }
        }

        public static final StrategyLevel current()
        {
            return currentLevel;
        }

        public final void update()
        {
            //animate units
            for ( StrategyGameObject unit : iUnits )
            {
                unit.update( unit == iSelectedUnit );
            }

            //prune dead units
            for ( int i = iUnits.size() - 1; i >= 0; --i )
            {
                //check if unit is dead
                if ( iUnits.elementAt( i ).isDead() )
                {
                    //blur focus if this is the current selected unit
                    if ( iSelectedUnit == iUnits.elementAt( i ) )
                    {
                        blurFocus();
                    }

                    //remove element from stack
                    iUnits.removeElementAt( i );

                    //StrategyDebug.bugfix.info( ">>> PRUNE DEAD UNIT" );
                }
            }

            //add new units
            for ( StrategyGameObject newUnit : iUnitsToAdd )
            {
                iUnits.addElement( newUnit );
            }
            iUnitsToAdd.removeAllElements();

            //update box2d
            if ( HUD.BOX_2D_TEST )
            {
                StrategyBox2D.update( Gdx.graphics.getDeltaTime() );
            }
        }

        public final void clickMapWindow( int x, int y )
        {
            //clip click inside the map window
            if ( StrategyHUD.isInMapWindow( x, y ) )
            {
                //get clicked map location
                Vector2 clickedMap = iMap.getClickedMap( x, y );

                //get clicked tile location
                @SuppressWarnings( "unused" )
                Vector2 clickedTile = iMap.getClickedTile( x, y );

                //check unit selection
                boolean activeUnitClicked   = false;
                boolean unitSelected        = false;
                boolean keepTargetUnit      = false;
                for ( StrategyGameObject unit : iUnits )
                {
                    //check selection
                    if ( unit.checkSelection( clickedMap.x, clickedMap.y ) )
                    {
                        //only change if this unit is currently unselected
                        if ( unit == iSelectedUnit )
                        {
                            //check active unit as clicked
                            activeUnitClicked   = true;
                        }
                        else
                        {
                            //attack enemies
                            if ( iSelectedAction == StrategyGameObjectAction.EAttack && unit.isEnemy() )
                            {
                                //let figure attack
                                iSelectedUnit.setTargetUnit( unit );
                                keepTargetUnit = true;
                            }
                            else
                            {
                                //reset current action
                                iSelectedAction = StrategyGameObjectAction.ENone;

                                //set this unit as the current selected unit
                                iSelectedUnit   = unit;

                                //update HUD buttons for this unit
                                updateHUDButtonsForCurrentActiveUnit();

                                //flag as 'selected a unit'
                                unitSelected    = true;
                            }
                        }
                        break;
                    }
                }

                //check focus ditch
                {
                    boolean blurFocus = false;
                    if ( activeUnitClicked )
                    {
                        //leave focus if selected unit has been clicked
                        blurFocus = false;
                    }
                    else if ( !unitSelected && iSelectedAction == StrategyGameObjectAction.ENone )
                    {
                        blurFocus = true;
                    }
                    //check unit movement if no selection has been made and a unit is active
                    else if ( !unitSelected && iSelectedUnit != null )
                    {
                        //check the current active action
                        switch ( iSelectedAction )
                        {
                            case ENone:
                            case EWait:
                            case EEliminate:
                            case EBuildSoldier1:
                            {
                                //blur focus
                                blurFocus = true;
                                break;
                            }

                            case EMove:
                            {
                                //clear current action and move unit
                                iSelectedAction         = StrategyGameObjectAction.ENone;
                                iSelectedUnit.moveTo( clickedMap.x, clickedMap.y, StrategyGameObjectAction.EMove );
                                break;
                            }

                            case EAttack:
                            {
                                //clear current action and let unit attack
                                iSelectedAction         = StrategyGameObjectAction.ENone;
                                iSelectedUnit.moveTo( clickedMap.x, clickedMap.y, StrategyGameObjectAction.EAttack );

                                //ditch target unit if desired
                                if ( !keepTargetUnit )
                                {
                                    iSelectedUnit.setTargetUnit( null );
                                }
                                break;
                            }

                            case EHarvest:
                            {
                                //clear current action and let unit harvest
                                iSelectedAction         = StrategyGameObjectAction.ENone;
                                iSelectedUnit.moveTo( clickedMap.x, clickedMap.y, StrategyGameObjectAction.EHarvest );
                                break;
                            }
                        }
                    }

                    //blur selection if desired
                    if ( blurFocus )
                    {
                        blurFocus();
                    }
                }
            }
        }

        public final void blurFocus()
        {
            iSelectedUnit = null;
            iSelectedAction = StrategyGameObjectAction.ENone;
            StrategyStage.setupStageWithButtons( null, new StrategyStageButtonType[] {}, null, iSelectedAction );
        }

        /**************************************************************************************
        *   Checks if the given unit collides with one of the other units.
        *
        *   @param  checker The unit to check for a collision with all other units.
        *   @return         <code>true</code> if a collision occured.
        *                   <code>false</code> if no collision occured.
        **************************************************************************************/
        public final boolean checkUnitCollision( StrategyGameObject checker )
        {
            //browse all units
            for ( StrategyGameObject unit : iUnits )
            {
                //skip checker unit
                if ( unit == checker ) continue;
                if ( unit.checkCollision( checker ) ) return true;
            }

            return false;
        }

        public final boolean checkLevelCollision( StrategyUnit checker )
        {
            boolean ret = ( checker.isInside( iMap.getBounds() ) == false );
            return ret;
        }

        public final Rectangle getBounds()
        {
            return iMap.getBounds();
        }

        public final void drawCurrentUnitHUD( SpriteBatch batch )
        {
            //only if a unit is selected
            if ( iSelectedUnit != null )
            {
                iSelectedUnit.drawHUD( batch );
            }
        }

        public final void clickStageButton( StrategyStageButtonType kind )
        {
            switch ( kind )
            {
                case EMove:
                {
                    iSelectedAction = StrategyGameObjectAction.EMove;
                    updateHUDButtonsForCurrentActiveUnit();
                    break;
                }

                case EAttack:
                {
                    iSelectedAction = StrategyGameObjectAction.EAttack;
                    updateHUDButtonsForCurrentActiveUnit();
                    break;
                }

                case EWait:
                {
                    iSelectedAction = StrategyGameObjectAction.ENone;
                    iSelectedUnit.performWait();
                    updateHUDButtonsForCurrentActiveUnit();
                    break;
                }

                case EHarvest:
                {
                    iSelectedAction = StrategyGameObjectAction.EHarvest;
                    updateHUDButtonsForCurrentActiveUnit();
                    break;
                }

                case EEliminate:
                {
                    //prune current unit
                    iUnits.removeElement( iSelectedUnit );

                    //blur selection
                    blurFocus();
                    break;
                }

                case EBuildSoldier1:
                {
                    //only build if idle!
                    if ( iSelectedUnit.iAction == null || iSelectedUnit.iAction == StrategyGameObjectAction.ENone )
                    {
                        iSelectedUnit.iAction                                   = StrategyGameObjectAction.EBuildSoldier1;
                        ( (StrategyBuilding)iSelectedUnit ).iUnitTypeToBuild    = StrategyUnitType.ESoldier4;   //TODO to type
                        ( (StrategyBuilding)iSelectedUnit ).iCreationTimer      = ( (StrategyBuilding)iSelectedUnit ).iUnitTypeToBuild.getConstructionTime();
                        updateHUDButtonsForCurrentActiveUnit();
                    }
                    break;
                }

                case ERepair:
                {
                    break;
                }
            }
        }

        public final void updateHUDButtonsForUnit( StrategyGameObject unit )
        {
            if ( iSelectedUnit == unit )
            {
                updateHUDButtonsForCurrentActiveUnit();
            }
        }

        public final void updateHUDButtonsForCurrentActiveUnit()
        {
            if ( iSelectedUnit != null )
            {
                iSelectedUnit.updateHUDButtons( iSelectedUnit, iSelectedUnit.getAction(), iSelectedAction, iSelectedUnit.getButtonsToSet() );
            }
        }

        private final void drawUnits( SpriteBatch batch )
        {
            float mapLeft = iMap.getMapLeft();
            float mapTop  = iMap.getMapTop();

            //draw all enemy units
            for ( StrategyGameObject unit : iUnits )
            {
                if ( unit.isEnemy() )
                {
                    //draw indicators and unit
                    unit.drawIndicators( batch, mapLeft, mapTop );
                    unit.draw( batch, mapLeft, mapTop );
                }
            }

            //draw all friendly units
            for ( StrategyGameObject unit : iUnits )
            {
                if ( unit.isFriend() )
                {
                    //draw indicators and unit
                    unit.drawIndicators( batch, mapLeft, mapTop );
                    unit.draw( batch, mapLeft, mapTop );
                }
            }
        }

        private final void drawDebug( SpriteBatch batch )
        {
            float mapLeft = iMap.getMapLeft();
            float mapTop  = iMap.getMapTop();
            for ( LibMathFloydWarshall.Node node : debugAllNodes )
            {
                LibDrawing.fillRect( batch, mapLeft + node.iPos.x, mapTop - node.iPos.y, 10, 10, Color.BLUE );
            }
            for ( Vector2 node : debugAllWayPoints )
            {
                LibDrawing.fillRect( batch, mapLeft + node.x, mapTop - node.y, 10, 10, Color.YELLOW );
            }
            for ( LibMathFloydWarshall.Edge edge : debugAllEdgesRed )
            {
                //if ( edge.collided ) // useless check - all are colliding!
                {
                    LibDrawing.fillLine( batch, mapLeft, mapTop, edge.from.iPos, edge.to.iPos, Color.RED  );
                }
            }
            for ( LibMathFloydWarshall.Edge edge : debugAllEdgesGreen )
            {
                //if ( !edge.collided ) // useless check - all are non-colliding
                {
                    LibDrawing.fillLine( batch, mapLeft, mapTop, edge.from.iPos, edge.to.iPos, Color.GREEN );
                }
            }
        }

        public final void scrollBy( int firstX, int firstY, int deltaX, int deltaY )
        {
            iMap.scrollBy( firstX, firstY, deltaX, deltaY );
        }

        public final boolean isSelectedUnit( StrategyUnit unit )
        {
            return ( iSelectedUnit == unit );
        }

        public final StrategyGameObject getNearestPlayerUnit( StrategyGameObject srcUnit, float maxDistance, Party fromParty )
        {
            return srcUnit.getNearestUnit( iUnits, maxDistance, fromParty );
        }

        /**************************************************************************************
        *   Delivers the shortest waypoints to reach the given target collision-free.
        *
        *   @param  checker The unit to check for a collision with all other units.
        *   @return         <code>true</code> if a collision occured.
        *                   <code>false</code> if no collision occured.
        **************************************************************************************/
        public final Vector2[] getShortestWaypointsToTarget( StrategyGameObject unit, Vector2 target )
        {
            long    startTime                   = System.currentTimeMillis();
            int     collisionTestsDone          = 0;
            int     collisionTestsSkipped       = 0;

            // TODO check target point for colliding unit!

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 1 " + ( System.currentTimeMillis() - startTime ) );

            //gather all nodes for way-find-algo
            Vector<LibMathFloydWarshall.Node> allNodes = new Vector<LibMathFloydWarshall.Node>();
            Vector<LibMathFloydWarshall.Edge> allWalkableEdges = new Vector<LibMathFloydWarshall.Edge>();

            //update debug points
            debugAllEdgesRed    = new Vector<LibMathFloydWarshall.Edge>();
            debugAllEdgesGreen  = new Vector<LibMathFloydWarshall.Edge>();

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 2 " + ( System.currentTimeMillis() - startTime ) );

            //add points
            {
                //add src point
                allNodes.addElement( new LibMathFloydWarshall.Node( allNodes.size(), new Vector2( unit.iRect.getX(), unit.iRect.getY() ), unit ) );

                float MAGIC = 1.0f;

                //collect all waypoints around all units
                for ( StrategyGameObject u : iUnits )
                {
                    //skip moving unit
                    if ( u == unit ) continue;

                    //add all walkable waypoints surrounding this unit
                    allNodes.addElement( new LibMathFloydWarshall.Node( allNodes.size(), new Vector2( u.iRect.x - unit.iRect.width - MAGIC      /*  - unit.iRect.width */,     u.iRect.y - unit.iRect.height    - MAGIC     /* - unit.iRect.height */    ), u ) );
                    allNodes.addElement( new LibMathFloydWarshall.Node( allNodes.size(), new Vector2( u.iRect.x - unit.iRect.width - MAGIC      /*  - unit.iRect.width */,     u.iRect.y + u.iRect.height       + MAGIC     /* + unit.iRect.height */    ), u ) );
                    allNodes.addElement( new LibMathFloydWarshall.Node( allNodes.size(), new Vector2( u.iRect.x + u.iRect.width + MAGIC         /*  + unit.iRect.width */,     u.iRect.y - unit.iRect.height    - MAGIC     /* - unit.iRect.height */    ), u ) );
                    allNodes.addElement( new LibMathFloydWarshall.Node( allNodes.size(), new Vector2( u.iRect.x + u.iRect.width + MAGIC         /*  + unit.iRect.width */,     u.iRect.y + u.iRect.height       + MAGIC     /* + unit.iRect.height */    ), u ) );
                }

                //add target point
                LibMathFloydWarshall.Node node = new LibMathFloydWarshall.Node( allNodes.size(), target, null );
                allNodes.addElement( node );
            }

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3 " + ( System.currentTimeMillis() - startTime ) );


            debugAllNodes = new Vector<LibMathFloydWarshall.Node>( allNodes );

            //create all edges - connect all points with all points
            for ( LibMathFloydWarshall.Node node : allNodes )
            {
                for ( LibMathFloydWarshall.Node node2 : allNodes )
                {
                    //add to stack
                    LibMathFloydWarshall.Edge newEdge = new LibMathFloydWarshall.Edge( node, node2, node.iPos.dst( node2.iPos ) );
                    allWalkableEdges.addElement( newEdge );
                }
            }

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.0.1 " + ( System.currentTimeMillis() - startTime ) );

            //browse all edges and calc collisions
            for ( int i = allWalkableEdges.size() - 1; i >= 0; --i )
            {
                //reference this edge
                Edge edge = allWalkableEdges.elementAt( i );

                //create this edge and check collision for this edge
                //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.1 " + ( System.currentTimeMillis() - startTime ) );

                //browse all units
                for ( StrategyGameObject unit2 : iUnits )
                {
                    //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.2 " + ( System.currentTimeMillis() - startTime ) );

                    //connect node1 to node2 // do NOT prune redundant connections! ( a-b, b-a ) Floyd Warshall needs them because nodes can be passed in both directions

                    //check if this unit sticks on the edge
                    if ( edge.from.iUnit == unit2 && edge.to.iUnit == unit2 )
                    {
                        //never collide lines on the own unit if axis match
                        if ( edge.from.iPos.x == edge.to.iPos.x || edge.from.iPos.y == edge.to.iPos.y )
                        {

                        }
                        else
                        {
                            edge.collided = true;
                            break;
                        }
                    }
                    else if ( unit2 == unit )
                    {
                        //never lines on the own player
                        continue;
                    }
                    else
                    {
                        //check if edge exists
                        boolean edgeExists = false;
/*
                        for ( LibMathFloydWarshall.Edge edge2 : allEdges )
                        {
                            if
                            (
                                    (   edge.from  == edge2.from  && edge.to  == edge2.to )
                                ||  (   edge2.from == edge.from   && edge2.to == edge.to )
                            )
                            {
                                edgeExists = true;
                                edge.collided = edge2.collided;
                                break;
                            }
                        }
*/
                        //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.2.5 " + ( System.currentTimeMillis() - startTime ) );


                        //add if not existent
                        if ( edgeExists )
                        {
                            //StrategyDebug.bugfix.info( "skip collision calc" );
                            ++collisionTestsSkipped;
                        }
                        else
                        {
                            //StrategyDebug.bugfix.info( "DONT skip collision calc" );

                            // TODO simplify

                            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.3 " + ( System.currentTimeMillis() - startTime ) );

                            //check edge collision with this unit ( with all four point lines ! )
                            Vector2 p1 = new Vector2( edge.from.iPos );
                            Vector2 p2 = new Vector2( edge.to.iPos   );
                            Vector2 p3 = new Vector2( edge.from.iPos );
                            Vector2 p4 = new Vector2( edge.to.iPos   );
                            Vector2 p5 = new Vector2( edge.from.iPos );
                            Vector2 p6 = new Vector2( edge.to.iPos   );
                            Vector2 p7 = new Vector2( edge.from.iPos );
                            Vector2 p8 = new Vector2( edge.to.iPos   );

                            //add 1.0f to all values!

                            p1.add( 1.0f, 1.0f );
                            p2.add( 1.0f, 1.0f );

                            p3.add( 1.0f, unit.iRect.height + 1.0f );
                            p4.add( 1.0f, unit.iRect.height + 1.0f );

                            p5.add( unit.iRect.width + 1, 1.0f );
                            p6.add( unit.iRect.width + 1, 1.0f );

                            p7.add( unit.iRect.width + 1.0f, unit.iRect.height + 1.0f );
                            p8.add( unit.iRect.width + 1.0f, unit.iRect.height + 1.0f );

                            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.4 " + ( System.currentTimeMillis() - startTime ) );

                            if
                            (
                                    unit2.checkCollision( p1, p2 )
                                ||  unit2.checkCollision( p3, p4 )
                                ||  unit2.checkCollision( p5, p6 )
                                ||  unit2.checkCollision( p7, p8 )
/*
||  unit2.checkCollision( newEdge.from.iPos.cpy().add( 0.0f,                -unit.iRect.height  ),       newEdge.to.iPos.cpy().add( 0.0f,                -unit.iRect.height  ) )
||  unit2.checkCollision( newEdge.from.iPos.cpy().add( unit.iRect.width,    0.0f                ),       newEdge.to.iPos.cpy().add( unit.iRect.width,    0.0f                ) )
||  unit2.checkCollision( newEdge.from.iPos.cpy().add( 0.0f,                -unit.iRect.height   ),       newEdge.to.iPos.cpy().add( 0.0f,                -unit.iRect.height   ) )
||  unit2.checkCollision( newEdge.from.iPos.cpy().add( unit.iRect.width,    -unit.iRect.height   ),       newEdge.to.iPos.cpy().add( unit.iRect.width,    -unit.iRect.height   ) )
*/
                            )
                            {
                                //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.5 " + ( System.currentTimeMillis() - startTime ) );

                                edge.collided = true;
                                ++collisionTestsDone;
                                break;
                            }

                            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 3.6 " + ( System.currentTimeMillis() - startTime ) );
                        }
                    }
                }
            }

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 4 " + ( System.currentTimeMillis() - startTime ) );

            StrategyDebug.bugfix.info( " Edges before pruning [" + allWalkableEdges.size() + "] collision tests done [" + collisionTestsDone + "] collision tests skipped [" + collisionTestsSkipped + "]" );

            //prune colliding lines
            for ( int i = allWalkableEdges.size() - 1; i >= 0; --i )
            {
                //add non-collided edges
                if ( allWalkableEdges.elementAt( i ).collided )
                {
                    debugAllEdgesRed.addElement( allWalkableEdges.elementAt( i ) );
                    allWalkableEdges.removeElementAt( i );
                }
                else
                {
                    debugAllEdgesGreen.addElement( allWalkableEdges.elementAt( i ) );
                }
            }

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 5 " + ( System.currentTimeMillis() - startTime ) );

            StrategyDebug.bugfix.info( " picked total points [" + allNodes.size() + "] non-colliding edges [" + allWalkableEdges.size() + "]" );

            //give points to floyd warshall
            LibMathFloydWarshall fw = new LibMathFloydWarshall( allNodes.toArray( new LibMathFloydWarshall.Node[] {} ), allWalkableEdges.toArray( new LibMathFloydWarshall.Edge[] {} ) );
            List<LibMathFloydWarshall.Node> shortestPath = fw.getShortestPath( allNodes.firstElement(), allNodes.lastElement() );

            //StrategyDebug.bugfix.info( "Floyd-Warshall profiling 6 " + ( System.currentTimeMillis() - startTime ) );

            //convert to Vector2
            Vector<Vector2> ret = new Vector<Vector2>();
            for ( LibMathFloydWarshall.Node sp : shortestPath )
            {
                ret.addElement( new Vector2( sp.iPos.x, sp.iPos.y ) );
            }

            debugAllWayPoints = ret;

            StrategyDebug.bugfix.info( "Thanks for using the new way-find-algo Floyd-Warshall. This took [" + ( System.currentTimeMillis() - startTime ) + "] ms" );

            return ret.toArray( new Vector2[] {} );
        }
    }
