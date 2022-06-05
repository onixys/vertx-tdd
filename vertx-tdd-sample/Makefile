# import config.env and deploy.env
cnf ?= config.env
include $(cnf)
export $(shell sed 's/=.*//' $(cnf))

dpl ?= deploy.env
include $(dpl)
export $(shell sed 's/=.*//' $(dpl))


# HELP
# This will output the help for each task
# thanks to https://marmelab.com/blog/2016/02/29/auto-documented-makefile.html
.PHONY: help

help: ## This help.
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

.DEFAULT_GOAL := help

# DOCKER TASKS

# Build the container
build:
	mvn clean install
	docker build -t $(APP_NAME) .

run: #stop ## Run container on port configured in `config.env`
	docker run -i -t --rm --env-file=./config.env -p=$(PORT):$(PORT) --name="$(CONTAINER_NAME)" $(APP_NAME)

stop: ## Stop running containers
	docker stop $(CONTAINER_NAME)

rm: stop ## Stop and remove running containers
	docker rm $(APP_NAME)

clean: ## Clean the generated/compiles files
	echo "nothing clean ..."

# Docker release - build, tag and push the container
release: build publish ## Make a release by building and publishing the `{version}` and `latest` tagged containers to ECR

# Docker publish
publish: repo-login publish-latest publish-version ## publish the `{version}` and `latest` tagged containers to ECR

publish-latest: tag-latest ## publish the `latest` taged container to ECR
	@echo 'publish latest to $(DOCKER_REPO)'
	docker push $(APP_NAME):latest

publish-version: tag-version ## publish the `{version}` taged container to ECR
	@echo 'publish $(VERSION) to $(DOCKER_REPO)'
	docker push $(APP_NAME):$(VERSION)

# Docker tagging
tag: tag-latest tag-version ## Generate container tags for the `{version}` and `latest` tags

tag-latest: ## Generate container `{version}` tag
	@echo 'create tag latest'
	docker tag $(APP_NAME) $(APP_NAME):latest

tag-version: ## Generate container `latest` tag
	@echo 'create tag $(VERSION)'
	docker tag $(APP_NAME) $(APP_NAME):$(VERSION)

# HELPERS
repo-login:
	docker login

version: ## output to version
	@echo $(VERSION)

pod-delete:
	kubectl delete pod $(POD_NAME)

pod-deploy:
	kubectl create -f ../../suzohapp-cp-kafka-deployment/kubernetes/pod/$(POD_NAME).yml

clean-all:
	make pod-delete

deploy-all:
	make build publish pod-deploy
