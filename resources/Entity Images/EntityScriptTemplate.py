from Core.Messages import Message
from Entity.EntityScripting import EntityScript
from Entity import Entity
from Graphics import CanvasInterface
from java.util import LinkedList
from java.awt import Point

class EntityScript(EntityScript):
	def create(self, entity, arguments):
		pass
	
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
		pass


