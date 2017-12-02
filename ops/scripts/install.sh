#!/usr/bin/env bash

# install required packages
curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install -y build-essential
sudo apt-get install -y nginx
sudo npm install -g pm2

# set nginx config
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
sudo cp ${DIR}/nginx.conf /etc/nginx/sites-available/default

# add ssh key for gitlab
sudo cp ${DIR}/frontend-server.key ~/.ssh/id_rsa
eval `ssh-agent -s`
ssh-add

# start server using process manager
pm2 start npm --name "bitflow-frontend" -- start