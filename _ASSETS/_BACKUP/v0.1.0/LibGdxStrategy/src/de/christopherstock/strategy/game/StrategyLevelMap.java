/*  $Id: StrategyLevelMap.java 165 2012-11-19 19:00:41Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.game;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  de.christopherstock.strategy.io.*;
    import  de.christopherstock.strategy.ui.*;

    /**************************************************************************************
    *   Represents the current level map.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    **************************************************************************************/
    class StrategyLevelMap
    {
        private                         Vector2                 iScroll             = null;
        private                         Vector2                 iOffset             = null;
        private                         Vector2                 iTileDimension      = null;
        private                         Rectangle               iBounds             = null;
        private                         StrategyTile[][]        iTiles              = null;
        private                         Vector2                 iTileCount          = null;
        private                         Vector2                 iMaxScroll          = null;
        private                         boolean                 iAllowScrollX       = false;
        private                         boolean                 iAllowScrollY       = false;

        public StrategyLevelMap()
        {
            iTiles = new StrategyTile[][]
            {
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.ESand  ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ),   },
                new StrategyTile[] {    new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EGrass ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ), new StrategyTile( StrategyTileType.EStone ),   },
            };

            iTileDimension  = new Vector2
            (
                StrategyImage.ETileSand1.getTextureRegion().getTexture().getWidth(),
                StrategyImage.ETileSand1.getTextureRegion().getTexture().getHeight()
            );

            iTileCount      = new Vector2
            (
                iTiles[ 0 ].length,
                iTiles.length
            );

            iScroll         = new Vector2();

            iBounds = new Rectangle
            (
                0,
                0,
                iTileCount.x * iTileDimension.x,
                iTileCount.y * iTileDimension.y
            );

            iMaxScroll      = new Vector2
            (
                (int)( iBounds.width  - StrategyHUD.getMapWindowWidth()     ),
                (int)( iBounds.height - StrategyHUD.getMapWindowHeight()    )
            );

            iAllowScrollX   = ( iMaxScroll.x > 0 );
            iAllowScrollY   = ( iMaxScroll.y > 0 );

            iOffset         = new Vector2();

            if ( !iAllowScrollX )
            {
                iOffset.x   = (int)( ( StrategyHUD.getMapWindowWidth() - (int)iBounds.width ) / 2 );
            }

            if ( !iAllowScrollY )
            {
                iOffset.y   = (int)( ( StrategyHUD.getMapWindowHeight() - (int)iBounds.height ) / 2 );
            }
        }

        public final void draw( SpriteBatch batch )
        {
            float mapLeft = ( -iScroll.x + iOffset.x );
            float mapTop  = ( StrategyScreen.getScreenHeight() + iScroll.y + iOffset.y );

            //draw tiled bg
            drawTiles( batch, mapLeft, mapTop );
        }

        public final float getMapLeft()
        {
            float mapLeft = ( -iScroll.x + iOffset.x );
            return mapLeft;
        }

        public final float getMapTop()
        {
            float mapTop  = ( StrategyScreen.getScreenHeight() + iScroll.y + iOffset.y );
            return mapTop;
        }

        private final void drawTiles( SpriteBatch batch, float mapLeft, float mapTop )
        {
            float drawY = mapTop;
            for ( int tileY = 0; tileY < iTileCount.y; ++tileY )
            {
                float drawX = mapLeft;
                for ( int tileX = 0; tileX < iTileCount.x; ++tileX )
                {
                    iTiles[ tileY ][ tileX ].draw( batch, drawX, drawY );

                    drawX += iTileDimension.x;
                }
                drawY -= iTileDimension.y;
            }
        }

        public final void scrollBy( int firstX, int firstY, int deltaX, int deltaY )
        {
            //start position of the drag has to occur inside the map window
            if ( StrategyHUD.isInMapWindow( firstX, firstY ) )
            {
                //assign scroll
                if ( iAllowScrollX )
                {
                    iScroll.x -= deltaX;

                    //clip
                    if ( iScroll.x < 0 ) iScroll.x = 0;
                    else if ( iScroll.x > iMaxScroll.x ) iScroll.x = iMaxScroll.x;
                }

                if ( iAllowScrollY )
                {
                    iScroll.y -= deltaY;

                    //clip
                    if ( iScroll.y < 0 ) iScroll.y = 0;
                    else if ( iScroll.y > iMaxScroll.y ) iScroll.y = iMaxScroll.y;
                }

                //StrategyDebug.bugfix.info( "current scroll [" + iScroll.x + "][" + iScroll.y + "]" );
            }
        }

        public final Rectangle getBounds()
        {
            return iBounds;
        }

        public final Vector2 getClickedMap( int x, int y )
        {
            float mapClickX       = x - iOffset.x + iScroll.x;
            float mapClickY       = y - iOffset.y + iScroll.y;

            return new Vector2( mapClickX, mapClickY );
        }

        public final Vector2 getClickedTile( int x, int y )
        {
            Vector2 mapClick = getClickedMap( x, y );

            //get clicked map tile
            float clickedTileX    = mapClick.x / iTileDimension.x;
            float clickedTileY    = mapClick.y / iTileDimension.y;

            //StrategyDebug.bugfix.info( "clicked [" + mapClickX + "][" + mapClickY + "] tile [" + clickedTileX + "][" + clickedTileY + "]" );

            return new Vector2( clickedTileX, clickedTileY );
        }
    }
