
    package de.christopherstock.strategy.box2d;

    import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;

    public abstract class StrategyBox2DBaseBoxObject{
        static final float WORLD_TO_BOX=0.01f;
        static final float BOX_TO_WORLD=100f;
        float ConvertToBox(float x){
             return x*WORLD_TO_BOX;
        }
        float ConvertToWorld(float x){
            return x*BOX_TO_WORLD;
        }
        protected Body body;
        protected StrategyBox2DBoxUserData userData;
        protected Vector2 worldPosition;
        public StrategyBox2DBaseBoxObject(Vector2 pos,World world,int boxIndex,int collisionGroup){
              userData=new StrategyBox2DBoxUserData(boxIndex,collisionGroup);
              worldPosition=new Vector2();
              CreateBody(world,pos,0);
              body.setUserData(userData);
        }
        public void CreateBody(World world,Vector2 pos,float angle){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(ConvertToBox(pos.x),ConvertToBox(pos.y));
            bodyDef.angle=angle;
            body = world.createBody(bodyDef);
        }

        public void MakeRectFixture(float width,float height,BodyDef.BodyType bodyType,
           float density,float restitution, Vector2 pos,float angle){
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
        void MakeCircleFixture(float radius,BodyDef.BodyType bodyType,
           float density,float restitution, Vector2 pos,float angle){

           FixtureDef fixtureDef=new FixtureDef();
           fixtureDef.density=density;
           fixtureDef.restitution=restitution;
           fixtureDef.shape=new CircleShape();
           fixtureDef.shape.setRadius(StrategyBox2D.ConvertToBox(radius));

           body.createFixture(fixtureDef);
           fixtureDef.shape.dispose();
        }
        public void UpdateWorldPosition(){
            worldPosition.set(ConvertToWorld(body.getPosition().x),ConvertToWorld(body.getPosition().y));
        }
    }
