ifeq ($(OS),Windows_NT)
    GRADLEW = ./gradlew.bat
else
    GRADLEW = ./gradlew
endif

IMAGE_NAME = soybean-admin-backend
DATE = $(shell date +%y%m%d)
IMAGE_TAG = $(DATE)-1.0.0

clean:
	$(GRADLEW) clean

dev-run: clean
	$(GRADLEW) :system:quarkusDev

docker-profile-run: clean
	$(GRADLEW) :system:quarkusRun -Dquarkus.profile=docker

build: clean
	$(GRADLEW) build

build-native-linux: clean
	$(GRADLEW) build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true

#Could not determine the dependencies of task ':quarkusBuild'.
#https://github.com/quarkusio/quarkus/discussions/40679
build-native-mac: clean
	$(GRADLEW) build -Dquarkus.native.enabled=true -Dquarkus.package.jar.enabled=false -x test

build-native-win:
	$(GRADLEW) clean build "-Dquarkus.native.enabled=true" "-Dquarkus.package.jar.enabled=false" -x test

build-image-jvm: build
	docker build -f src/main/docker/Dockerfile.jvm -t $(IMAGE_NAME):$(IMAGE_TAG) .

build-image-native: build-native-linux
	docker build -f src/main/docker/Dockerfile.native-micro -t $(IMAGE_NAME):$(IMAGE_TAG) .
