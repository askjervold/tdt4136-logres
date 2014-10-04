import math
import state

class SearchNode:
	def __init__(self, state, parent, goal):
		self.state = state
		self.parent = parent
		self.isOpen = True
		self.g = state.cost
		if parent is not None:
			self.g += parent.g

	def compareTo(self, node):
		if self.f == node.f:
			return 0
		return 1 if self.f < node.f else -1

	def generateChildren(self, costs):
		children = []
		children.append(state.State(self.state.x, self.state.y - 1, costs[self.state.x][self.state.y - 1]))
		children.append(state.State(self.state.x, self.state.y + 1, costs[self.state.x][self.state.y + 1]))
		children.append(state.State(self.state.x + 1, self.state.y, costs[self.state.x + 1][self.state.y]))
		children.append(state.State(self.state.x - 1, self.state.y, costs[self.state.x - 1][self.state.y]))
		return children

	def getG():
		return self.g

	def getH(goal):
		return math.fabs((goal.x - self.x) + (goal.y - self.y))

	def close(self, closedNodes):
		self.isOpen = False

	def setParent(self, newParent):
		self.parent = newParent