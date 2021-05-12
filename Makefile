timestamp := $(shell /bin/date "+%F %T")

clean:
	@mvn -f $(CURDIR)/pom.xml clean -q

package:
	@mvn -f $(CURDIR)/pom.xml clean package

deploy:
	@mvn -f $(CURDIR)/pom.xml clean deploy -P"sonar"

install:
	@mvn -f $(CURDIR)/pom.xml clean install

version:
	@mvn -f $(CURDIR)/pom.xml versions:set
	@mvn -f $(CURDIR)/pom.xml -N versions:update-child-modules
	@mvn -f $(CURDIR)/pom.xml versions:commit

github: clean
	@git add .
	@git commit -m "$(timestamp)"
	@git push

.PHONY: clean package install deploy version github