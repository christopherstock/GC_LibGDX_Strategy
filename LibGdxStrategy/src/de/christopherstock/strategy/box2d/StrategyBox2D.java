/*  $Id: StrategyBox2D.java 100 2012-08-01 22:20:05Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
    import  de.christopherstock.strategy.io.*;

    /**********************************************************************************************
    *   Ready to export.
    **********************************************************************************************/
    public class StrategyBox2D
    {
        public      static  final   int                             VIRTUAL_WIDTH       = 320;
        public      static  final   int                             VIRTUAL_HEIGHT      = 480;

        private     static  final   int                             BACK_WIDTH          = 320;
        private     static  final   int                             BACK_HEIGHT         = 480;

        private     static  final   int                             BALL_WIDTH          = 32;
        private     static  final   int                             BALL_HEIGHT         = 32;

        private     static          TextureRegion                   ballRegion          = null;
        private     static          TextureRegion                   groundRegion        = null;
        private     static          TextureRegion                   backRegion          = null;

        private     static          StrategyBox2DTextureWrapper     backTexture         = null;
        protected   static          StrategyBox2DBoxObjectManager   boxManager          = null;

        private     static          StrategyBox2DBoxRectObject      ground1             = null;
        private     static          StrategyBox2DBoxRectObject      ground2             = null;
        private     static          StrategyBox2DBoxRectObject      ground3             = null;

        private     static          StrategyBox2DBoxCircleObject    ball1               = null;
        private     static          StrategyBox2DBoxCircleObject    ball2               = null;

        public static final void init()
        {
            //load images
            backRegion      = StrategyImage.EBox2DBg.getTextureRegion();
            groundRegion    = StrategyImage.EBox2DGround.getTextureRegion();
            ballRegion      = StrategyImage.EBox2DBall.getTextureRegion();

            backTexture = new StrategyBox2DTextureWrapper( backRegion, new Vector2( VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2 ) );
            backTexture.setDimension( BACK_WIDTH, BACK_HEIGHT );

            boxManager = new StrategyBox2DBoxObjectManager();

            initGround();
            initObjects();
        }

        public static final void update( float dt )
        {
            boxManager.update( dt );
        }

        public static final void draw( SpriteBatch batch )
        {
            batch.disableBlending();
            backTexture.draw(batch);

            batch.enableBlending();
            boxManager.draw(batch);
        }

        public static final void dispose()
        {
            //Assets.Dispose();
            boxManager.dispose();
        }

        private static final void initObjects()
        {
            int gRadius = BALL_WIDTH/2;

            ball1 = new StrategyBox2DBoxCircleObject( boxManager.getNewObjectIndex(), 1, gRadius, BodyType.DynamicBody, 1, 1, 210, 400, 0, ballRegion );
            ball1.setTextureDimension( BALL_WIDTH, BALL_HEIGHT );

            ball2 = new StrategyBox2DBoxCircleObject( boxManager.getNewObjectIndex(), 1, gRadius, BodyType.DynamicBody, 1, 1, 200, 200, 0, ballRegion );
            ball2.setTextureDimension( BALL_WIDTH, BALL_HEIGHT );

            boxManager.addObject( ball1 );
            boxManager.addObject( ball2 );
        }

        private static final void initGround()
        {
            ground1 = new StrategyBox2DBoxRectObject(boxManager.getNewObjectIndex(), 1, 320, 80, BodyType.StaticBody, 1, 1, 160, 40, 0, groundRegion);
            ground1.setTextureDimension( 320, 80 );

            ground2 = new StrategyBox2DBoxRectObject(boxManager.getNewObjectIndex(), 1, 40, 800, BodyType.StaticBody, 1, 1, 0, 450, 0, groundRegion);
            ground2.setTextureDimension( 40, 800 );

            ground3 = new StrategyBox2DBoxRectObject(boxManager.getNewObjectIndex(), 1, 40, 800, BodyType.StaticBody, 1, 1, 300, 450, 0, groundRegion);
            ground3.setTextureDimension( 40, 800 );

            boxManager.addObject( ground1 );
            boxManager.addObject( ground2 );
            boxManager.addObject( ground3 );
        }
    }
