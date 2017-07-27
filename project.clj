(defproject directionsurveycarry "0.1.0-SNAPSHOT"
  :main directionsurveycarry.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0" :exclusions [cljsjs/react]]
                 [cljsjs/react-with-addons "15.3.1-0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [carry "0.7.0"]
                 [carry-reagent "0.7.0"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.10" :exclusions [org.clojure/clojure]]]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "directionsurveycarry.core/on-jsload"}
     :compiler     {:main                 directionsurveycarry.core
                    :optimizations        :none
                    :output-to            "resources/public/js/app.js"
                    :output-dir           "resources/public/js/dev"
                    :asset-path           "js/dev"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            directionsurveycarry.core
                    :optimizations   :advanced
                    :output-to       "resources/public/js/app.js"
                    :output-dir      "resources/public/js/min"
                    :elide-asserts   true
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ]})
