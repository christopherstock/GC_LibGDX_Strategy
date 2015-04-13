/*  $Id: StrategyStageButtonType.java 179 2013-04-11 12:37:39Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game;

    import  de.christopherstock.strategy.game.unit.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.ui.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public enum StrategyStageButtonType
    {
        EHarvest(       "Harvest",          StrategyGameObjectAction.EHarvest             ),
        EMove(          "Move",             StrategyGameObjectAction.EMove                ),
        EAttack(        "Attack",           StrategyGameObjectAction.EAttack              ),
        EWait(          "Wait",             StrategyGameObjectAction.EWait                ),
        EEliminate(     "Eliminate",        StrategyGameObjectAction.EEliminate           ),

        EBuildSoldier1( "Soldier1",         StrategyGameObjectAction.EBuildSoldier1       ),
        ERepair(        "Repair",           StrategyGameObjectAction.ENone                ),
        ;

        public              StrategyStageButton                     iButtonUnselected           = null;
        public              StrategyStageButton                     iButtonSelected             = null;
        public              StrategyStageButton                     iButtonActiveButSelectable  = null;

        public              StrategyGameObjectAction                      iAssociatedAction           = null;

        public              String                                  iCaption                    = null;

        private StrategyStageButtonType( String aCaption, StrategyGameObjectAction aAssociatedAction )
        {
            iCaption            = aCaption;
            iAssociatedAction   = aAssociatedAction;
        }

        public static final void initAll()
        {
            for ( StrategyStageButtonType kind : values() )
            {
                kind.init();
            }
        }

        private final void init()
        {
            iButtonUnselected           = new StrategyStageButton( iCaption, this, StrategyImage.EButtonUnselect.getTextureRegionDrawable(),   StrategyImage.EButtonHover.getTextureRegionDrawable(),         StrategyImage.EButtonUnselect.getTextureRegionDrawable()   );
            iButtonSelected             = new StrategyStageButton( iCaption, this, StrategyImage.EButtonSelected.getTextureRegionDrawable(),   StrategyImage.EButtonSelectedHover.getTextureRegionDrawable(), StrategyImage.EButtonSelected.getTextureRegionDrawable()   );
            iButtonActiveButSelectable  = new StrategyStageButton( iCaption, this, StrategyImage.EButtonActive.getTextureRegionDrawable(),     StrategyImage.EButtonActiveHover.getTextureRegionDrawable(),   StrategyImage.EButtonActive.getTextureRegionDrawable()     );
        }

        public final void click()
        {
            StrategyLevel.current().clickStageButton( this );
        }
    }
