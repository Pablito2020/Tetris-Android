<h1 align="center">Tetris Game for Android 🕹️</h1>
<p align="center">
Tetris game written in Kotlin for Android devices. The implementation uses the MVVM pattern and clean architecture.
</p>

## Summary

  - [Dependencies](#dependencies-)
  - [Preview](#preview-)
  - [License](#license-)
  - [Contributing](#contributing-)

## Dependencies 📦:
This project depends on my own tetris logic, that can be found here:
 - [Tetris Library for Kotlin](https://github.com/Pablito2020/Tetris)
And the konfetti library for a more motivational finish 😇:
 - [Konfetti library for android](https://github.com/DanielMartinus/Konfetti)

The default gradle configuration get's the Tetris jar from the github packages **using my own credentials that are exported as environment variables**. So, if you try to run the project it should fail because it can't find the jar.

A fix for this is to remove the following lines from the settings.gradle file:
```groovy
    maven {
        url = uri("https://maven.pkg.github.com/Pablito2020/Tetris")
        credentials {
            username = "Pablito2020"
            password = System.getenv("GITHUB_TOKEN")
        }
    }
```
And this other line from the app/build.gradle file (where version should be the current version of the library):
```
implementation("me.pablito:tetris:version")
```

Once you've done that, download the Tetris jar file from the [github repo](https://github.com/Pablito2020/Tetris) and add it as a dependency via settings/project-structure

## Preview 📹
![tetris-example](https://user-images.githubusercontent.com/18640261/170821575-a6abe1f3-3d65-4ba7-ad77-9202c236d285.gif)

## License 📖
The project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for more information.

## Contributing 🧑‍🤝‍🧑
Please read [CONTRIBUTING.md](.github/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.
