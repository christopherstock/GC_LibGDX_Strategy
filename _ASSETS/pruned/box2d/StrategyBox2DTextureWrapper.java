/*  $Id: StrategyBox2DTextureWrapper.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.box2d;

    import com.badlogic.gdx.graphics.g2d.*;
import  com.badlogic.gdx.math.*;

    /**************************************************************************************
    *   The version history system.
    *
    *   @author     Christopher Stock
    *   @version    0.0.2
    **************************************************************************************/
    public class StrategyBox2DTextureWrapper
    {
         TextureRegion region;
         int width;
         int height;
         Vector2 position;
         float scaleX;
         float scaleY;
         float originX;
         float originY;
         float rotation;
         public StrategyBox2DTextureWrapper(TextureRegion region,Vector2 pos)
         {
              this.position=pos;
              SetTextureRegion(region);
         }
         public void SetTextureRegion(TextureRegion aRegion){
              this.region=aRegion;
              width=aRegion.getRegionWidth();
              height=aRegion.getRegionHeight();
              originX=width/2;
              originY=height/2;
              scaleX=1;
              scaleY=1;
         }
         public int GetWidth(){
            return width;
         }
         public int GetHeight(){
              return height;
         }

         public void SetPosition(float x,float y){
              position.set(x,y);
         }
         public void SetRotation(float r){
              rotation=r;
         }

         public void Draw(SpriteBatch sp){
              sp.draw(region,position.x-width/2, position.y-height/2,
               originX, originY, width, height,
              scaleX, scaleY, rotation);
         }
    }
