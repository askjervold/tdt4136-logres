class State:
	def __init__(self, x, y, cost):
		self.x = x
		self.y = y
		self.id = str(x) + "," + str(y)
		self.cost = cost