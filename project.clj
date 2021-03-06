(defproject small_star_empires "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.clojure/core.async "0.2.374"
                  :exclusions [org.clojure/tools.reader]]
                 ; last reagent + last react...
                 [reagent "0.6.0-alpha" :exclusions [cljsjs/react-dom]]
                 [cljsjs/react-dom "15.0.0-rc.2-0"]
                 [cljsjs/react-dom-server "15.0.0-rc.2-0"]]

  :plugins [[lein-figwheel "0.5.2"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]

  ;:hooks [leiningen.cljsbuild]

  :source-paths ["src-clj" "src-shared"]
  :test-paths ["test-clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
                          [{:id           "dev"
                            :source-paths ["src-cljs", "src-shared"]

                            ;; If no code is to be run, set :figwheel true for continued automagical reloading
                            :figwheel     {:on-jsload "small_star_empires.core/on-js-reload"}

                            :compiler     {:main                 small_star_empires.core
                                           :asset-path           "js/compiled/out"
                                           :output-to            "resources/public/js/compiled/small_star_empires.js"
                                           :output-dir           "resources/public/js/compiled/out"
                                           :source-map-timestamp true}}
                           ;; This next build is an compressed minified build for
                           ;; production. You can build this with:
                           ;; lein cljsbuild once min
                           {:id           "min"
                            :source-paths ["src-cljs", "src-shared"]
                            :compiler     {:output-to     "resources/public/js/compiled/small_star_empires.js"
                                           :main          small_star_empires.core
                                           :optimizations :advanced
                                           :pretty-print  false}}
                           ; This build is for the ClojureScript unit tests that will
                           ; be run via PhantomJS.  See the phantom/unit-test.js file
                           ; for details on how it's run.
                           {:id           "test"
                            :source-paths ["src-cljs", "src-shared", "test-cljs"]
                            :compiler     {:output-to     "resources/private/js/unit-test.js"
                                           :optimizations :whitespace
                                           :pretty-print  true}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"]             ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
