/*  $Id: StrategyFont.java 133 2012-08-06 17:31:04Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.ui;

    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.files.*;
    import  com.badlogic.gdx.graphics.*;
    import  com.badlogic.gdx.graphics.Texture.TextureFilter;
    import  com.badlogic.gdx.graphics.g2d.*;

    /***********************************************************************************************
    *   The font system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    public class StrategyFont
    {
        public          static          StrategyFont    presidentBlack          = null;
        public          static          StrategyFont    presidentWhite          = null;

        public                          BitmapFont      iFont                   = null;

        public StrategyFont( String urlFnt, String urlBmp )
        {
            //crashes for html5
            FileHandle      fnt = Gdx.files.internal( urlFnt );
            FileHandle      bmp = Gdx.files.internal( urlBmp );
            Texture         t   = new Texture( bmp );
            t.setFilter( TextureFilter.Linear, TextureFilter.Linear );  //enables smoother scaling ( a bit )
            TextureRegion   tr  = new TextureRegion( t );
            iFont = new BitmapFont( fnt, tr, false );
        }

        public static final void init()
        {
            //create font - don't flip!
            presidentBlack  = new StrategyFont( "data/font_president_black_fnt.fnt", "data/font_president_black_png.png" );
            presidentWhite  = new StrategyFont( "data/font_president_white_fnt.fnt", "data/font_president_white_png.png" );
        }

        public static final void dispose()
        {
            presidentBlack.iFont.dispose();
        }
    }
