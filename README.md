https://www.kickstarter.com/projects/archonagames/small-star-empires

![](https://ksr-ugc.imgix.net/assets/005/398/383/bd8bb4cbeedc6f21abc1dc368e52a93b_original.jpg?w=680&fit=max&v=1456078134&auto=format&q=92&s=2150fbd723a5d0bb02598a0a08de6227)

![](https://ksr-ugc.imgix.net/assets/005/398/370/cce7b40e8a3cf02d788d307d560a65ef_original.gif?w=680&fit=max&v=1456077896&q=92&s=a03824100f54e30c258cb734e98a7fa6)


## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Setup

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

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
