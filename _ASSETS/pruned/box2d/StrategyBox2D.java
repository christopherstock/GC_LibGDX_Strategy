/*  $Id: StrategyBox2D.java 155 2012-08-08 21:38:37Z jenetic.bytemare@gmail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  com.badlogic.gdx.physics.box2d.*;
    import  com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
    import  de.christopherstock.strategy.io.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.2
    **************************************************************************************/
    public class StrategyBox2D
    {
        static  final   float   WORLD_TO_BOX    = 0.01f;
        static  final   float   BOX_WORLD_TO    = 100f;

        static          World                   world           = null;
        static          Body                    body            = null;
        static          StrategyBox2DBoxObject  testObject1     = null;
        static          StrategyBox2DBoxObject  testObject2     = null;
        static          StrategyBox2DBoxObject  testObject3     = null;

        public static final void init()
        {
            world = new World( new Vector2( 0, -20 ),true );

            // ?
            world.setGravity( new Vector2( 2.0f, 2.0f ) );

            testObject1 = new StrategyBox2DBoxObject( StrategyImage.EAvatarHarvester.getSprite(), new Vector2( 100, 100 ), StrategyBox2DBoxObject.CIRCLE_OBJECT, world, 0, 0  );
            testObject2 = new StrategyBox2DBoxObject( StrategyImage.EAvatarHarvester.getSprite(), new Vector2( 150, 150 ), StrategyBox2DBoxObject.CIRCLE_OBJECT, world, 1, 0  );
            testObject3 = new StrategyBox2DBoxObject( StrategyImage.EAvatarHarvester.getSprite(), new Vector2( 100, 200 ), StrategyBox2DBoxObject.CIRCLE_OBJECT, world, 2, 0  );




        }

        public static final void draw( SpriteBatch batch )
        {
            testObject1.Draw( batch );
            testObject2.Draw( batch );
            testObject3.Draw( batch );
        }

        public static final void updateObjects()
        {
            testObject1.Update( 1.0f );
            testObject2.Update( 1.0f );
            testObject3.Update( 1.0f );
        }


        public static final float ConvertToBox( float x )
        {
            return x * WORLD_TO_BOX;
        }

        public void CreateBody(World aWorld,Vector2 pos,float angle)
        {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(ConvertToBox(pos.x),ConvertToBox(pos.y));
            bodyDef.angle=angle;
            body = aWorld.createBody(bodyDef);
        }

        protected void MakeRectFixture(float width,float height,BodyDef.BodyType bodyType, float density,float restitution, Vector2 pos,float angle)
        {
             PolygonShape bodyShape = new PolygonShape();

             float w=ConvertToBox(width/2f);
             float h=ConvertToBox(height/2f);
             bodyShape.setAsBox(w,h);

             FixtureDef fixtureDef=new FixtureDef();
             fixtureDef.density=density;
             fixtureDef.restitution=restitution;
             fixtureDef.shape=bodyShape;

             body.createFixture(fixtureDef);
             bodyShape.dispose();
         }

         void MakeCircleFixture(float radius,BodyDef.BodyType bodyType, float density,float restitution, Vector2 pos,float angle)
         {
             FixtureDef fixtureDef=new FixtureDef();
             fixtureDef.density=density;
             fixtureDef.restitution=restitution;
             fixtureDef.shape=new CircleShape();
             fixtureDef.shape.setRadius( ConvertToBox( radius ) );

             body.createFixture(fixtureDef);
             fixtureDef.shape.dispose();
         }

        static final float BOX_STEP=1/120f;
        static final int  BOX_VELOCITY_ITERATIONS=8;
        static final int BOX_POSITION_ITERATIONS=3;
        static float accumulator;
/*
        public static void Update(float dt)
        {
            accumulator+=dt;
            while(accumulator>BOX_STEP)
            {
                world.step(BOX_STEP,BOX_VELOCITY_ITERATIONS,BOX_POSITION_ITERATIONS);
                accumulator-=BOX_STEP;
            }

            updateObjects();
        }
*/
    }
