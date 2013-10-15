from Core.Messages import Message
from Entity.EntityScripting import EntityScript
from Entity import Entity
from Entity.Modules import GraphicsModule
from Graphics import CanvasInterface
from java.awt import Point
from java.awt.geom import Rectangle2D
from java.util import List

class EntityScript(EntityScript):
	def create(self, entity, arguments):
		self.entity = entity
		
		self.entity.setName("Breaking Block")
		
		boundRect = self.entity.getObjectRect()
		boundRect.width = 32.0
		boundRect.height = 32.0
		self.entity.setObjectRect(boundRect)
		
		self.graphicsModule = GraphicsModule(entity)
		self.graphicsModule.loadImage("img4", self.entity.getPWD() + "\\BreakingBlock4.gif")
		self.graphicsModule.selectImage("img4")
	
	def destroy(self):
		pass
	
	def processKeys(self):
		pass
	
	def processMousePress(self, coords, button):
		pass
	
	def processMouseRelease(self, coords, button):
		pass
	
	def processMouseClick(self, coords, button):
		pass
	
	def processMouseMotion(self, coords, button, dragged):
		pass
	
	def processMessage(self, message):
		pass
	
	def processCollisionMessage(self, message):
		pass
	
	def reset(self):
		pass
	
	def update(self):
		pass
	
	def draw(self, canvas):
		self.graphicsModule.draw(canvas)


