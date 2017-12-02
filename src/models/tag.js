const mongoose = require('mongoose');
const integerValidator = require('mongoose-integer');
const Schema = mongoose.Schema;

let tagSchema = new Schema({
  	resources: { type: String, required: true },
  	slots: { type: String, required: true }
});

tagSchema.set('toJSON', {
    virtuals: true,
    transform: (doc, ret, options) => {
        delete ret.__v;
        delete ret._id;
    }
});

 module.exports = tagSchema;