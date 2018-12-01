const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let pipelineStepSchema = new Schema({
  id: { type: Number, integer: true },
  pipelineId: { type: Number, integer: true, required: true },
  successors: { type: Array },
  algorithm: { type: String, required: true },
  arguments: { type: Array },
  isInputStep: { type: Boolean, required: true },
  isOutputStep: { type: Boolean, required: true },
  inputTechnologies: { type: Array },
  outputTechnologies: { type: Array },
  selectedInputTechnology: { type: String },
  selectedOutputTechnology: { type: String },
  inputFileName: { type: String },
  outputFileName: { type: String },
  inputTcpAddress: { type: String },
  outputTcpAddress: { type: String },
  agentHostName: { type: String },
  agentApiPort: { type: String }
});

pipelineStepSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = pipelineStepSchema;