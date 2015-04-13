/*  $Id: StrategyBox2DBoxCircleObject.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  com.badlogic.gdx.graphics.g2d.TextureRegion;
    import  com.badlogic.gdx.math.Vector2;
    import  com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

    /**********************************************************************************************
    *   Ready to export.
    **********************************************************************************************/
    class StrategyBox2DBoxCircleObject extends StrategyBox2DBaseBoxObject
    {
        public StrategyBox2DBoxCircleObject( int bodyIndex, int collisionGroup, float radius, BodyType bodyType, float density, float restitution, float x, float y, float angle, TextureRegion texRegion ) 
        {
            super( bodyIndex, collisionGroup );

            Vector2 pos = new Vector2( x, y );
            makeBody( 0, 0, radius, bodyType, density, restitution, pos, angle );
            texture = new StrategyBox2DTextureWrapper( texRegion, pos );
            setTextureDimension( radius * 2, radius * 2 );
        }
    }
