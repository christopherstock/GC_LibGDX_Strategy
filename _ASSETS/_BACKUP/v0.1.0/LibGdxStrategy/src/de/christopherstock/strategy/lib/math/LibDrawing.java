/*  $Id: LibMath.java 103 2012-08-01 22:56:56Z jenetic.bytemare@googlemail.com $
 *  =======================================================================================
 */
    package de.christopherstock.strategy.lib.math;

    import  com.badlogic.gdx.graphics.*;
    import  com.badlogic.gdx.graphics.g2d.*;
    import  com.badlogic.gdx.graphics.glutils.*;
    import  com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
    import  com.badlogic.gdx.math.*;

    /**************************************************************************************
    *   Offers arithmetic functionality.
    *
    *   @author     Christopher Stock
    *   @version    1.0
    **************************************************************************************/
    public final class LibDrawing
    {
        public static final void fillRect( SpriteBatch batch, float x, float y, float width, float height, Color col )
        {
            //paws current sprite batch
            batch.end();

            //render the filled rect
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            shapeRenderer.setProjectionMatrix(  batch.getProjectionMatrix() );
            shapeRenderer.begin(                ShapeType.FilledRectangle   );
            shapeRenderer.setColor(             col                         );
            shapeRenderer.filledRect(           x, y, width, -height        );   //inverse height for drawing downwards
            shapeRenderer.end();

            //resume current sprite batch
            batch.begin();
        }

        public static final void fillLine( SpriteBatch batch, float offX, float offY, Vector2 p1, Vector2 p2, Color col )
        {
            //paws current sprite batch
            batch.end();

            //render the filled rect
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            shapeRenderer.setProjectionMatrix(  batch.getProjectionMatrix() );
            shapeRenderer.begin(                ShapeType.Line              );
            shapeRenderer.setColor(             col                         );
            shapeRenderer.line(                 offX + p1.x, offY - p1.y, offX + p2.x, offY - p2.y      );   //inverse height for drawing downwards
            shapeRenderer.end();

            //resume current sprite batch
            batch.begin();
        }
    }
