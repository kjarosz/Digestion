package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Models.Tile;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.EntityContainer;
import Util.Size;

public class AddTile extends LevelModelCommand {
   private LinkedList<Tile> mTilesCache;
   
   public AddTile(LevelModel level, EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
      
      makeTiles();
   }
   
   private void makeTiles() {
      mTilesCache = new LinkedList<>();
      EntityFactory factory = EntityFactory.getInstance();
      
      World world = new World(new Vec2(0.0f, 0.0f));
      Vec2 pos = new Vec2(0, 0);
      EntityComponents comps = new EntityComponents();
      for(String name: factory.getEntityNames()) {
         int mask = factory.createEntity(world, name, pos, comps);
         
         if((mask & EntityContainer.ENTITY_DRAWABLE) != 0) {
            Tile tile = new Tile();
            tile.name = name;
            tile.image = comps.drawable.image;
            mTilesCache.add(tile);
         }
      }
   }
   
   public void perform(MouseEvent e) {
      BufferedImage tileImg = getTile();
      
      if(tileImg == null || levelHasTile(e.getPoint())) {
         return;
      }
      
      Point pos = snapToGrid(e.getPoint());
      Rectangle tileRect = new Rectangle();
      tileRect.x = pos.x;
      tileRect.y = pos.y;
      tileRect.width = tileImg.getWidth();
      tileRect.height = tileImg.getHeight();
      
      Tile tile = new Tile();
      tile.name = mEditor.getSelectedTile();
      tile.tileRect = tileRect;
      tile.image = tileImg;
      
      mLevel.addTiles(tile);
   }
   
   private boolean levelHasTile(Point coords) {
      for(Tile tile: mLevel.getTiles()) {
         if(tile.tileRect.contains(coords)) {
            return true;
         }
      }
      return false;
   }
   
   private Point snapToGrid(Point coords) {
      Size gridSize = mDrawer.getGridSize();
      Point snapped = new Point(coords);
      snapped.x = (int)(coords.x/(float)gridSize.width)*gridSize.width;
      snapped.y = (int)(coords.y/(float)gridSize.height)*gridSize.height;
      return snapped;
   }
   
   private BufferedImage getTile() {
      String tileName = mEditor.getSelectedTile();
      for(Tile tile: mTilesCache) {
         if(tile.name.equals(tileName)) {
            return tile.image;
         }
      }
      return null;
   }
}
