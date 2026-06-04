(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]
            [org.corfield.new :as deps-new]))

(def sample-dir "target/generated/cool-app")

(defn- run-cmd [dir command-args]
  (let [{:keys [exit]} (b/process (cond-> {:command-args command-args}
                                    dir (assoc :dir dir)))]
    (when-not (zero? exit)
      (throw (ex-info "Command failed" {:dir dir
                                        :command command-args
                                        :exit exit})))))

(defn test "Run fast template tests." [opts]
  (run-cmd nil ["clojure" "-M:test"])
  opts)

(defn generate-sample "Generate target/generated/cool-app." [opts]
  (b/delete {:path "target/generated"})
  (deps-new/create {:template 'jamiepratt/barebones-cljs
                    :name 'my/cool-app
                    :target-dir sample-dir
                    :src-dirs ["resources/io/github"]
                    :overwrite :delete})
  (assoc opts :target-dir sample-dir))

(defn verify-generated "Generate sample app and run npm install + verify." [opts]
  (let [{:keys [target-dir]} (generate-sample opts)]
    (run-cmd target-dir ["npm" "install"])
    (run-cmd target-dir ["npm" "run" "verify"]))
  opts)

(defn ci "Run fast tests, then generated app verification." [opts]
  (verify-generated (test opts)))
