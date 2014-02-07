package Level;

import Entity.EntityComponents;

public class EntityContainer {
   public static final int MAXIMUM_ENTITIES = 20000;
   public static final int ENTITY_NONE = 0;
   public static final int ENTITY_DRAWABLE = 1;
   public static final int ENTITY_MOVABLE = 2;
   public static final int ENTITY_COLLIDABLE = 4;
   public static final int ENTITY_CONTROLLABLE = 8 | ENTITY_MOVABLE;
   public static final int ENTITY_ANIMATED = 16 | ENTITY_DRAWABLE;
   public static final int ENTITY_TRIGGERABLE = 32;
   public static final int ENTITY_DESTRUCTIBLE = 64;
   public static final int ENTITY_FOCUSABLE = 128;
   public static final int ENTITY_SPAWNER = 256;
   
   private int mEntities[];
   
   private EntityComponents mEmptyComponent;   
   private EntityComponents mComponents[];
   
   public EntityContainer() {
      mEntities = new int[MAXIMUM_ENTITIES];
      clearEntities();
      
      mEmptyComponent = new EntityComponents();
      mComponents = new EntityComponents[MAXIMUM_ENTITIES];
   }
   
   public final void clearEntities() {
      for(int i = 0; i < MAXIMUM_ENTITIES; i++)
         mEntities[i] = ENTITY_NONE;
   }
   
   private boolean IDIsValid(int entityID) {
      return 0 <= entityID && entityID < MAXIMUM_ENTITIES;
   }
   
   public int createNewEntity() {
      for(int i = 0; i < MAXIMUM_ENTITIES; i++)
         if(mEntities[i] == ENTITY_NONE) {
            mComponents[i] = new EntityComponents();
            return i;
         }
      return -1;
   }
   
   public void setEntityMask(int entityID, int mask) {
      if(IDIsValid(entityID))
         mEntities[entityID] = mask;
   }
   
   public int getEntityMask(int entityID) {
      if(IDIsValid(entityID))
         return mEntities[entityID];
      
      return ENTITY_NONE;
   }
   
   public void destroyEntity(int entityID) {
      if(IDIsValid(entityID))
         mEntities[entityID] = ENTITY_NONE;
   }
   
   public EntityComponents accessComponents(int entityID) {
      if(IDIsValid(entityID))
         return mComponents[entityID];
      
      return mEmptyComponent;
   }
   
   public void resetTimers() {
      // TO-DO write code to take care of this
   }
}
