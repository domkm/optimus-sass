(ns optimus-sass.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [optimus.assets.creation :refer [last-modified existing-resource]]
            [optimus.assets.load-css :refer [create-css-asset]])
  (:import [org.jruby.embed ScriptingContainer LocalContextScope]))

(let [ruby (ScriptingContainer. LocalContextScope/SINGLETON)
      sass (.runScriptlet ruby "ENV['GEM_PATH']='deps'
                                ENV['GEM_HOME']='deps'
                                require 'sass'
                                Sass")]
  (defn compile-file [^java.net.URL file]
    (.callMethod ruby sass "compile_file" (.getPath file) String)))

(defn load-sass-asset [public-dir path]
  (let [resource (existing-resource public-dir path)]
    (-> (create-css-asset (str/replace path #"\.sass\z|\.scss\z" ".css")
                          (compile-file resource)
                          (last-modified resource))
        (assoc :original-path path))))

(doseq [ext ["sass" "scss"]]
  (defmethod optimus.assets.creation/load-asset ext
    [public-dir path]
    (load-sass-asset public-dir path)))
