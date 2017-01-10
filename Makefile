git_version = $$(git branch 2>/dev/null | sed -e '/^[^*]/d'-e's/* \(.*\)/\1/')


doc:
	javadoc -d ./docs -version -sourcepath ./src/main/java macaca.java.biz

deploy:
	mvn -s settings.xml deploy
.PHONY: test
