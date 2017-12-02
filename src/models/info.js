const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let infoSchema = new Schema({
  numberOfAgents: { type: Number, integer: true, required: true },
  numberOfIdleAgents: { type: Number, integer: true, required: true },
  agents: { type: Array, required: true }
});

infoSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = infoSchema;