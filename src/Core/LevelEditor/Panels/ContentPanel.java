package Core.LevelEditor.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.jbox2d.common.Vec2;

import Core.LevelEditor.LevelEditor;
import Core.LevelEditor.Panels.Control.ContentPanelControl;
import Entity.EntityComponents;
import Graphics.ScrollablePanel;
import Level.EntityContainer;
import Level.Level;
import Util.Size;
import Util.UnitConverter;

public final class ContentPanel extends JPanel implements ActionListener, FocusListener {
	public static final int CONTENT_PANEL = 0;
	
   private Level mLevel;
   private EntityContainer mWorld;
   
	// CONTENT PANEL
   private ScrollablePanel mCanvas;

   private Size mGridSize;
   
	private JCheckBox mGridEnBox;
	private JCheckBox mObjectsEnBox;
	
	private JTextField mWidth;
	private JTextField mHeight;
	private JTextField mXGridSize;
	private JTextField mYGridSize;

	boolean mGridEnabled, mBckgrEnabled, mObjectsEnabled, mPlatformsEnabled;
	
	public ContentPanel(LevelEditor editor, Level level, EntityContainer world) {
      mLevel = level;
      mWorld = world;
      mGridEnabled = mBckgrEnabled = mObjectsEnabled = mPlatformsEnabled = true;
      mGridSize = new Size(32, 32);
      createWidgets(editor);
      update();
	}
   
   private void createWidgets(LevelEditor editor) {
		setLayout(new BorderLayout());
      
      createLevelCanvas(editor);
      createContentToolbar();
   }
   
   private void createLevelCanvas(LevelEditor editor) {
      Vec2 px_levelSize = UnitConverter.metersToPixels(mLevel.m_size);
      mCanvas = new ScrollablePanel(new ContentPanelControl(editor), new Size(px_levelSize));
      add(mCanvas, BorderLayout.CENTER);
   }
   
   private void createContentToolbar() {
		JToolBar contentToolbar = new JToolBar();
      addTogglesTo(contentToolbar);
		contentToolbar.add(new JSeparator(JSeparator.VERTICAL));
		
		contentToolbar.add(new JLabel("Size:"));
		
		Vec2 px_levelSize = UnitConverter.metersToPixels(mLevel.m_size);
      mWidth = createTextField((int)px_levelSize.x + "", 4, contentToolbar);
      mHeight = createTextField((int)px_levelSize.y + "", 4, contentToolbar);
		
      contentToolbar.add(new JLabel("GridSize:"));
      mXGridSize = createTextField("32", 4, contentToolbar);
		mYGridSize = createTextField("32", 4, contentToolbar);
		
		add(contentToolbar, BorderLayout.SOUTH);
   }
   
   private JTextField createTextField(String text, int size, Container container) {
      JTextField textField = new JTextField(size);
      textField.setText(text);
      textField.addFocusListener(this);
      container.add(textField);
      return textField;
   }
   
   private void addTogglesTo(JToolBar contentToolbar) {
      createToggle(contentToolbar, "Grid", true);
      createToggle(contentToolbar, "Objects", true);
      createToggle(contentToolbar, "Platforms", true);
   }
   
   private void createToggle(Container container, String name, boolean initValue) {
      JCheckBox toggle = new JCheckBox(name, initValue);
      toggle.setActionCommand(name);
      toggle.addActionListener(this);
      container.add(toggle);
   }
   
   public void setNewLevel(Level level, EntityContainer world) {
      mLevel = level;
      mWorld = world;
      reset();
   }
	
	public void reset() {
	   Vec2 px_levelSize = UnitConverter.metersToPixels(mLevel.m_size);
      setCanvasSize((int)px_levelSize.x, (int)px_levelSize.y);
		
      mGridSize.width = 32;
      mGridSize.height = 32;
		mXGridSize.setText(Integer.toString(mGridSize.width));
		mYGridSize.setText(Integer.toString(mGridSize.height));
      update();
	}
	
	public void setCanvasSize(int width, int height) {
		mWidth.setText(Integer.toString(width));
		mHeight.setText(Integer.toString(height));
		mCanvas.setCanvasSize(new Dimension(width, height));
	}
   
   public void update() {
      paintToCanvas();
      
      invalidate();      
   }
   
   private void paintToCanvas() {		
      Graphics2D g = (Graphics2D)mCanvas.getCanvasGraphics();
      Dimension canvasSize = mCanvas.getCanvasSize();

      Color oldColor = g.getColor();
      g.setBackground(Color.WHITE);
      g.clearRect(0, 0, canvasSize.width, canvasSize.height);
      g.drawRect(0, 0, canvasSize.width, canvasSize.height);
      
      g.setColor(Color.BLACK);

      if(mGridEnabled) {
         for(int i = mGridSize.height; i < canvasSize.height; i += mGridSize.height) 
            g.drawLine(0, i, canvasSize.width, i);

         for(int i = mGridSize.width; i < canvasSize.width; i += mGridSize.width) 
            g.drawLine(i, 0, i, canvasSize.height);
      }

      g.setColor(oldColor);

      g.dispose();
      
      drawWorld();

      mCanvas.showCanvas();
   }
   
   private void drawWorld() {
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         if(mWorld.getEntityMask(i) == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents components = mWorld.accessComponents(i);
         Vec2 m_position = components.body.getPosition();
         Vec2 px_position = UnitConverter.metersToPixels(m_position);
         mCanvas.drawImage(components.drawable.image,
                 px_position.x,
                 px_position.y,
                 0.0f,
                 components.drawable.image.getWidth(),
                 components.drawable.image.getHeight());
      }
   }
   
   public MouseEvent snapToGrid(MouseEvent e) {
      Point coords = e.getPoint();
      coords.x = ((int)(coords.x/mGridSize.width))*mGridSize.width;
      coords.y = ((int)(coords.y/mGridSize.height))*mGridSize.height;
      
      return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(),
            e.getModifiers(), coords.x, coords.y, e.getClickCount(), 
            e.isPopupTrigger(), e.getButton());
   }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object comp = e.getSource();

		if(comp == mGridEnBox) {
			mGridEnabled = mGridEnBox.isSelected();
         validate();
		} else if(comp == mObjectsEnBox) {
			mObjectsEnabled = mObjectsEnBox.isSelected();
         validate();
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		Object source = e.getSource();
		
		if(source == mWidth) {
		   getLevelWidth();
		} else if(source == mHeight) {
		   getLevelHeight();
		} else if(source == mXGridSize) {
		   getGridWidth();
		} else if(source == mYGridSize) {
		   getGridHeight();
		}
		update();
	}
	
	private void getLevelWidth() {
      try {
         int px_width = Integer.parseInt(mWidth.getText());
         mLevel.m_size.x = UnitConverter.pixelsToMeters(px_width);
         setCanvasSize(
               (int)px_width, 
               (int)UnitConverter.metersToPixels(mLevel.m_size.y));
      } catch (NumberFormatException ex) {
         mWidth.setText(
               Integer.toString(
                     (int)UnitConverter.metersToPixels(mLevel.m_size.x)));
      }
	}
	
	private void getLevelHeight() {
      try {
         int px_height = Integer.parseInt(mHeight.getText());
         mLevel.m_size.y = UnitConverter.pixelsToMeters(px_height);
         setCanvasSize(
               (int)UnitConverter.metersToPixels(mLevel.m_size.x), 
               (int)px_height);
      } catch (NumberFormatException ex) {
         mHeight.setText(
               Integer.toString(
                     (int)UnitConverter.metersToPixels(mLevel.m_size.y)));
      }
	}
	
	private void getGridWidth() {
      try {
         mGridSize.width = Integer.parseInt(mXGridSize.getText());
      } catch (NumberFormatException ex) {
         mXGridSize.setText(Integer.toString(mGridSize.width));
      }
	}
	
	private void getGridHeight() {
      try {
         mGridSize.height = Integer.parseInt(mYGridSize.getText());
      } catch (NumberFormatException ex) {
         mYGridSize.setText(Integer.toString(mGridSize.height));
      }
	}
	
	@Override
	public void focusGained(FocusEvent e) {}
}
