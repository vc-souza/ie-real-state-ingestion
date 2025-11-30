.config/deployment/archiver/config/application.properties:
	@bash .scripts/host/gen.sh archiver \
		MYSQL_RW_HOST\
		MYSQL_RW_PORT\
		MYSQL_RW_USERNAME\
		MYSQL_RW_PASSWORD\
		KAFKA_BOOTSTRAP_HOST\
		KAFKA_BOOTSTRAP_PORT

.config/deployment/fetcher/config/application.properties:
	@bash .scripts/host/gen.sh fetcher \
		REDIS_BOOTSTRAP_HOST\
		REDIS_BOOTSTRAP_PORT\
		KAFKA_BOOTSTRAP_HOST\
		KAFKA_BOOTSTRAP_PORT

.config/deployment/gateway/config/application.properties:
	@bash .scripts/host/gen.sh gateway \
		KAFKA_BOOTSTRAP_HOST\
		KAFKA_BOOTSTRAP_PORT


.PHONY: install
install: .config/deployment/archiver/config/application.properties
install: .config/deployment/fetcher/config/application.properties
install: .config/deployment/gateway/config/application.properties

.PHONY: uninstall
uninstall:
# TODO: docker compose down, etc
	@rm -rf .config/deployment/archiver/
	@rm -rf .config/deployment/fetcher/
	@rm -rf .config/deployment/gateway/
