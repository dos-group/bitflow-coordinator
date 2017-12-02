const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let projectSchema = new Schema({
  id: { type: Number, integer: true },
  name: { type: String, required: true },
  creatorId: { type: Number, integer: true, required: true },
  createdAt: { type: String, required: true },
  users: { type: Array }
});

projectSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = projectSchema;