Stack is a mobile application that allows you to solve your programming problems using data from the popular 
[StackOverFlow](https://stackoverflow.com/) website using an API from [StackExchange](https://api.stackexchange.com/).
The app is fully built in Jetpack Compose and uses new solutions such as [Ktor](https://ktor.io/) or [Koin](https://insert-koin.io/)

## TODO
- [  ] Allowing the user to link the app to his account
- [  ] Improving application optimization and UI
- [  ] Adding TensorFlow to teach the tags that interest you
- [  ] Adding tests

## Project Setup
1. Clone repository and open project in the latest version of Android Studio.
2. Generate and import your `google-services.json` file and put it in the `/app`
3. Create `local.properties` and import it to `/app`
4. Add your [StackExchange](https://api.stackexchange.com/) STACK_API_KEY key in `local.properties`
```
STACK_API_KEY = "YOUR_STACK_API_KEY"
```

1. Create `keystore.properties` file and import it to `/app`
2. Add your `keyAlias`, `keyPassword`, `storePassword` and `storeFile` properties.
```
keyAlias=XYZ
keyPassword=XYZ
storePassword=XYZ
storeFile=File XYZ
```

## Inspiration
- [stack](https://github.com/tylerbwong/stack)
