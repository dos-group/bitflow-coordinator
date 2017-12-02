const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let userSchema = new Schema({
  id: { type: Number, integer: true },
  name: { type: String, required: true },
  email: { type: String, required: true },
  password: { type: String, required: true },
  registeredSince: { type: String, required: true }
});

userSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = userSchema;