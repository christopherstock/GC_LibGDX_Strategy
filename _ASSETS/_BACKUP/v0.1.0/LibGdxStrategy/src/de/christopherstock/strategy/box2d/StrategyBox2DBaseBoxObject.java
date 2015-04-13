/*  $Id: StrategyBox2DBaseBoxObject.java 103 2012-08-01 22:56:56Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  java.util.*;
    import  com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import  com.badlogic.gdx.math.MathUtils;
    import  com.badlogic.gdx.math.Vector2;
    import  com.badlogic.gdx.physics.box2d.Body;
    import  com.badlogic.gdx.physics.box2d.BodyDef;
    import  com.badlogic.gdx.physics.box2d.CircleShape;
    import  com.badlogic.gdx.physics.box2d.Fixture;
    import  com.badlogic.gdx.physics.box2d.FixtureDef;
    import  com.badlogic.gdx.physics.box2d.PolygonShape;
    import  com.badlogic.gdx.physics.box2d.World;

    /**********************************************************************************************
    *   The base class of all Box2D-objects.
    **********************************************************************************************/
    @SuppressWarnings( "unused" )
    abstract class StrategyBox2DBaseBoxObject
    {
        protected               Body                            body                = null;
        protected               StrategyBox2DBoxUserData        boxUserData         = null;
        public                  Vector2                         bodyWorldPosition   = null;
        public                  boolean                         isAlive             = false;
        public                  StrategyBox2DTextureWrapper     texture             = null;

        public StrategyBox2DBaseBoxObject( int bodyIndex,int collisionGroup )
        {
            isAlive             = true;
            bodyWorldPosition   = new Vector2();
            boxUserData         = new StrategyBox2DBoxUserData( bodyIndex, collisionGroup );
        }

        public void makeBody( float width, float height, float radius, BodyDef.BodyType bodyType, float density, float restitution, Vector2 pos, float angle )
        {
            World   world           = StrategyBox2D.boxManager.getWorld();
            BodyDef jumperBodyDef   = new BodyDef();
            jumperBodyDef.type      = bodyType;
            jumperBodyDef.position.set( StrategyBox2DBoxObjectManager.convertToBox( pos.x ), StrategyBox2DBoxObjectManager.convertToBox( pos.y ) );
            jumperBodyDef.angle = angle;

            body = world.createBody( jumperBodyDef );

            //boxes are defined by their "half width" and "half height", hence the 2 multiplier.
            if ( radius == 0 )
            {
                makeRectBody( width, height, bodyType, density, restitution, pos, angle );
            }
            else
            {
                makeCircleBody( radius, bodyType, density, restitution, pos, angle );
            }

            //the character should not ever spin around on impact.
            bodyWorldPosition.set
            (
                StrategyBox2DBoxObjectManager.convertToWorld( body.getPosition().x ),
                StrategyBox2DBoxObjectManager.convertToWorld( body.getPosition().y )
            );
        }

        public Vector2 getPosition()
        {
            return bodyWorldPosition;
        }

        void makeRectBody( float width, float height, BodyDef.BodyType bodyType, float density, float restitution, Vector2 pos, float angle )
        {
            float w = StrategyBox2DBoxObjectManager.convertToBox( width  / 2.0f );
            float h = StrategyBox2DBoxObjectManager.convertToBox( height / 2.0f );
            PolygonShape bodyShape = new PolygonShape();
            bodyShape.setAsBox( w, h );

            FixtureDef fixtureDef   = new FixtureDef();
            fixtureDef.density      = density;
            fixtureDef.restitution  = restitution;
            fixtureDef.shape        = bodyShape;

            body.createFixture( fixtureDef );
            bodyShape.dispose();
        }

        void makeCircleBody( float radius, BodyDef.BodyType bodyType, float density, float restitution, Vector2 pos, float angle )
        {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density      = density;
            fixtureDef.restitution  = restitution;
            fixtureDef.shape        = new CircleShape();
            fixtureDef.shape.setRadius( StrategyBox2DBoxObjectManager.convertToBox( radius ) );

            body.createFixture( fixtureDef );
            fixtureDef.shape.dispose();
        }

        public void destroyBody()
        {
            if ( body != null )
            {
                isAlive = false;
                StrategyBox2D.boxManager.getWorld().destroyBody( body );
                body = null;
            }
        }

        public boolean IsClicked(float wx,float wy)
        {
            List<Fixture> flist=body.getFixtureList();
            boolean present=false;
            wx=StrategyBox2DBoxObjectManager.convertToBox(wx);
            wy=StrategyBox2DBoxObjectManager.convertToBox(wy);
            for(Fixture f:flist){
                present=f.testPoint(wx, wy);
                if(present)
                    return present;
            }
            return present;
        }

        public void updateWorldPosition()
        {
            bodyWorldPosition.set
            (
                StrategyBox2DBoxObjectManager.convertToWorld( body.getPosition().x ),
                StrategyBox2DBoxObjectManager.convertToWorld( body.getPosition().y )
            );
        }

        public void setPosition(float wx,float wy)
        {
            wx=StrategyBox2DBoxObjectManager.convertToBox(wx);
            wy=StrategyBox2DBoxObjectManager.convertToBox(wy);
            float angle=body.getAngle();
            body.setTransform(wx, wy, angle);
            updateWorldPosition();
        }

        public void SetTransform(StrategyBox2DBaseBoxObject bbo)
        {
            body.setTransform(bbo.GetBoxPosition(),bbo.GetAngle());
            updateWorldPosition();
        }

        public Vector2 GetBoxPosition()
        {
            return body.getPosition();
        }

        public float GetAngle()
        {
            return body.getAngle();
        }

        public int getBoxIndex()
        {
            return boxUserData.bodyIndex;
        }

        public int getBoxCollisionGroup()
        {
            return boxUserData.bodyCollisionGroup;
        }

        public void setPosition( Vector2 v )
        {
            setPosition( v.x, v.y );
        }

        public void update( float dt )
        {
            if ( isAlive )
            {
                updateWorldPosition();
                texture.setPosition( bodyWorldPosition );
                texture.setRotation( getBodyRotationInDegrees() );
            }
        }

        public void setUserData( int index )
        {
            boxUserData.setIndex( index );
        }

        public void setBodyCollisionType( int group )
        {
            boxUserData.setCollisionGroup( group );
        }

        public final void draw( SpriteBatch sp )
        {
            if ( isAlive )
            {
                texture.draw( sp );
            }
        }

        public float getBodyRotationInDegrees()
        {
            return ( body.getAngle() * MathUtils.radiansToDegrees );
        }

        public void setTextureDimension( float width, float height )
        {
            texture.setDimension( width, height );
        }
    }
