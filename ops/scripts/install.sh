#!/usr/bin/env bash

curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install -y build-essential
sudo apt-get install -y nginx
sudo npm install -g pm2

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
sudo cp ${DIR}/nginx.conf /etc/nginx/sites-available/default
pm2 restart npm --name "bitflow-frontend" -- start