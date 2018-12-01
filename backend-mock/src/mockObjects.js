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

let testStep0 = {
	'id': 0,
	'pipelineId': 0,
	'successors': [1],
	'algorithm': 'Input',
	'isInputStep': true,
	'isOutputStep': false,
	'inputTechnologies': ['tcp', 'csv', 'binary']
}

let testStep1 = {
	'id': 1,
	'pipelineId': 0,
	'successors': [2],
	'algorithm': 'Avg',
	'arguments': ['arg1', 'arg2'],
	'isInputStep': false,
	'isOutputStep': false
}

let testStep2 = {
	'id': 2,
	'pipelineId': 0,
	'algorithm': 'Output',
	'isInputStep': false,
	'isOutputStep': true,
	'outputTechnologies': ['tcp', 'csv', 'binary']
}

module.exports = { testInfo, testStep0, testStep1, testStep2 };
