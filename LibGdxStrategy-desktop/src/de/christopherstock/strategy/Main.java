
    package de.christopherstock.strategy;

    import  com.badlogic.gdx.backends.lwjgl.LwjglApplication;
    import  com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

    public class Main
    {
        protected       static      LwjglApplication    appInstance         = null;

        public static void main( String[] args )
        {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

            cfg.title       = "LibGDX - Strategy " + StrategyVersion.getCurrentVersionDesc();
            cfg.useGL20     = false;
            cfg.width       = 854;
            cfg.height      = 480;
            cfg.resizable   = false;

            appInstance = new LwjglApplication( new Strategy(), cfg );
        }
    }
