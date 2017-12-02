#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
sudo cp ${DIR}/nginx.conf /etc/nginx/sites-available/default
sudo service nginx reload
pm2 restart bitflow-frontend