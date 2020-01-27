(defproject optimus-sass "0.1.1"
  :description "Sass/SCSS asset loader for Optimus"
  :url "http://github.com/DomKM/optimus-sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[io.bit3/jsass "5.7.3"]
                 [org.clojure/clojure "1.10.0"]
                 [optimus "0.20.2"]]
  :resource-paths ["deps"]
  :global-vars {*warn-on-reflection* true})
