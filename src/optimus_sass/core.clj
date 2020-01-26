(ns optimus-sass.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [optimus.assets.creation :refer [last-modified existing-resource]]
            [optimus.assets.load-css :refer [create-css-asset]])
  (:import [io.bit3.jsass Options]))

(defn compile-file [^java.net.URL file output]
  (let [compiler (io.bit3.jsass.Compiler.)
        options (Options.)]
    (.getCss (.compileFile compiler (.toURI file) (.toURI output) options))))

(defn load-sass-asset [public-dir path]
  (let [resource (existing-resource public-dir path)
        new-path (str/replace path #"\.sass\z|\.scss\z" ".css")
        output (.toURL (.toURI (io/file (str public-dir new-path))))]
    (-> (create-css-asset new-path
                          (compile-file resource output)
                          (last-modified resource))
        (assoc :original-path path))))

(doseq [ext ["sass" "scss"]]
  (defmethod optimus.assets.creation/load-asset ext
    [public-dir path]
    (load-sass-asset public-dir path)))
