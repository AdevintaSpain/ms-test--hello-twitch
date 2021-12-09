[![CI](https://github.com/AdevintaSpain/ms-test--hello-twitch/actions/workflows/gradle.yml/badge.svg)](https://github.com/AdevintaSpain/ms-test--hello-twitch/actions/workflows/gradle.yml)

# Adevinta Spain MicroServices Live!

This repo only contains the source code created on the live coding sessions, but not our platform stuff

Watch it on YouTube 👇

1. [🌍 Hello World!](https://www.youtube.com/watch?v=fIJCqtmxg2M&list=PLaZLZgOTnV42vfA4zGnNmw_ZCeoHaA8-G) with [@guatebus](https://github.com/guatebus) & [@rogervinas](https://github.com/rogervinas)
2. [🔎 Logs & 📈 Metrics](https://www.youtube.com/watch?v=UW-DkoRI1FQ&list=PLaZLZgOTnV42vfA4zGnNmw_ZCeoHaA8-G) with [@miquelrossello](https://github.com/miquelrossello) & [@rogervinas](https://github.com/rogervinas)
3. [🏗️ AWS Infrastructure](https://www.youtube.com/watch?v=f7a-_baRon8&list=PLaZLZgOTnV42vfA4zGnNmw_ZCeoHaA8-G) with [@tetexxr](https://github.com/tetexxr) & [@rogervinas](https://github.com/rogervinas)
4. [✉️ Kafka](https://www.youtube.com/watch?v=pEii_WtJrrM&list=PLaZLZgOTnV42vfA4zGnNmw_ZCeoHaA8-G) with [@tetexxr](https://github.com/tetexxr) & [@rogervinas](https://github.com/rogervinas)
5. [🐙 KrakenD API Gateway](https://www.youtube.com/watch?v=49LR6sasSBA&list=PLaZLZgOTnV42vfA4zGnNmw_ZCeoHaA8-G) and :octocat: [ms-test--krakend-twitch](https://github.com/AdevintaSpain/ms-test--krakend-twitch) with Filippo Machi & [@rogervinas](https://github.com/rogervinas)

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
