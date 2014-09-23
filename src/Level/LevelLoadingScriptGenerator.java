package Level;

import java.io.BufferedWriter;
import java.io.IOException;

import org.jbox2d.common.Vec2;

import Entity.EntityComponents;

public class LevelLoadingScriptGenerator {
   protected static void generateScript(Level level, EntityContainer world, BufferedWriter writer) throws IOException {
      writeHeader(writer);
      writeLoadLevel(level, writer);
      writeLoadEntities(world, writer);
   }
   
   private static void writeHeader(BufferedWriter writer) throws IOException {
      writeImports(writer);
      writeSpawnFunction(writer);
      writer.write("class LevelLoader(LevelLoadingScript):");
      writer.newLine();
   }
   
   private static void writeImports(BufferedWriter writer) throws IOException {
      writer.write("from org.jbox2d.common import Vec2");      writer.newLine();
      writer.write("from Level import Level");                 writer.newLine();
      writer.write("from Level import LevelLoadingScript");    writer.newLine();
      writer.write("from Entity import EntityFactory");        writer.newLine();
      writer.write("from Level import World");                 writer.newLine();
      writer.write("from java.awt.geom import Point2D");       writer.newLine();
      writer.newLine();
   }
   
   private static void writeSpawnFunction(BufferedWriter writer) throws IOException {
      writer.write("def spawnEntity(world, factory, name, position):");          writer.newLine();
      writer.write("\tid = world.createNewEntity()");                            writer.newLine();
      writer.write("\tcomponents = world.accessComponents(id)");                 writer.newLine();
      writer.write("\tmask = factory.createEntity(name, position, components)"); writer.newLine();
      writer.write("\tworld.setEntityMask(id, mask)");                           writer.newLine();
      writer.newLine();
   }
   
   private static void writeLoadLevel(Level level, BufferedWriter writer) throws IOException {
      writer.write("\tdef loadLevel(self, level):");                                            writer.newLine();
      writer.write("\t\tlevel.name = \"" + level.name + "\"");                                  writer.newLine();
      writer.write("\t\tlevel.m_size = Vec2(" + level.m_size.x + ", " + level.m_size.y + ")");  writer.newLine();
      writer.write("\t\tlevel.m_gravity = " + level.m_gravity);                                 writer.newLine();
      writer.newLine();
   }
   
   private static void writeLoadEntities(EntityContainer world, BufferedWriter writer) throws IOException {
      writer.write("\tdef createEntities(self, factory, world):");               writer.newLine();
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int mask = world.getEntityMask(i);
         if(mask == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents components = world.accessComponents(i);
         writer.write("\t\tspawnEntity(world, factory, ");
         writer.write("\"" + components.name + "\", ");
         
         Vec2 position = components.body.getPosition();
         writer.write("Point2D.Double(" + position.x + ", " + position.y + "))");
         writer.newLine();
      }
      writer.newLine();
   }
}
