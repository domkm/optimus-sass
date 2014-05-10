(defproject optimus-sass "0.0.1"
  :description "Sass/SCSS asset loader for Optimus"
  :url "http://github.com/DomKM/optimus-sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [optimus "0.14.3"]
                 [org.jruby/jruby-complete "1.7.11"]
                 [zweikopf "1.0.0" :exclusions [org.jruby/jruby-complete]]]
  :resource-paths ["deps"])
