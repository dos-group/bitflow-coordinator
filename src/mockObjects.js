let testInfo = {
	'numberOfAgents': 1,
	'numberOfIdleAgents': 0,
	'agents': [{
		'hostName': 'bitflow.testAgent.1',
		'apiPort': '1234',
		'tags': [{
			'resources': "medium",
			'slots': "6"
		}],
		'numCores': 6,
		'totalMem': 456464,
		'usedCpuCores': [
		2.3,
		4.6,
		3.4567
		],
		'usedCpu': 2.3,
		'usedMem': 65243,
		'numProcs': 247,
		'goroutines': 6
	}]
}

let testStep = {
	'id': 0,
	'pipelineId': 0,
	'successors': [1, 2],
	'algorithm': 'Avg',
	'arguments': ['arg1', 'arg2'],
	'isInputStep': false,
	'isOutputStep': false,
}

module.exports = { testInfo, testStep};