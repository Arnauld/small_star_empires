This project is strongely inspired from [**Small Star Empires**](https://www.kickstarter.com/projects/archonagames/small-star-empires) by [Archona Games](http://www.archonagames.com/) from KickStarter

The purpose of this project is only to learn clojurescript by using an actual game with all its set of rules. The rules are quite simple - thus making the game a good candidate for a full implementation - whereas the possibility of the game is quite impressive.
Of course, I hope it will also do some advertisement for this game, encouraging you to buy to the board game!

:warning: I'm neither a designer nor part of the game's authors - just a developer :alien:

![](https://ksr-ugc.imgix.net/assets/005/618/714/4ba46dd009a2e125c673b8ad01aaca0c_original.png?w=680&fit=max&v=1459334271&auto=format&lossless=true&s=6eddc59179992338e0a4ce466bdd4ad4)

**https://www.kickstarter.com/projects/archonagames/small-star-empires**

![](https://ksr-ugc.imgix.net/assets/005/398/383/bd8bb4cbeedc6f21abc1dc368e52a93b_original.jpg?w=680&fit=max&v=1456078134&auto=format&q=92&s=2150fbd723a5d0bb02598a0a08de6227)

![](https://ksr-ugc.imgix.net/assets/005/398/370/cce7b40e8a3cf02d788d307d560a65ef_original.gif?w=680&fit=max&v=1456077896&q=92&s=a03824100f54e30c258cb734e98a7fa6)


## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Developer Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## Diary

Initial Layout:

```
$ lein new figwheel small_star_empires -- --reagent
```


## License

Copyright © 2016 Arnauld Loyer

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
