# Ops

This folder contains anything related to deployment and maintenance of the application.
The root folder ../ contains a makefile 

- tub-cit-frontend.key: the ssh key-file, use this to ssh to the servers on cit openstack
- scripts: a folder containing scripts to be copied onto the server
 - install.sh: a script that sets up the server, installing node, nginx, pm2, etc.
 - nginx.conf: the nginx config with portforwarding 80->8080
 - npm-f3-install.sh: equals to `npm install` for machines with low RAM (in case `npm install` gets killed), see [Original](https://gist.github.com/SuperPaintman/851b330c08b2363aea1c870f0cc1ea5a)
 - restart-service.sh: restarts the application
 