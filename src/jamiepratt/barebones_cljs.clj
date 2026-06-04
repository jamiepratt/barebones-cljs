(ns jamiepratt.barebones-cljs
  (:require [clojure.string :as str]))

(defn- title-word [word]
  (let [lower (str/lower-case word)]
    (str (str/upper-case (subs lower 0 1))
         (subs lower 1))))

(defn display-name [artifact-id]
  (->> (str/split (str artifact-id) #"[-_]+")
       (remove str/blank?)
       (map title-word)
       (str/join " ")))

(defn data-fn [{:artifact/keys [id]}]
  {:display-name (display-name id)})
