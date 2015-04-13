/*  $Id: StrategyUnitType.java 115 2012-08-02 20:56:16Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game.unit;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  de.christopherstock.strategy.game.*;
    import  de.christopherstock.strategy.game.unit.StrategyGameObject.*;
    import  de.christopherstock.strategy.io.*;

    /**************************************************************************************
    *   Represents one game unit.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    public enum StrategyBuildingType implements GameObjectType
    {
        //              uniticon                                avatar                                      caption         isFactory   buttons                                                                             afterShotDelay      energy      damage      viewDistance        shootDistance
        EBarracks(      StrategyImage.EBuildingBarracks,        StrategyImage.EAvatarBarracks,              "Barracks",     true,       new StrategyStageButtonType[] { StrategyStageButtonType.EBuildSoldier1, },          0,                  50.0f,      0.0f,       0,                  0                   ),
        EWaterTower(    StrategyImage.EBuildingWaterTower,      StrategyImage.EAvatarWaterTower,            "Water tower",  false,      new StrategyStageButtonType[] {},                                                   0,                  50.0f,      0.0f,       0,                  0                   ),

        ;

        protected           String                          iCaption                = null;
        protected           boolean                         iIsFactory              = false;
        protected           StrategyStageButtonType[]       iButtonsToSet           = null;
        protected           int                             iAfterShotDelayTicks    = 0;
        protected           float                           iStartupEnergy          = 0;
        protected           float                           iDamage                 = 0;
        protected           int                             iViewDistance           = 0;
        protected           int                             iShootDistance          = 0;

        private             StrategyImage                   iUnitImage              = null;
        private             StrategyImage                   iAvatarImage            = null;

        private StrategyBuildingType( StrategyImage aUnitImage, StrategyImage aAvatarImage, String aCaption, boolean aIsFactory, StrategyStageButtonType[] aButtonsToSet, int aAfterShotDelayTicks, float aStartupEnergy, float aDamage, int aViewDistance, int aShootDistance )
        {
            iUnitImage              = aUnitImage;
            iAvatarImage            = aAvatarImage;
            iCaption                = aCaption;
            iIsFactory              = aIsFactory;
            iButtonsToSet           = aButtonsToSet;
            iAfterShotDelayTicks    = aAfterShotDelayTicks;
            iStartupEnergy          = aStartupEnergy;
            iDamage                 = aDamage;
            iViewDistance           = aViewDistance;
            iShootDistance          = aShootDistance;
        }

        @Override
        public TextureRegion getUnitTextureRegion()
        {
            return iUnitImage.getTextureRegion();
        }

        @Override
        public TextureRegion getAvatarTextureRegion()
        {
            return iAvatarImage.getTextureRegion();
        }

        @Override
        public String getCaption()
        {
            return iCaption;
        }

        @Override
        public float getStartupEnergy()
        {
            return iStartupEnergy;
        }

        @Override
        public final StrategyStageButtonType[] getButtonsToSet()
        {
            return iButtonsToSet;
        }

        @Override
        public final int getConstructionTime()
        {
            return 0;
        }
    }
