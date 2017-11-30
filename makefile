FRONTEND=10.200.2.70

deploy: scp-files restart


monitor:
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "pm2 show bitflow-frontend"

npm-install: scp-files
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "./scripts/npm-f3-install.sh"

scp-files:
	scp -r -i ops/tub-cit-frontend.key build config src static index.html package.json ops/scripts ubuntu@${FRONTEND}:/home/ubuntu

restart:
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "./scripts/restart-service.sh"
