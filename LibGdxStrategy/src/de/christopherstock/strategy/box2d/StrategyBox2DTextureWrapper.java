/*  $Id: StrategyBox2DTextureWrapper.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import  com.badlogic.gdx.graphics.Color;
    import  com.badlogic.gdx.graphics.Texture;
    import  com.badlogic.gdx.graphics.Texture.TextureFilter;
    import  com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import  com.badlogic.gdx.graphics.g2d.TextureRegion;
    import  com.badlogic.gdx.math.Vector2;

    /**********************************************************************************************
    *   Ready to export.
    **********************************************************************************************/
    class StrategyBox2DTextureWrapper
    {
        private                 Texture         texture             = null;
        private                 Vector2         position            = null;
        private                 Vector2         velocity            = null;
        private                 TextureRegion   region              = null;

        @SuppressWarnings( "unused" )
        private                 int             srcX                = 0;
        @SuppressWarnings( "unused" )
        private                 int             srcY                = 0;
        @SuppressWarnings( "unused" )
        private                 int             srcWidth            = 0;
        @SuppressWarnings( "unused" )
        private                 int             srcHeight           = 0;
        
        private                 float           destWidth           = 0;
        private                 float           destHeight          = 0;
                                                
        private                 int             colPadding          = 0;
        
        private                 float           rotation            = 0.0f;
        private                 float           rotationVelocity    = 0.0f;
        
        private                 float           scaleX              = 0.0f;
        private                 float           scaleY              = 0.0f;
        private                 float           originX             = 0.0f;
        private                 float           originY             = 0.0f;
        private                 Color           color               = null;

        public StrategyBox2DTextureWrapper( TextureRegion tex,Vector2 pos )
        {
            setTexture( tex );
            colPadding  = 0;
            position    = pos;
            scaleX      = 1;
            scaleY      = 1;
            color       = Color.WHITE;
            setFilter( TextureFilter.Linear, TextureFilter.Linear );
            velocity    = new Vector2();
        }

        public void setFilter( TextureFilter min, TextureFilter max )
        {
            texture.setFilter( min, max );
        }

        public void setTexture( TextureRegion texRegion, int width,int height )
        {
            region      = texRegion;
            texture     = texRegion.getTexture();
            srcX        = texRegion.getRegionX();
            srcY        = texRegion.getRegionY();
            srcWidth    = texRegion.getRegionWidth();
            srcHeight   = texRegion.getRegionHeight();
            destWidth   = width;
            destHeight  = height;
        }

        public void setTexture( TextureRegion texRegion )
        {
            setTexture( texRegion, texRegion.getRegionWidth(), texRegion.getRegionHeight() );
        }

        public float getWidth()
        {
            return destWidth;
        }

        public float getHeight()
        {
            return destHeight;
        }

        public void setOrigin( int originx, int originy )
        {
            originX = originx;
            originY = originy;
        }

        public void setDimension( float width, float height )
        {
            destWidth  = width;
            destHeight = height;
            originX    = width  / 2;
            originY    = height / 2;
        }

        public void setColor( Color c )
        {
            this.color = c;
        }

        public void setScale( float x, float y )
        {
            scaleX = x;
            scaleY = y;
        }

        public void setRotation( int r )
        {
            rotation = r;
        }

        public void setVelocity( float rv )
        {
            setVelocity( velocity, rv );
        }

        public void setVelocity( float x,float y )
        {
            setVelocity( x, y, rotationVelocity );
        }

        public void setVelocity( Vector2 v, float rot )
        {
            setVelocity( v.x, v.y, rot );
        }

        public void setVelocity( float vx,float vy,float rot )
        {
            rotationVelocity = rot;
            velocity.set( vx, vy );
        }

        public void draw( SpriteBatch sp )
        {
            sp.setColor( color );
            sp.draw( region,position.x - destWidth / 2, position.y - destHeight / 2, originX, originY, destWidth, destHeight, scaleX, scaleY, rotation );
            sp.setColor( Color.WHITE );
        }

        public void update( float dt )
        {
            position.x += velocity.x * dt;
            position.y += velocity.y * dt;
            rotation   += rotationVelocity * dt;
        }

        public boolean isClicked( float x, float y )
        {
            boolean ret =
            (
                    x > ( position.x - destWidth  / 2 - colPadding ) && x < ( position.x + destWidth  / 2 + colPadding )
                &&  y > ( position.y - destHeight / 2 - colPadding ) && y < ( position.y + destHeight / 2 + colPadding )
            );

            return ret;
        }
        
        public void setPosition( Vector2 newPos )
        {
            position.set( newPos );
        }
        
        public void setRotation( float newRot )
        {
            rotation = newRot;
        }
    }
