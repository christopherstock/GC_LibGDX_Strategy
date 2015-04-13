/*  $Id: StrategySound.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.io;

    import  com.badlogic.gdx.*;
    import  com.badlogic.gdx.audio.*;

    /***********************************************************************************************
    *   The sound system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.5
    ***********************************************************************************************/
    public class StrategySound
    {
        private         static                      Sound                   dropSound                       = null;
        private         static                      Music                   rainMusic                       = null;

        public static final void test()
        {
            //load sounds
            {
                dropSound = Gdx.audio.newSound( Gdx.files.internal( "data/drop.wav" ) );
                rainMusic = Gdx.audio.newMusic( Gdx.files.internal( "data/rain.mp3" ) );
            }

            boolean testPlay = false;
            if ( testPlay )
            {
                //play bg sound
                {
                    rainMusic.setLooping( true );
                    rainMusic.play();
                }

                //play test sound
                {
                    dropSound.play();
                }
            }
        }

        public static final void dispose()
        {
            //dispose all sounds!

            if ( dropSound != null ) dropSound.dispose();
            if ( rainMusic != null ) rainMusic.dispose();
        }
    }
