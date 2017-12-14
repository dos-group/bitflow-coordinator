FRONTEND=10.200.2.70

deploy: scp-files restart

monitor:
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "pm2 show bitflow-frontend"

npm-install:
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "./scripts/npm-f3-install.sh"

restart:
	ssh -i ops/tub-cit-frontend.key ubuntu@${FRONTEND} "./scripts/restart-service.sh"

scp-files:
	scp -r -i ops/tub-cit-frontend.key build config src static index.html package.json ops/scripts ubuntu@${FRONTEND}:/home/ubuntu

swagger:
	bootprint openapi ops/swagger.yaml ops/scripts/swagger

test-production:
	npm run build
	echo "FROM kyma/docker-nginx\nCOPY dist/ /var/www\nCMD 'nginx'" > Dockerfile
	docker build -t bitflow .
	rm Dockerfile
	docker run -p 80:80 bitflow
