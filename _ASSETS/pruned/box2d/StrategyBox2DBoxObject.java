
    package de.christopherstock.strategy.box2d;

    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.math.*;
    import  com.badlogic.gdx.physics.box2d.*;

    public class StrategyBox2DBoxObject extends StrategyBox2DBaseBoxObject
    {
         StrategyBox2DTextureWrapper texture;

         public static final int CIRCLE_OBJECT=1;

         public static final int POLY_OBJECT=2;

         public StrategyBox2DBoxObject( TextureRegion region,Vector2 pos, int bodyType,World world,int index,int colgroup )
         {
                super(pos,world,index,colgroup);
                texture=new StrategyBox2DTextureWrapper(region,pos);
                if(bodyType==CIRCLE_OBJECT){
                        MakeCircleFixture(texture.GetWidth()/2,BodyDef.BodyType.StaticBody,1,1,pos,0);
                }else{
                        MakeRectFixture(texture.GetWidth(),texture.GetHeight(),BodyDef.BodyType.StaticBody,1,1,pos,0);
                }
         }
        public void Draw(SpriteBatch sp){
               texture.Draw(sp);
        }
        public void Update(float dt)
        {
              UpdateWorldPosition();
              texture.SetPosition(worldPosition.x, worldPosition.y);
              //MathUtils is a class available in box2d which has useful math functions
              texture.SetRotation(body.getAngle()*MathUtils.radiansToDegrees);
        }

    }
