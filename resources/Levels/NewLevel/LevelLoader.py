from Util import Size
from Level import Level
from Level import LevelLoadingScript
from Entity import EntityFactory
from Level import World
from java.awt.geom import Point2D

def spawnEntity(world, factory, name, position):
	id = world.createNewEntity()
	components = world.accessComponents(id)
	mask = factory.createEntity(name, position, components)
	world.setEntityMask(id, mask)

class LevelLoader(LevelLoadingScript):
	def loadLevel(self, level):
		level.name = "NewLevel"
		level.size = Size(800, 600)
		level.gravity = 9.8

	def createEntities(self, factory, world):
		spawnEntity(world, factory, "Normal Block", Point2D.Double(64.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(96.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(128.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(160.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(192.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(224.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(256.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(288.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(320.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(352.0, 320.0))
		spawnEntity(world, factory, "Player", Point2D.Double(96.0, 256.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 320.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 288.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 256.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 192.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 160.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(32.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(64.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(96.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(128.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(160.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(192.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(224.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(256.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(288.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(320.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(352.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 128.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 160.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 192.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 256.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 288.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(384.0, 320.0))

