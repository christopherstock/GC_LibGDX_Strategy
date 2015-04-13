/*  $Id: StrategyImage.java 179 2013-04-11 12:37:39Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.io;

    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.graphics.*;
    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.scenes.scene2d.utils.*;

    /***********************************************************************************************
    *   The image system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    public enum StrategyImage
    {
        EAvatarOverlay,
        EAvatarSoldier1,
        EAvatarSoldier2,
        EAvatarSoldier3,
        EAvatarSoldier4,
        EAvatarSoldier5,
        EAvatarSoldier6,
        EAvatarSoldier7,
        EAvatarSoldier8,
        EAvatarHarvester,
        EAvatarWaterTower,
        EAvatarBarracks,

        EBox2DBall,
        EBox2DBg,
        EBox2DGround,
        EBox2DBox,

        EBgHUD,

        EBuildingBarracks,
        EBuildingWaterTower,

        EButtonUnselect,
        EButtonHover,
        EButtonSelected,
        EButtonSelectedHover,
        EButtonActive,
        EButtonActiveHover,

        EGaugeEnergyBg,
        EGaugeEnergyFgHigh,
        EGaugeEnergyFgOk,
        EGaugeEnergyFgMedium,
        EGaugeEnergyFgLow,
        EGaugeEnergyFgDamaged,
        EGaugeProductionFg,

        EIndicatorUnitAttack,
        EIndicatorUnitMove,
        EIndicatorUnitSelectionPlayer,
        EIndicatorUnitSelectionEnemy,

        EIndicatorBuildingSelectionPlayer,
        EIndicatorBuildingSelectionEnemy,

        ETileSand1,
        ETileGrass1,
        ETileGrass2,
        ETileGrass3,
        ETileStone1,
        ETileTest,
        ETileTestBig,

        EUnitSoldier1,
        EUnitSoldier2,
        EUnitSoldier3,

        EUnitSoldier4_frame1,
        EUnitSoldier4_frame2,

        EUnitSoldier5,
        EUnitSoldier6,
        EUnitSoldier7,
        EUnitSoldier8,

        EUnitHarvester,

        ;

        private                     Texture                 iTexture                        = null;
        private                     TextureRegion           iTextureRegion                  = null;

        public static final void loadImages()
        {
            for ( StrategyImage img : values() )
            {
                img.loadImage();
            }
        }

        private final void loadImage()
        {
            iTexture        = new Texture( Gdx.files.internal( "data/" + this.toString().toLowerCase() + ".png" ) );
            iTextureRegion  = new TextureRegion( iTexture );

            //do NOT flip vertical because we now use the default coordinate-system with y up!
            //iTextureRegion.flip( false, true );
        }

        public static final void disposeImages()
        {
            for ( StrategyImage img : values() )
            {
                img.disposeImage();
            }
        }

        public final void disposeImage()
        {
            iTexture.dispose();
        }

        public final Sprite createNewSprite()
        {
            return new Sprite( iTextureRegion );
        }

        public final TextureRegion getTextureRegion()
        {
            return iTextureRegion;
        }

        public final TextureRegionDrawable getTextureRegionDrawable()
        {
            return new TextureRegionDrawable( iTextureRegion );
        }
    }
