/*  $Id: StrategyStage.java 179 2013-04-11 12:37:39Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.scenes.scene2d.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.game.unit.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public class StrategyStage
    {
        private             static          Stage           stage                   = null;

        public static final void init()
        {
            //create stage
            stage = new Stage( StrategyScreen.getScreenWidth(), StrategyScreen.getScreenHeight(), false );

            //set stage as input processor
            Gdx.input.setInputProcessor( stage );
        }

        public static void draw()
        {
            //draw stage
            stage.draw();
        }

        public static void setupStageWithButtons( StrategyGameObject unit, StrategyStageButtonType[] toSet, StrategyGameObjectAction unitAction, StrategyGameObjectAction currentMapAction )
        {
            int pos = 0;

            //remove all actors
            stage.clear();

/*
            //remove all buttons of all button kinds
            for ( StrategyStageButtonType kind : StrategyStageButtonType.values() )
            {
                stage.removeActor( kind.iButtonUnselected           );
                stage.removeActor( kind.iButtonSelected             );
                stage.removeActor( kind.iButtonActiveButSelectable  );
            }
*/
            //StrategyDebug.bugfix.info( "unit action " + unitAction );

            //browse all button kinds
            for ( StrategyStageButtonType kind : toSet )
            {
                StrategyStageButton button = null;

                if ( kind.iAssociatedAction == currentMapAction )
                {
                    //add selected button
                    button = kind.iButtonSelected;
                }
                else if ( kind.iAssociatedAction == unitAction )
                {
                    //add active button
                    button = kind.iButtonActiveButSelectable;
                }
                else
                {
                    //add unselected button
                    button = kind.iButtonUnselected;
                }

                //add button to stage
                button.setYForPosition( unit, pos++ );
                stage.addActor( button );
            }
        }
    }
