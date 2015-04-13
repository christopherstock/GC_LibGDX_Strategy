/*  $Id: StrategyTile.java 162 2012-11-17 19:14:27Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  de.christopherstock.strategy.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.lib.math.*;

    /**************************************************************************************
    *   Represents one tile of a level.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    class StrategyTile
    {
        private                 StrategyTileType                iKind                   = null;
        private                 TextureRegion                   iTextureRegion          = null;

        public StrategyTile( StrategyTileType aKind )
        {
            iKind           = aKind;

            switch ( iKind )
            {
                case ESand:
                {
                    iTextureRegion    = StrategyImage.ETileSand1.getTextureRegion();
                    break;
                }

                case EStone:
                {
                    iTextureRegion    = StrategyImage.ETileStone1.getTextureRegion();
                    break;
                }

                case EGrass:
                {
                    LibMath.col2f3( 0xff00ff00 );

                    switch ( LibMath.getRandom( 0, 2 ) )
                    {
                        case 0:     iTextureRegion    = StrategyImage.ETileGrass1.getTextureRegion();   break;
                        case 1:     iTextureRegion    = StrategyImage.ETileGrass2.getTextureRegion();   break;
                        case 2:     iTextureRegion    = StrategyImage.ETileGrass3.getTextureRegion();   break;
                    }
                    break;
                }
            }
        }

        public final void draw( SpriteBatch batch, float drawX, float drawY )
        {
            //draw tile
            batch.draw( iTextureRegion, drawX, drawY - iTextureRegion.getTexture().getHeight() );

            //draw test tile
            if ( StrategyDebug.DRAW_TEST_TILES_MAP )
            {
                batch.draw( StrategyImage.ETileTest.getTextureRegion(), drawX, drawY - StrategyImage.ETileTest.getTextureRegion().getTexture().getHeight() );
            }
        }
    }
