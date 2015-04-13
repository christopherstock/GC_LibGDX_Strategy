/*  $Id: StrategyBox2DBoxUserData.java 99 2012-08-01 22:17:52Z jenetic.bytemare@googlemail.com $
 *  ================================================================================================
 */
    package de.christopherstock.strategy.box2d;

    /**********************************************************************************************
    *   Ready to export.
    **********************************************************************************************/
    class StrategyBox2DBoxUserData
    {
        public  int     bodyIndex                       = 0;
        public  int     bodyCollisionGroup              = 0;

        public StrategyBox2DBoxUserData( int index,int group )
        {
            setIndex(          index );
            setCollisionGroup( group );
        }

        protected void setIndex( int index )
        {
            bodyIndex = index;
        }

        protected void setCollisionGroup( int group )
        {
            bodyCollisionGroup = group;
        }
    }
