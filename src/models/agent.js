const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let agentSchema = new Schema({
  hostName: { type: String, required: true },
  apiPort: { type: String, required: true },
  tags: { type: Array, required: true },
  numCores: { type: Number, integer: true, required: true },
  totalMem: { type: Number, integer: true, required: true },
  usedCpuCores: { type: Array, required: true },
  usedCpu: { type: Number, required: true },
  usedMem: { type: Number, integer: true, required: true },
  numProcs: { type: Number, integer: true, required: true },
  goroutines: { type: Number, integer: true, required: true }
});

agentSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = agentSchema;