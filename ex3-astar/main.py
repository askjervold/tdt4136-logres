import os, sys
import Image
import node, state

print "Exercise 3: A* implementation and usage"

#Around here we should read the map from file and store it
#in a matrix
costs = [[0,1,2],[1,2,3],[1,1,0]]
start = state.State(0, 0, costs[0][0])
goal = state.State(2, 2, costs[2][2])
print "Starting in (" + str(start.x) + "," + str(start.y) + ")"
print "Looking for (" + str(goal.x) + "," + str(goal.y) + ")"

#Keep track of discovered states and open/closed nodes
discoveredStates = {}
openNodes = []
closedNodes = []

#Time to start the actual search
print("Time to start the actual search:\n")
openNodes.append(node.SearchNode(start, None, goal))

currentNode = openNodes[0]
while currentNode is not goal:
	children = currentNode.generateChildren(costs)

	for child in children:
		# If this is a new state:
		if child.id not in discoveredStates:
			discoveredStates.update({child.id:child})
			openNodes.append(node.SearchNode(child, currentNode))
		else:
			for node in openNodes:
				if node.state.id == child.id:
					if node.parent.f > currentNode.f + child.cost:
						node.setParent(currentNode)

	# The node is now expanded, and should be closed
	currentNode.close(closedNodes)


#The best path from start to goal, generated by backtracing
#best-parent pointers from the goal state to the start state
path = []