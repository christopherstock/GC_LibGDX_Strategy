/*  $Id: StrategyStageButtonType.java 123 2012-08-04 02:11:35Z jenetic.bytemare@googlemail.com $
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
            iButtonUnselected           = new StrategyStageButton( iCaption, this, StrategyImage.EButtonUnselect.getTextureRegion(),   StrategyImage.EButtonHover.getTextureRegion(),         StrategyImage.EButtonUnselect.getTextureRegion()   );
            iButtonSelected             = new StrategyStageButton( iCaption, this, StrategyImage.EButtonSelected.getTextureRegion(),   StrategyImage.EButtonSelectedHover.getTextureRegion(), StrategyImage.EButtonSelected.getTextureRegion()   );
            iButtonActiveButSelectable  = new StrategyStageButton( iCaption, this, StrategyImage.EButtonActive.getTextureRegion(),     StrategyImage.EButtonActiveHover.getTextureRegion(),   StrategyImage.EButtonActive.getTextureRegion()     );
        }

        public final void click()
        {
            StrategyLevel.current().clickStageButton( this );
        }
    }
