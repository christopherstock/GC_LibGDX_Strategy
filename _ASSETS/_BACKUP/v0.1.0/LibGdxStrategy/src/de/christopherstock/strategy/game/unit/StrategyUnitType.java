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
    public enum StrategyUnitType implements GameObjectType
    {
        //              uniticon                                unitIconWalking                                                                                     avatar                              speed   caption         buttons                                                                                                                                                                                 afterShotDelay      energy      damage      viewDistance        shootDistance   ticksTilConstruction
        ESoldier1(      StrategyImage.EUnitSoldier1,            new StrategyImage[] { StrategyImage.EUnitSoldier1, },                                               StrategyImage.EAvatarSoldier1,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier2(      StrategyImage.EUnitSoldier2,            new StrategyImage[] { StrategyImage.EUnitSoldier2, },                                               StrategyImage.EAvatarSoldier2,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier3(      StrategyImage.EUnitSoldier3,            new StrategyImage[] { StrategyImage.EUnitSoldier3, },                                               StrategyImage.EAvatarSoldier3,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier4(      StrategyImage.EUnitSoldier4_frame1,     new StrategyImage[] { StrategyImage.EUnitSoldier4_frame1, StrategyImage.EUnitSoldier4_frame2, },    StrategyImage.EAvatarSoldier4,      3.0f,   "A-Soldier",    new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  50,                 8.0f,       1.0f,       800,                250,            500                    ),
        ESoldier5(      StrategyImage.EUnitSoldier5,            new StrategyImage[] { StrategyImage.EUnitSoldier5, },                                               StrategyImage.EAvatarSoldier5,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier6(      StrategyImage.EUnitSoldier6,            new StrategyImage[] { StrategyImage.EUnitSoldier6, },                                               StrategyImage.EAvatarSoldier6,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier7(      StrategyImage.EUnitSoldier7,            new StrategyImage[] { StrategyImage.EUnitSoldier7, },                                               StrategyImage.EAvatarSoldier7,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        ESoldier8(      StrategyImage.EUnitSoldier8,            new StrategyImage[] { StrategyImage.EUnitSoldier8, },                                               StrategyImage.EAvatarSoldier8,      1.0f,   "Soldiers",     new StrategyStageButtonType[] { StrategyStageButtonType.EAttack,    StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  100,                6.0f,       1.0f,       450,                150,            500                    ),
        EHarvester(     StrategyImage.EUnitHarvester,           new StrategyImage[] { StrategyImage.EUnitHarvester, },                                              StrategyImage.EAvatarHarvester,     2.0f,   "Harvester",    new StrategyStageButtonType[] { StrategyStageButtonType.EHarvest,   StrategyStageButtonType.EMove,      StrategyStageButtonType.EWait,      StrategyStageButtonType.EEliminate,     },  0,                  14.0f,      0.0f,       450,                0,              500                    ),

        ;

        protected           float                           iSpeed                              = 0.0f;
        protected           String                          iCaption                            = null;
        protected           StrategyStageButtonType[]       iButtonsToSet                       = null;
        protected           int                             iAfterShotDelayTicks                = 0;
        protected           float                           iStartupEnergy                      = 0;
        protected           float                           iDamage                             = 0;
        protected           int                             iViewDistance                       = 0;
        protected           int                             iShootDistance                      = 0;
        protected           int                             iTicksTillConstruction              = 0;

        private             StrategyImage                   iUnitImage                          = null;
        protected           StrategyImage[]                 iImagesMove                         = null;
        private             StrategyImage                   iAvatarImage                        = null;

        private StrategyUnitType( StrategyImage aUnitImage, StrategyImage[] aImagesMove, StrategyImage aAvatarImage, float aSpeed, String aCaption, StrategyStageButtonType[] aButtonsToSet, int aAfterShotDelayTicks, float aStartupEnergy, float aDamage, int aViewDistance, int aShootDistance, int aTicksTillConstruction )
        {
            iUnitImage              = aUnitImage;
            iImagesMove             = aImagesMove;
            iAvatarImage            = aAvatarImage;
            iSpeed                  = aSpeed;
            iCaption                = aCaption;
            iButtonsToSet           = aButtonsToSet;
            iAfterShotDelayTicks    = aAfterShotDelayTicks;
            iStartupEnergy          = aStartupEnergy;
            iDamage                 = aDamage;
            iViewDistance           = aViewDistance;
            iShootDistance          = aShootDistance;
            iTicksTillConstruction  = aTicksTillConstruction;
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
            return iTicksTillConstruction;
        }
    }
