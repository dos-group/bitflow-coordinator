'use strict';

const express = require('express');
const cors = require('cors');
const app = express();
const bodyParser = require('body-parser');

const PORT = 8080;

// MongoDB connection setup
const mongodb = require('mongodb');
const mongoose = require('mongoose');
const host = process.env.MONGO_PORT_27017_TCP_ADDR;
const port = process.env.MONGO_PORT_27017_TCP_PORT;
const mongodbURL = 'mongodb://' + host +  ':' + port + '/dev';

mongoose.connect(mongodbURL);
let db = mongoose.connection;
db.on('error', console.error.bind(console, 'conn error:'));
db.once('open', () => {
  app.listen(PORT, () => console.log('Connected via mongoose'));
});

// Schemas and models

// process.env.BASE_URL
const agentSchema = require('./models/agent.js');
const infoSchema = require('./models/info.js');
const pipelineSchema = require('./models/pipeline.js');
const pipelineStepSchema = require('./models/pipelinestep.js');
const projectSchema = require('./models/project.js');
const tagSchema = require('./models/tag.js');
const userSchema = require('./models/user.js');

let Agent = mongoose.model('Agent', agentSchema, 'agent');
let Info = mongoose.model('Info', infoSchema, 'info');
let Pipeline = mongoose.model('Pipeline', pipelineSchema, 'pipeline');
let PipelineStep = mongoose.model('PipelineStep', pipelineStepSchema, 'pipelinestep');
let Project = mongoose.model('Project', projectSchema, 'project');
let Tag = mongoose.model('Tag', tagSchema, 'tag');
let User = mongoose.model('User', userSchema, 'user');

const mockObjects = require('./mockObjects');

// Express Application Middleware
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded
// Enabling cors
app.use(cors());
// Enabling cors pre-flight requests

// Paths
app.options('/info', cors());
app.options('/users', cors());
app.options('/user/:id', cors());
app.options('/projects', cors());
app.options('/project/:id', cors());
app.options('/pipelines', cors());
app.options('/pipeline/:id', cors());
app.options('/pipelinesteps', cors());
app.options('/pipelinestep/:id', cors());

// Insert infrastructure mock data
(function () {
	let info = new Info(mockObjects.testInfo);
	info.save((err, i) => {
		if (err || i === null) {
			console.log(err);
		} else {
			console.log('Created info mock object');
		}
	});
}());

(function () {
	let step = new PipelineStep(mockObjects.testStep);
	step.save((err, s) => {
		if (err || s === null) {
			console.log(err);
		} else {
			console.log('Created pipeline step mock object')
		}
	});
}());

app.get('/', (req, res) => {
	res.end('This path is unused. Possible paths: info, user, project, pipeline, pipelinestep.');
});

// - Info

// get information about agents and their tags (infrastructure)
app.get('/info', (req, res) => {
	Info.find().exec((err, info) => {
		if (err || info === null) {
			res.status(404).end();
		} else {
			res.status(200).send(info);
		}
	});
});

// - Users

// get all users
app.get('/users', (req, res) => {
	User.find().exec((err, users) => {
		if (err || users === null) {
			res.status(404).end();
		} else {
			res.status(200).send(users);
		}
	});
});


// get a single user
app.get('/user/:id', (req, res) => {
	User.findOne({'id': req.params.id}).exec((err, user) => {
		if (err || user === null) {
			res.status(404).end();
		} else {
			res.status(200).send(user);
		}
	});
});

// create a new user
app.post('/users', (req, res) => {
	createNewUser(req, res);
});

// edit a user
app.put('/user/:id', (req, res) => {
	User.findOne({'id': req.params.id}).exec((err, user) => {
		if (err || user === null) {
			res.status(400).end();
		} else {
			user.remove();
			createNewUser(req, res);
		}
	});
});

function createNewUser(req, res) {
	User.find().exec((err, users) => {
		let user = new User(req.body);
		let newId = users
			.map(el => el.id)
			.reduce((prev, curr) => { return curr > prev ? curr + 1 : prev + 1 }, 0);
		user.id = newId;
		user.save((err, u) => {
			if (err || u === null) {
				res.status(400).end();
			} else {
				let ip = req.hostname || "127.0.0.1";
				let loc = 'http://' + ip +':4000' + '/user/' + u.id;
				res.status(201).header('location', loc).end();
			}
		});
	});
}

// - Projects

// get all projects
app.get('/projects', (req, res) => {
	Project.find().exec((err, projects) => {
		if (err || projects === null) {
			res.status(404).end();
		} else {
			res.status(200).send(projects);
		}
	});
});

// get a single project
app.get('/project/:id', (req, res) => {
	Project.findOne({'id': req.params.id}).exec((err, project) => {
		if (err || project === null) {
			res.status(404).end();
		} else {
			res.status(200).send(project);
		}
	});
});

// create a new project
app.post('/projects', (req, res) => {
	createNewProject(req, res);
});

// edit a project
app.put('/project/:id', (req, res) => {
	Project.findOne({'id': req.params.id}).exec((err, project) => {
		if (err || project === null) {
			res.status(400).end();
		} else {
			project.remove();
			createNewProject(req, res);
		}
	});
});

function createNewProject(req, res) {
	Project.find().exec((err, projects) => {
		let project = new Project(req.body);
		let newId = projects
			.map(el => el.id)
			.reduce((prev, curr) => { return curr > prev ? curr + 1 : prev + 1 }, 0);
		project.id = newId;
		project.save((err, p) => {
			if (err || p === null) {
				res.status(400).end();
			} else {
				let ip = req.hostname || "127.0.0.1";
				let loc = 'http://' + ip +':4000' + '/project/' + p.id;
				res.status(201).header('location', loc).end();
			}
		});
	});
}

// delete a project
app.delete('/project/:id', (req, res) => {
	Project.findOne({'id': req.params.id}).exec((err, project) => {
		if (err || project === null) {
			res.status(400).end();
		} else {
			project.remove();
			res.status(204).end();
		}
	});
});

// - Pipelines

// get all pipelines
app.get('/pipelines', (req, res) => {
	Pipeline.find().exec((err, pipelines) => {
		if (err || pipelines === null) {
			res.status(404).end();
		} else {
			res.status(200).send(pipelines);
		}
	});
});

// get a single pipeline
app.get('/pipeline/:id', (req, res) => {
	Pipeline.findOne({'id': req.params.id}).exec((err, pipeline) => {
		if (err || pipeline === null) {
			res.status(404).end();
		} else {
			res.status(200).send(pipeline);
		}
	});
});

// create a new pipeline
app.post('/pipelines', (req, res) => {
	createNewPipeline(req, res);
});

// edit a pipeline
app.put('/pipeline/:id', (req, res) => {
	Pipeline.findOne({'id': req.params.id}).exec((err, pipeline) => {
		if (err || pipeline === null) {
			res.status(400).end();
		} else {
			pipeline.remove();
			createNewPipeline(req, res);
		}
	});
});

function createNewPipeline(req, res) {
	Pipeline.find().exec((err, pipelines) => {
		let pipeline = new Pipeline(req.body);
		let newId = pipelines
			.map(el => el.id)
			.reduce((prev, curr) => { return curr > prev ? curr + 1 : prev + 1 }, 0);
		pipeline.id = newId;
		pipeline.save((err, p) => {
			if (err || p === null) {
				res.status(400).end();
			} else {
				let ip = req.hostname || "127.0.0.1";
				let loc = 'http://' + ip +':4000' + '/pipeline/' + p.id;
				res.status(201).header('location', loc).end();
			}
		});
	});
}

// - Pipeline Steps

// get all pipeline steps
app.get('/pipelinesteps', (req, res) => {
	PipelineStep.find().exec((err, steps) => {
		if (err || steps === null) {
			res.status(404).end();
		} else {
			res.status(200).send(steps);
		}
	});
});

// get a single pipeline step
app.get('/pipelinesteps/:id', (req, res) => {
	PipelineStep.findOne({'id': req.params.id}).exec((err, step) => {
		if (err || step === null) {
			res.status(404).end();
		} else {
			res.status(200).send(step);
		}
	});
});
