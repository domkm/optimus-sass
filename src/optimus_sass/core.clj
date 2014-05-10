(ns optimus-sass.core
  (:require [clojure.string :as str]
            [optimus.assets.creation :refer [last-modified existing-resource]]
            [optimus.assets.load-css :refer [create-css-asset]]
            [zweikopf.multi :refer [call-ruby ruby-eval]])
  (:import [org.jruby.embed ScriptingContainer LocalContextScope]))

(def ^:private ruby
  (ScriptingContainer. LocalContextScope/SINGLETON))

(ruby-eval ruby "ENV['GEM_PATH']='deps'
                 ENV['GEM_HOME']='deps'
                 require 'sass'")

(def ^:private sass (ruby-eval ruby "Sass"))

(defn ^:private compile-sass [path]
  (call-ruby ruby sass :compile_file path))

(defn ^:private load-sass-asset [public-dir path]
  (let [resource (existing-resource public-dir path)
        css (-> resource .getFile compile-sass)
        css-asset (create-css-asset (str/replace path #"\.sass\z|\.scss\z" ".css")
                                    css
                                    (last-modified resource))]
    (assoc css-asset :original-path path)))

(doseq [ext ["sass" "scss"]]
  (defmethod optimus.assets.creation/load-asset ext
    [public-dir path]
    (load-sass-asset public-dir path)))
