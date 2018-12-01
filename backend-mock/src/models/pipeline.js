const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let pipelineSchema = new Schema({
  id: { type: Number, integer: true },
  name: { type: String, required: true },
  projectId: { type: Number, integer: true, required: true },
  lastChanged: { type: String, required: true },
  pipelineSteps: { type: Array } 
});

pipelineSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = pipelineSchema;