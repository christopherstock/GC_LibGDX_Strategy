
package de.christopherstock.strategy.box2d;

public class StrategyBox2DBoxUserData
{
        int collisionGroup;

        int boxId;

        public StrategyBox2DBoxUserData(int boxid,int collisiongroup){
                  Set(boxid,collisiongroup);
        }

        public void Set(int boxid,int collisiongroup){
                  this.boxId=boxid;
                  this.collisionGroup=collisiongroup;
        }

        public int GetBoxId(){
               return this.boxId;
        }

        public int GetCollisionGroup()
        {
                return this.collisionGroup;
        }

}