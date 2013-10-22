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
		level.name = "Newer World"
		level.size = Size(800, 600)
		level.gravity = 9.8

	def createEntities(self, factory, world):
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(96.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(128.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(160.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(192.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(224.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(256.0, 96.0))
		spawnEntity(world, factory, "Spikey Block", Point2D.Double(288.0, 96.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(96.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(128.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(160.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(192.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(224.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(256.0, 224.0))
		spawnEntity(world, factory, "Normal Block", Point2D.Double(288.0, 224.0))
		spawnEntity(world, factory, "Player", Point2D.Double(160.0, 160.0))

