[![CI](https://github.com/AdevintaSpain/ms-test--hello-twitch/actions/workflows/gradle.yml/badge.svg)](https://github.com/AdevintaSpain/ms-test--hello-twitch/actions/workflows/gradle.yml)

# Adevinta Spain MicroServices Live!

This repo only contains the source code created on the live coding sessions, but not our platform stuff

Watch it on YouTube 👇

1. [🌍 Hello World!](https://youtu.be/fIJCqtmxg2M) with [@guatebus](https://github.com/guatebus) & [@rogervinas](https://github.com/rogervinas)
2. [🔎 Logs & 📈 Metrics](https://youtu.be/UW-DkoRI1FQ) with [@miquelrossello](https://github.com/miquelrossello) & [@rogervinas](https://github.com/rogervinas)
3. [🏗️ AWS Infrastructure](https://youtu.be/f7a-_baRon8) with [@tetexxr](https://github.com/tetexxr) & [@rogervinas](https://github.com/rogervinas)
4. [✉️ Kafka](https://youtu.be/pEii_WtJrrM) with [@tetexxr](https://github.com/tetexxr) & [@rogervinas](https://github.com/rogervinas)
5. [🐙 KrakenD API Gateway](https://youtu.be/49LR6sasSBA) and :octocat: [ms-test--krakend-twitch](https://github.com/AdevintaSpain/ms-test--krakend-twitch) with Filippo Machi & [@rogervinas](https://github.com/rogervinas)

Take a look at our [discussions page](https://github.com/AdevintaSpain/ms-test--hello-twitch/discussions) 👀

## Run

```
docker-compose up -d
./gradlew bootRun
```

### Hello!

Use [curl](https://curl.se/):
```
curl http://localhost:8000/hello
```

### Ping-Pong!

Use [kcat](https://github.com/edenhill/kcat):

* Produce:
```
echo -n '{"ping":123, "message":"hi!"}' | kcat -b localhost:9094 -P -t priv.hello.twitch.ping 
```
* Consume:
```
kcat -b localhost:9094 -C -t priv.hello.twitch.pong 
```

## Test

```
./gradlew test
```

## Integration Test

```
./gradlew integrationTest
```
