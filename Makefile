IMAGE_NAME = soybean-admin-backend
DATE = $(shell date +%y%m%d)
IMAGE_TAG = $(DATE)-1.0.0

clean:
	./gradlew clean

dev-run: clean
	./gradlew :system:quarkusDev

build: clean
	./gradlew build

build-native-linux: clean
	./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

build-native-mac: clean
	./gradlew build -Dquarkus.package.type=native

build-image-jvm: build
	docker build -f src/main/docker/Dockerfile.jvm -t $(IMAGE_NAME):$(IMAGE_TAG) .

build-image-native: build-native-linux
	docker build -f src/main/docker/Dockerfile.native-micro -t $(IMAGE_NAME):$(IMAGE_TAG) .
