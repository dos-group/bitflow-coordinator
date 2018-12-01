FROM node:7.1-slim

# Create app directory
RUN mkdir -p /myapp/src
WORKDIR /myapp

# Install app dependencies
COPY package.json /myapp/
RUN npm install

# Copy app source
COPY src /myapp/src

EXPOSE 8080
CMD [ "npm", "start" ]