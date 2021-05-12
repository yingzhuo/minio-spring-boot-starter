timestamp := $(shell /bin/date "+%F %T")

clean:
	@mvn -f $(CURDIR)/pom.xml clean -q

deploy:
	@mvn -f $(CURDIR)/pom.xml clean deploy -P"sonar"

install:
	@mvn -f $(CURDIR)/pom.xml clean install

github: clean
	@git add .
	@git commit -m "$(timestamp)"
	@git push

.PHONY: clean install deploy github